package com.RiskApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.RiskApp.domain.RiskProfile;
import com.RiskApp.dto.RiskProfileDTO;
import com.RiskApp.dto.RiskProfileSearchDTO;
import com.RiskApp.dto.RiskProfilePageDTO;
import com.RiskApp.dto.RiskProfileConvertCriteriaDTO;
import com.RiskApp.service.GenericService;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RiskProfileService extends GenericService<RiskProfile, Integer> {

	List<RiskProfile> findAll();

	ResultDTO addRiskProfile(RiskProfileDTO riskProfileDTO, RequestDTO requestDTO);

	ResultDTO updateRiskProfile(RiskProfileDTO riskProfileDTO, RequestDTO requestDTO);

    Page<RiskProfile> getAllRiskProfiles(Pageable pageable);

    Page<RiskProfile> getAllRiskProfiles(Specification<RiskProfile> spec, Pageable pageable);

	ResponseEntity<RiskProfilePageDTO> getRiskProfiles(RiskProfileSearchDTO riskProfileSearchDTO);
	
	List<RiskProfileDTO> convertRiskProfilesToRiskProfileDTOs(List<RiskProfile> riskProfiles, RiskProfileConvertCriteriaDTO convertCriteria);

	RiskProfileDTO getRiskProfileDTOById(Integer riskProfileId);



	
}
