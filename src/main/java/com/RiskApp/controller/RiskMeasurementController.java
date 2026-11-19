package com.RiskApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.RiskApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.RiskApp.domain.RiskMeasurement;
import com.RiskApp.dto.RiskMeasurementDTO;
import com.RiskApp.dto.RiskMeasurementSearchDTO;
import com.RiskApp.dto.RiskMeasurementPageDTO;
import com.RiskApp.service.RiskMeasurementService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/riskMeasurement")
@RestController
public class RiskMeasurementController {

	private final static Logger logger = LoggerFactory.getLogger(RiskMeasurementController.class);

	@Autowired
	RiskMeasurementService riskMeasurementService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<RiskMeasurement> getAll() {

		List<RiskMeasurement> riskMeasurements = riskMeasurementService.findAll();
		
		return riskMeasurements;	
	}

	@GetMapping(value = "/{riskMeasurementId}")
	@ResponseBody
	public RiskMeasurementDTO getRiskMeasurement(@PathVariable Integer riskMeasurementId) {
		
		return (riskMeasurementService.getRiskMeasurementDTOById(riskMeasurementId));
	}

 	@RequestMapping(value = "/addRiskMeasurement", method = RequestMethod.POST)
	public ResponseEntity<?> addRiskMeasurement(@RequestBody RiskMeasurementDTO riskMeasurementDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskMeasurementService.addRiskMeasurement(riskMeasurementDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/riskMeasurements")
	public ResponseEntity<RiskMeasurementPageDTO> getRiskMeasurements(RiskMeasurementSearchDTO riskMeasurementSearchDTO) {
 
		return riskMeasurementService.getRiskMeasurements(riskMeasurementSearchDTO);
	}	

	@RequestMapping(value = "/updateRiskMeasurement", method = RequestMethod.POST)
	public ResponseEntity<?> updateRiskMeasurement(@RequestBody RiskMeasurementDTO riskMeasurementDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskMeasurementService.updateRiskMeasurement(riskMeasurementDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
