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
import com.RiskApp.dao.RiskBreachDAO;
import com.RiskApp.domain.RiskBreach;
import com.RiskApp.dto.RiskBreachDTO;
import com.RiskApp.dto.RiskBreachSearchDTO;
import com.RiskApp.dto.RiskBreachPageDTO;
import com.RiskApp.dto.RiskBreachConvertCriteriaDTO;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import com.RiskApp.service.RiskBreachService;
import com.RiskApp.util.ControllerUtils;


@Service
public class RiskBreachServiceImpl extends GenericServiceImpl<RiskBreach, Integer> implements RiskBreachService {

    private final static Logger logger = LoggerFactory.getLogger(RiskBreachServiceImpl.class);

	@Autowired
	RiskBreachDAO riskBreachDao;

	

	@Override
	public GenericDAO<RiskBreach, Integer> getDAO() {
		return (GenericDAO<RiskBreach, Integer>) riskBreachDao;
	}
	
	public List<RiskBreach> findAll () {
		List<RiskBreach> riskBreachs = riskBreachDao.findAll();
		
		return riskBreachs;	
		
	}

	public ResultDTO addRiskBreach(RiskBreachDTO riskBreachDTO, RequestDTO requestDTO) {

		RiskBreach riskBreach = new RiskBreach();

		riskBreach.setRiskBreachId(riskBreachDTO.getRiskBreachId());

		riskBreach.setRiskLimitId(riskBreachDTO.getRiskLimitId());

		riskBreach.setActualValue(riskBreachDTO.getActualValue());

		riskBreach.setSeverity(riskBreachDTO.getSeverity());

		riskBreach.setStatus(riskBreachDTO.getStatus());

		riskBreach.setDetectedAt(riskBreachDTO.getDetectedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		riskBreach = riskBreachDao.save(riskBreach);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<RiskBreach> getAllRiskBreachs(Pageable pageable) {
		return riskBreachDao.findAll(pageable);
	}

	public Page<RiskBreach> getAllRiskBreachs(Specification<RiskBreach> spec, Pageable pageable) {
		return riskBreachDao.findAll(spec, pageable);
	}

	public ResponseEntity<RiskBreachPageDTO> getRiskBreachs(RiskBreachSearchDTO riskBreachSearchDTO) {
	
			Integer riskBreachId = riskBreachSearchDTO.getRiskBreachId(); 
 			Integer riskLimitId = riskBreachSearchDTO.getRiskLimitId(); 
  			String severity = riskBreachSearchDTO.getSeverity(); 
 			String status = riskBreachSearchDTO.getStatus(); 
 			Date detectedAt = riskBreachSearchDTO.getDetectedAt();
 			String sortBy = riskBreachSearchDTO.getSortBy();
			String sortOrder = riskBreachSearchDTO.getSortOrder();
			String searchQuery = riskBreachSearchDTO.getSearchQuery();
			Integer page = riskBreachSearchDTO.getPage();
			Integer size = riskBreachSearchDTO.getSize();

	        Specification<RiskBreach> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, riskBreachId, "riskBreachId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, riskLimitId, "riskLimitId"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, severity, "severity"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, detectedAt, "detectedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("severity")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<RiskBreach> riskBreachs = this.getAllRiskBreachs(spec, pageable);
		
		//System.out.println(String.valueOf(riskBreachs.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(riskBreachs.getTotalPages()));
		
		List<RiskBreach> riskBreachsList = riskBreachs.getContent();
		
		RiskBreachConvertCriteriaDTO convertCriteria = new RiskBreachConvertCriteriaDTO();
		List<RiskBreachDTO> riskBreachDTOs = this.convertRiskBreachsToRiskBreachDTOs(riskBreachsList,convertCriteria);
		
		RiskBreachPageDTO riskBreachPageDTO = new RiskBreachPageDTO();
		riskBreachPageDTO.setRiskBreachs(riskBreachDTOs);
		riskBreachPageDTO.setTotalElements(riskBreachs.getTotalElements());
		return ResponseEntity.ok(riskBreachPageDTO);
	}

	public List<RiskBreachDTO> convertRiskBreachsToRiskBreachDTOs(List<RiskBreach> riskBreachs, RiskBreachConvertCriteriaDTO convertCriteria) {
		
		List<RiskBreachDTO> riskBreachDTOs = new ArrayList<RiskBreachDTO>();
		
		for (RiskBreach riskBreach : riskBreachs) {
			riskBreachDTOs.add(convertRiskBreachToRiskBreachDTO(riskBreach,convertCriteria));
		}
		
		return riskBreachDTOs;

	}
	
	public RiskBreachDTO convertRiskBreachToRiskBreachDTO(RiskBreach riskBreach, RiskBreachConvertCriteriaDTO convertCriteria) {
		
		RiskBreachDTO riskBreachDTO = new RiskBreachDTO();

		riskBreachDTO.setRiskBreachId(riskBreach.getRiskBreachId());

		riskBreachDTO.setRiskLimitId(riskBreach.getRiskLimitId());

		riskBreachDTO.setActualValue(riskBreach.getActualValue());

		riskBreachDTO.setSeverity(riskBreach.getSeverity());

		riskBreachDTO.setStatus(riskBreach.getStatus());

		riskBreachDTO.setDetectedAt(riskBreach.getDetectedAt());
		
		return riskBreachDTO;
	}

	public ResultDTO updateRiskBreach(RiskBreachDTO riskBreachDTO, RequestDTO requestDTO) {
		
		RiskBreach riskBreach = riskBreachDao.getById(riskBreachDTO.getRiskBreachId());
		
		riskBreach.setRiskBreachId(ControllerUtils.setValue(riskBreach.getRiskBreachId(), riskBreachDTO.getRiskBreachId()));
		
		riskBreach.setRiskLimitId(ControllerUtils.setValue(riskBreach.getRiskLimitId(), riskBreachDTO.getRiskLimitId()));
		
		riskBreach.setActualValue(ControllerUtils.setValue(riskBreach.getActualValue(), riskBreachDTO.getActualValue()));
		
		riskBreach.setSeverity(ControllerUtils.setValue(riskBreach.getSeverity(), riskBreachDTO.getSeverity()));
		
		riskBreach.setStatus(ControllerUtils.setValue(riskBreach.getStatus(), riskBreachDTO.getStatus()));
		
		riskBreach.setDetectedAt(ControllerUtils.setValue(riskBreach.getDetectedAt(), riskBreachDTO.getDetectedAt()));

        riskBreach = riskBreachDao.save(riskBreach);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RiskBreachDTO getRiskBreachDTOById(Integer riskBreachId) {
	
		RiskBreach riskBreach = riskBreachDao.getById(riskBreachId);
		
		RiskBreachConvertCriteriaDTO convertCriteria = new RiskBreachConvertCriteriaDTO();
		return(this.convertRiskBreachToRiskBreachDTO(riskBreach,convertCriteria));
	}

}
