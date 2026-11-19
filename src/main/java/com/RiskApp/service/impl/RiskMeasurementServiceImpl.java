package com.RiskApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.RiskApp.dao.GenericDAO;
import com.RiskApp.service.GenericService;
import com.RiskApp.service.impl.GenericServiceImpl;
import com.RiskApp.dao.RiskMeasurementDAO;
import com.RiskApp.domain.RiskMeasurement;
import com.RiskApp.dto.RiskMeasurementDTO;
import com.RiskApp.dto.RiskMeasurementSearchDTO;
import com.RiskApp.dto.RiskMeasurementPageDTO;
import com.RiskApp.dto.RiskMeasurementConvertCriteriaDTO;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import com.RiskApp.service.RiskMeasurementService;
import com.RiskApp.util.ControllerUtils;


@Service
public class RiskMeasurementServiceImpl extends GenericServiceImpl<RiskMeasurement, Integer> implements RiskMeasurementService {

    private final static Logger logger = LoggerFactory.getLogger(RiskMeasurementServiceImpl.class);

	@Autowired
	RiskMeasurementDAO riskMeasurementDao;

	

	@Override
	public GenericDAO<RiskMeasurement, Integer> getDAO() {
		return (GenericDAO<RiskMeasurement, Integer>) riskMeasurementDao;
	}
	
	public List<RiskMeasurement> findAll () {
		List<RiskMeasurement> riskMeasurements = riskMeasurementDao.findAll();
		
		return riskMeasurements;	
		
	}

