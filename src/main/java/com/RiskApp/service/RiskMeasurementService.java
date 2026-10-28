package com.RiskApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.RiskApp.domain.RiskMeasurement;
import com.RiskApp.dto.RiskMeasurementDTO;
import com.RiskApp.dto.RiskMeasurementSearchDTO;
import com.RiskApp.dto.RiskMeasurementPageDTO;
import com.RiskApp.dto.RiskMeasurementConvertCriteriaDTO;
import com.RiskApp.service.GenericService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RiskMeasurementService extends GenericService<RiskMeasurement, Integer> {

	List<RiskMeasurement> findAll();

	ResultDTO addRiskMeasurement(RiskMeasurementDTO riskMeasurementDTO, RequestDTO requestDTO);

	ResultDTO updateRiskMeasurement(RiskMeasurementDTO riskMeasurementDTO, RequestDTO requestDTO);

    Page<RiskMeasurement> getAllRiskMeasurements(Pageable pageable);

    Page<RiskMeasurement> getAllRiskMeasurements(Specification<RiskMeasurement> spec, Pageable pageable);

	ResponseEntity<RiskMeasurementPageDTO> getRiskMeasurements(RiskMeasurementSearchDTO riskMeasurementSearchDTO);
	
	List<RiskMeasurementDTO> convertRiskMeasurementsToRiskMeasurementDTOs(List<RiskMeasurement> riskMeasurements, RiskMeasurementConvertCriteriaDTO convertCriteria);

	RiskMeasurementDTO getRiskMeasurementDTOById(Integer riskMeasurementId);



	
}
