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

import com.RiskApp.domain.RiskProfile;
import com.RiskApp.dto.RiskProfileDTO;
import com.RiskApp.dto.RiskProfileSearchDTO;
import com.RiskApp.dto.RiskProfilePageDTO;
import com.RiskApp.service.RiskProfileService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/riskProfile")
@RestController
public class RiskProfileController {

	private final static Logger logger = LoggerFactory.getLogger(RiskProfileController.class);

	@Autowired
	RiskProfileService riskProfileService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<RiskProfile> getAll() {

		List<RiskProfile> riskProfiles = riskProfileService.findAll();
		
		return riskProfiles;	
	}

	@GetMapping(value = "/{riskProfileId}")
	@ResponseBody
	public RiskProfileDTO getRiskProfile(@PathVariable Integer riskProfileId) {
		
		return (riskProfileService.getRiskProfileDTOById(riskProfileId));
	}

 	@RequestMapping(value = "/addRiskProfile", method = RequestMethod.POST)
	public ResponseEntity<?> addRiskProfile(@RequestBody RiskProfileDTO riskProfileDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskProfileService.addRiskProfile(riskProfileDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/riskProfiles")
	public ResponseEntity<RiskProfilePageDTO> getRiskProfiles(RiskProfileSearchDTO riskProfileSearchDTO) {
 
		return riskProfileService.getRiskProfiles(riskProfileSearchDTO);
	}	

	@RequestMapping(value = "/updateRiskProfile", method = RequestMethod.POST)
	public ResponseEntity<?> updateRiskProfile(@RequestBody RiskProfileDTO riskProfileDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = riskProfileService.updateRiskProfile(riskProfileDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