	public ResultDTO addRiskMeasurement(RiskMeasurementDTO riskMeasurementDTO, RequestDTO requestDTO) {

		RiskMeasurement riskMeasurement = new RiskMeasurement();

		riskMeasurement.setRiskMeasurementId(riskMeasurementDTO.getRiskMeasurementId());

		riskMeasurement.setPortfolioId(riskMeasurementDTO.getPortfolioId());

		riskMeasurement.setMeasurementType(riskMeasurementDTO.getMeasurementType());

		riskMeasurement.setValue(riskMeasurementDTO.getValue());

		riskMeasurement.setMeasuredAt(riskMeasurementDTO.getMeasuredAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		riskMeasurement = riskMeasurementDao.save(riskMeasurement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<RiskMeasurement> getAllRiskMeasurements(Pageable pageable) {
		return riskMeasurementDao.findAll(pageable);
	}

	public Page<RiskMeasurement> getAllRiskMeasurements(Specification<RiskMeasurement> spec, Pageable pageable) {
		return riskMeasurementDao.findAll(spec, pageable);
	}

	public ResponseEntity<RiskMeasurementPageDTO> getRiskMeasurements(RiskMeasurementSearchDTO riskMeasurementSearchDTO) {
	
			Integer riskMeasurementId = riskMeasurementSearchDTO.getRiskMeasurementId(); 
 			Integer portfolioId = riskMeasurementSearchDTO.getPortfolioId(); 
 			String measurementType = riskMeasurementSearchDTO.getMeasurementType(); 
  			Date measuredAt = riskMeasurementSearchDTO.getMeasuredAt();
 			String sortBy = riskMeasurementSearchDTO.getSortBy();
			String sortOrder = riskMeasurementSearchDTO.getSortOrder();
			String searchQuery = riskMeasurementSearchDTO.getSearchQuery();
			Integer page = riskMeasurementSearchDTO.getPage();
			Integer size = riskMeasurementSearchDTO.getSize();

	        Specification<RiskMeasurement> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, riskMeasurementId, "riskMeasurementId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, measurementType, "measurementType"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, measuredAt, "measuredAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("measurementType")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<RiskMeasurement> riskMeasurements = this.getAllRiskMeasurements(spec, pageable);
		
		//System.out.println(String.valueOf(riskMeasurements.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(riskMeasurements.getTotalPages()));
		
		List<RiskMeasurement> riskMeasurementsList = riskMeasurements.getContent();
		
		RiskMeasurementConvertCriteriaDTO convertCriteria = new RiskMeasurementConvertCriteriaDTO();
		List<RiskMeasurementDTO> riskMeasurementDTOs = this.convertRiskMeasurementsToRiskMeasurementDTOs(riskMeasurementsList,convertCriteria);
		
		RiskMeasurementPageDTO riskMeasurementPageDTO = new RiskMeasurementPageDTO();
		riskMeasurementPageDTO.setRiskMeasurements(riskMeasurementDTOs);
		riskMeasurementPageDTO.setTotalElements(riskMeasurements.getTotalElements());
		return ResponseEntity.ok(riskMeasurementPageDTO);
	}

	public List<RiskMeasurementDTO> convertRiskMeasurementsToRiskMeasurementDTOs(List<RiskMeasurement> riskMeasurements, RiskMeasurementConvertCriteriaDTO convertCriteria) {
		
		List<RiskMeasurementDTO> riskMeasurementDTOs = new ArrayList<RiskMeasurementDTO>();
		
		for (RiskMeasurement riskMeasurement : riskMeasurements) {
			riskMeasurementDTOs.add(convertRiskMeasurementToRiskMeasurementDTO(riskMeasurement,convertCriteria));
		}
		
		return riskMeasurementDTOs;

	}
	
	public RiskMeasurementDTO convertRiskMeasurementToRiskMeasurementDTO(RiskMeasurement riskMeasurement, RiskMeasurementConvertCriteriaDTO convertCriteria) {
		
		RiskMeasurementDTO riskMeasurementDTO = new RiskMeasurementDTO();

		riskMeasurementDTO.setRiskMeasurementId(riskMeasurement.getRiskMeasurementId());

		riskMeasurementDTO.setPortfolioId(riskMeasurement.getPortfolioId());

		riskMeasurementDTO.setMeasurementType(riskMeasurement.getMeasurementType());

		riskMeasurementDTO.setValue(riskMeasurement.getValue());

		riskMeasurementDTO.setMeasuredAt(riskMeasurement.getMeasuredAt());
		
		return riskMeasurementDTO;
	}

	public ResultDTO updateRiskMeasurement(RiskMeasurementDTO riskMeasurementDTO, RequestDTO requestDTO) {
		
		RiskMeasurement riskMeasurement = riskMeasurementDao.getById(riskMeasurementDTO.getRiskMeasurementId());
		
		riskMeasurement.setRiskMeasurementId(ControllerUtils.setValue(riskMeasurement.getRiskMeasurementId(), riskMeasurementDTO.getRiskMeasurementId()));
		
		riskMeasurement.setPortfolioId(ControllerUtils.setValue(riskMeasurement.getPortfolioId(), riskMeasurementDTO.getPortfolioId()));
		
		riskMeasurement.setMeasurementType(ControllerUtils.setValue(riskMeasurement.getMeasurementType(), riskMeasurementDTO.getMeasurementType()));
		
		riskMeasurement.setValue(ControllerUtils.setValue(riskMeasurement.getValue(), riskMeasurementDTO.getValue()));
		
		riskMeasurement.setMeasuredAt(ControllerUtils.setValue(riskMeasurement.getMeasuredAt(), riskMeasurementDTO.getMeasuredAt()));

        riskMeasurement = riskMeasurementDao.save(riskMeasurement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RiskMeasurementDTO getRiskMeasurementDTOById(Integer riskMeasurementId) {
	
		RiskMeasurement riskMeasurement = riskMeasurementDao.getById(riskMeasurementId);
		
		RiskMeasurementConvertCriteriaDTO convertCriteria = new RiskMeasurementConvertCriteriaDTO();
		return(this.convertRiskMeasurementToRiskMeasurementDTO(riskMeasurement,convertCriteria));
	}

}
