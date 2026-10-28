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

import com.RiskApp.domain.RiskLimit;
import com.RiskApp.dto.RiskLimitDTO;
import com.RiskApp.dto.RiskLimitSearchDTO;
import com.RiskApp.dto.RiskLimitPageDTO;
import com.RiskApp.service.RiskLimitService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/riskLimit")
@RestController
public class RiskLimitController {

	private final static Logger logger = LoggerFactory.getLogger(RiskLimitController.class);

	@Autowired
	RiskLimitService riskLimitService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<RiskLimit> getAll() {

		List<RiskLimit> riskLimits = riskLimitService.findAll();
		
		return riskLimits;	
	}

	@GetMapping(value = "/{riskLimitId}")
	@ResponseBody
	public RiskLimitDTO getRiskLimit(@PathVariable Integer riskLimitId) {
		
		return (riskLimitService.getRiskLimitDTOById(riskLimitId));
	}

 	@RequestMapping(value = "/addRiskLimit", method = RequestMethod.POST)
	public ResponseEntity<?> addRiskLimit(@RequestBody RiskLimitDTO riskLimitDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskLimitService.addRiskLimit(riskLimitDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/riskLimits")
	public ResponseEntity<RiskLimitPageDTO> getRiskLimits(RiskLimitSearchDTO riskLimitSearchDTO) {
 
		return riskLimitService.getRiskLimits(riskLimitSearchDTO);
	}	

	@RequestMapping(value = "/updateRiskLimit", method = RequestMethod.POST)
	public ResponseEntity<?> updateRiskLimit(@RequestBody RiskLimitDTO riskLimitDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskLimitService.updateRiskLimit(riskLimitDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
