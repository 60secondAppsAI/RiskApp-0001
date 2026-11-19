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

import com.RiskApp.domain.RiskBreach;
import com.RiskApp.dto.RiskBreachDTO;
import com.RiskApp.dto.RiskBreachSearchDTO;
import com.RiskApp.dto.RiskBreachPageDTO;
import com.RiskApp.service.RiskBreachService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/riskBreach")
@RestController
public class RiskBreachController {

	private final static Logger logger = LoggerFactory.getLogger(RiskBreachController.class);

	@Autowired
	RiskBreachService riskBreachService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<RiskBreach> getAll() {

		List<RiskBreach> riskBreachs = riskBreachService.findAll();
		
		return riskBreachs;	
	}

	@GetMapping(value = "/{riskBreachId}")
	@ResponseBody
	public RiskBreachDTO getRiskBreach(@PathVariable Integer riskBreachId) {
		
		return (riskBreachService.getRiskBreachDTOById(riskBreachId));
	}

 	@RequestMapping(value = "/addRiskBreach", method = RequestMethod.POST)
	public ResponseEntity<?> addRiskBreach(@RequestBody RiskBreachDTO riskBreachDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskBreachService.addRiskBreach(riskBreachDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/riskBreachs")
	public ResponseEntity<RiskBreachPageDTO> getRiskBreachs(RiskBreachSearchDTO riskBreachSearchDTO) {
 
		return riskBreachService.getRiskBreachs(riskBreachSearchDTO);
	}	

	@RequestMapping(value = "/updateRiskBreach", method = RequestMethod.POST)
	public ResponseEntity<?> updateRiskBreach(@RequestBody RiskBreachDTO riskBreachDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskBreachService.updateRiskBreach(riskBreachDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
