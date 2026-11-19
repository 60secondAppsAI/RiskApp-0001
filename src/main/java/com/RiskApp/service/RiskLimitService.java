package com.RiskApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.RiskApp.domain.RiskLimit;
import com.RiskApp.dto.RiskLimitDTO;
import com.RiskApp.dto.RiskLimitSearchDTO;
import com.RiskApp.dto.RiskLimitPageDTO;
import com.RiskApp.dto.RiskLimitConvertCriteriaDTO;
import com.RiskApp.service.GenericService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RiskLimitService extends GenericService<RiskLimit, Integer> {

	List<RiskLimit> findAll();

	ResultDTO addRiskLimit(RiskLimitDTO riskLimitDTO, RequestDTO requestDTO);

	ResultDTO updateRiskLimit(RiskLimitDTO riskLimitDTO, RequestDTO requestDTO);

    Page<RiskLimit> getAllRiskLimits(Pageable pageable);

    Page<RiskLimit> getAllRiskLimits(Specification<RiskLimit> spec, Pageable pageable);

	ResponseEntity<RiskLimitPageDTO> getRiskLimits(RiskLimitSearchDTO riskLimitSearchDTO);
	
	List<RiskLimitDTO> convertRiskLimitsToRiskLimitDTOs(List<RiskLimit> riskLimits, RiskLimitConvertCriteriaDTO convertCriteria);

	RiskLimitDTO getRiskLimitDTOById(Integer riskLimitId);



	
}
