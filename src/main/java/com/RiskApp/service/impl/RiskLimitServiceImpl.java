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
import com.RiskApp.dao.RiskLimitDAO;
import com.RiskApp.domain.RiskLimit;
import com.RiskApp.dto.RiskLimitDTO;
import com.RiskApp.dto.RiskLimitSearchDTO;
import com.RiskApp.dto.RiskLimitPageDTO;
import com.RiskApp.dto.RiskLimitConvertCriteriaDTO;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import com.RiskApp.service.RiskLimitService;
import com.RiskApp.util.ControllerUtils;


@Service
public class RiskLimitServiceImpl extends GenericServiceImpl<RiskLimit, Integer> implements RiskLimitService {

    private final static Logger logger = LoggerFactory.getLogger(RiskLimitServiceImpl.class);

	@Autowired
	RiskLimitDAO riskLimitDao;

	

	@Override
	public GenericDAO<RiskLimit, Integer> getDAO() {
		return (GenericDAO<RiskLimit, Integer>) riskLimitDao;
	}
	
	public List<RiskLimit> findAll () {
		List<RiskLimit> riskLimits = riskLimitDao.findAll();
		
		return riskLimits;	
		
	}

	public ResultDTO addRiskLimit(RiskLimitDTO riskLimitDTO, RequestDTO requestDTO) {

		RiskLimit riskLimit = new RiskLimit();

		riskLimit.setRiskLimitId(riskLimitDTO.getRiskLimitId());

		riskLimit.setRiskProfileId(riskLimitDTO.getRiskProfileId());

		riskLimit.setLimitType(riskLimitDTO.getLimitType());

		riskLimit.setLimitValue(riskLimitDTO.getLimitValue());

		riskLimit.setWarningThreshold(riskLimitDTO.getWarningThreshold());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		riskLimit = riskLimitDao.save(riskLimit);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<RiskLimit> getAllRiskLimits(Pageable pageable) {
		return riskLimitDao.findAll(pageable);
	}

	public Page<RiskLimit> getAllRiskLimits(Specification<RiskLimit> spec, Pageable pageable) {
		return riskLimitDao.findAll(spec, pageable);
	}

	public ResponseEntity<RiskLimitPageDTO> getRiskLimits(RiskLimitSearchDTO riskLimitSearchDTO) {
	
			Integer riskLimitId = riskLimitSearchDTO.getRiskLimitId(); 
 			Integer riskProfileId = riskLimitSearchDTO.getRiskProfileId(); 
 			String limitType = riskLimitSearchDTO.getLimitType(); 
   			String sortBy = riskLimitSearchDTO.getSortBy();
			String sortOrder = riskLimitSearchDTO.getSortOrder();
			String searchQuery = riskLimitSearchDTO.getSearchQuery();
			Integer page = riskLimitSearchDTO.getPage();
			Integer size = riskLimitSearchDTO.getSize();

	        Specification<RiskLimit> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, riskLimitId, "riskLimitId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, riskProfileId, "riskProfileId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, limitType, "limitType"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("limitType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<RiskLimit> riskLimits = this.getAllRiskLimits(spec, pageable);
		
		//System.out.println(String.valueOf(riskLimits.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(riskLimits.getTotalPages()));
		
		List<RiskLimit> riskLimitsList = riskLimits.getContent();
		
		RiskLimitConvertCriteriaDTO convertCriteria = new RiskLimitConvertCriteriaDTO();
		List<RiskLimitDTO> riskLimitDTOs = this.convertRiskLimitsToRiskLimitDTOs(riskLimitsList,convertCriteria);
		
		RiskLimitPageDTO riskLimitPageDTO = new RiskLimitPageDTO();
		riskLimitPageDTO.setRiskLimits(riskLimitDTOs);
		riskLimitPageDTO.setTotalElements(riskLimits.getTotalElements());
		return ResponseEntity.ok(riskLimitPageDTO);
	}

	public List<RiskLimitDTO> convertRiskLimitsToRiskLimitDTOs(List<RiskLimit> riskLimits, RiskLimitConvertCriteriaDTO convertCriteria) {
		
		List<RiskLimitDTO> riskLimitDTOs = new ArrayList<RiskLimitDTO>();
		
		for (RiskLimit riskLimit : riskLimits) {
			riskLimitDTOs.add(convertRiskLimitToRiskLimitDTO(riskLimit,convertCriteria));
		}
		
		return riskLimitDTOs;

	}
	
	public RiskLimitDTO convertRiskLimitToRiskLimitDTO(RiskLimit riskLimit, RiskLimitConvertCriteriaDTO convertCriteria) {
		
		RiskLimitDTO riskLimitDTO = new RiskLimitDTO();

		riskLimitDTO.setRiskLimitId(riskLimit.getRiskLimitId());

		riskLimitDTO.setRiskProfileId(riskLimit.getRiskProfileId());

		riskLimitDTO.setLimitType(riskLimit.getLimitType());

		riskLimitDTO.setLimitValue(riskLimit.getLimitValue());

		riskLimitDTO.setWarningThreshold(riskLimit.getWarningThreshold());
		
		return riskLimitDTO;
	}

	public ResultDTO updateRiskLimit(RiskLimitDTO riskLimitDTO, RequestDTO requestDTO) {
		
		RiskLimit riskLimit = riskLimitDao.getById(riskLimitDTO.getRiskLimitId());
		
		riskLimit.setRiskLimitId(ControllerUtils.setValue(riskLimit.getRiskLimitId(), riskLimitDTO.getRiskLimitId()));
		
		riskLimit.setRiskProfileId(ControllerUtils.setValue(riskLimit.getRiskProfileId(), riskLimitDTO.getRiskProfileId()));
		
		riskLimit.setLimitType(ControllerUtils.setValue(riskLimit.getLimitType(), riskLimitDTO.getLimitType()));
		
		riskLimit.setLimitValue(ControllerUtils.setValue(riskLimit.getLimitValue(), riskLimitDTO.getLimitValue()));
		
		riskLimit.setWarningThreshold(ControllerUtils.setValue(riskLimit.getWarningThreshold(), riskLimitDTO.getWarningThreshold()));

        riskLimit = riskLimitDao.save(riskLimit);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RiskLimitDTO getRiskLimitDTOById(Integer riskLimitId) {
	
		RiskLimit riskLimit = riskLimitDao.getById(riskLimitId);
		
		RiskLimitConvertCriteriaDTO convertCriteria = new RiskLimitConvertCriteriaDTO();
		return(this.convertRiskLimitToRiskLimitDTO(riskLimit,convertCriteria));
	}

}
