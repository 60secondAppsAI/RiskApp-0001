package com.RiskApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.RiskApp.domain.RiskBreach;
import com.RiskApp.dto.RiskBreachDTO;
import com.RiskApp.dto.RiskBreachSearchDTO;
import com.RiskApp.dto.RiskBreachPageDTO;
import com.RiskApp.dto.RiskBreachConvertCriteriaDTO;
import com.RiskApp.service.GenericService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RiskBreachService extends GenericService<RiskBreach, Integer> {

	List<RiskBreach> findAll();

	ResultDTO addRiskBreach(RiskBreachDTO riskBreachDTO, RequestDTO requestDTO);

	ResultDTO updateRiskBreach(RiskBreachDTO riskBreachDTO, RequestDTO requestDTO);

    Page<RiskBreach> getAllRiskBreachs(Pageable pageable);

    Page<RiskBreach> getAllRiskBreachs(Specification<RiskBreach> spec, Pageable pageable);

	ResponseEntity<RiskBreachPageDTO> getRiskBreachs(RiskBreachSearchDTO riskBreachSearchDTO);
	
	List<RiskBreachDTO> convertRiskBreachsToRiskBreachDTOs(List<RiskBreach> riskBreachs, RiskBreachConvertCriteriaDTO convertCriteria);

	RiskBreachDTO getRiskBreachDTOById(Integer riskBreachId);



	
}
