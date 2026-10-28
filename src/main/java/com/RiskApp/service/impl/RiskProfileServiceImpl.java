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
import com.RiskApp.dao.RiskProfileDAO;
import com.RiskApp.domain.RiskProfile;
import com.RiskApp.dto.RiskProfileDTO;
import com.RiskApp.dto.RiskProfileSearchDTO;
import com.RiskApp.dto.RiskProfilePageDTO;
import com.RiskApp.dto.RiskProfileConvertCriteriaDTO;
import com.RiskApp.dto.common.RequestDTO;
import com.RiskApp.dto.common.ResultDTO;
import com.RiskApp.service.RiskProfileService;
import com.RiskApp.util.ControllerUtils;


@Service
public class RiskProfileServiceImpl extends GenericServiceImpl<RiskProfile, Integer> implements RiskProfileService {

    private final static Logger logger = LoggerFactory.getLogger(RiskProfileServiceImpl.class);

	@Autowired
	RiskProfileDAO riskProfileDao;

	

	@Override
	public GenericDAO<RiskProfile, Integer> getDAO() {
		return (GenericDAO<RiskProfile, Integer>) riskProfileDao;
	}
	
	public List<RiskProfile> findAll () {
		List<RiskProfile> riskProfiles = riskProfileDao.findAll();
		
		return riskProfiles;	
		
	}

	public ResultDTO addRiskProfile(RiskProfileDTO riskProfileDTO, RequestDTO requestDTO) {

		RiskProfile riskProfile = new RiskProfile();

		riskProfile.setRiskProfileId(riskProfileDTO.getRiskProfileId());

		riskProfile.setPortfolioId(riskProfileDTO.getPortfolioId());

		riskProfile.setName(riskProfileDTO.getName());

		riskProfile.setRiskLevel(riskProfileDTO.getRiskLevel());

		riskProfile.setActive(riskProfileDTO.getActive());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		riskProfile = riskProfileDao.save(riskProfile);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<RiskProfile> getAllRiskProfiles(Pageable pageable) {
		return riskProfileDao.findAll(pageable);
	}

	public Page<RiskProfile> getAllRiskProfiles(Specification<RiskProfile> spec, Pageable pageable) {
		return riskProfileDao.findAll(spec, pageable);
	}

	public ResponseEntity<RiskProfilePageDTO> getRiskProfiles(RiskProfileSearchDTO riskProfileSearchDTO) {
	
			Integer riskProfileId = riskProfileSearchDTO.getRiskProfileId(); 
 			Integer portfolioId = riskProfileSearchDTO.getPortfolioId(); 
 			String name = riskProfileSearchDTO.getName(); 
 			String riskLevel = riskProfileSearchDTO.getRiskLevel(); 
  			String sortBy = riskProfileSearchDTO.getSortBy();
			String sortOrder = riskProfileSearchDTO.getSortOrder();
			String searchQuery = riskProfileSearchDTO.getSearchQuery();
			Integer page = riskProfileSearchDTO.getPage();
			Integer size = riskProfileSearchDTO.getSize();

	        Specification<RiskProfile> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, riskProfileId, "riskProfileId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, riskLevel, "riskLevel"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("riskLevel")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<RiskProfile> riskProfiles = this.getAllRiskProfiles(spec, pageable);
		
		//System.out.println(String.valueOf(riskProfiles.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(riskProfiles.getTotalPages()));
		
		List<RiskProfile> riskProfilesList = riskProfiles.getContent();
		
		RiskProfileConvertCriteriaDTO convertCriteria = new RiskProfileConvertCriteriaDTO();
		List<RiskProfileDTO> riskProfileDTOs = this.convertRiskProfilesToRiskProfileDTOs(riskProfilesList,convertCriteria);
		
		RiskProfilePageDTO riskProfilePageDTO = new RiskProfilePageDTO();
		riskProfilePageDTO.setRiskProfiles(riskProfileDTOs);
		riskProfilePageDTO.setTotalElements(riskProfiles.getTotalElements());
		return ResponseEntity.ok(riskProfilePageDTO);
	}

	public List<RiskProfileDTO> convertRiskProfilesToRiskProfileDTOs(List<RiskProfile> riskProfiles, RiskProfileConvertCriteriaDTO convertCriteria) {
		
		List<RiskProfileDTO> riskProfileDTOs = new ArrayList<RiskProfileDTO>();
		
		for (RiskProfile riskProfile : riskProfiles) {
			riskProfileDTOs.add(convertRiskProfileToRiskProfileDTO(riskProfile,convertCriteria));
		}
		
		return riskProfileDTOs;

	}
	
	public RiskProfileDTO convertRiskProfileToRiskProfileDTO(RiskProfile riskProfile, RiskProfileConvertCriteriaDTO convertCriteria) {
		
		RiskProfileDTO riskProfileDTO = new RiskProfileDTO();

		riskProfileDTO.setRiskProfileId(riskProfile.getRiskProfileId());

		riskProfileDTO.setPortfolioId(riskProfile.getPortfolioId());

		riskProfileDTO.setName(riskProfile.getName());

		riskProfileDTO.setRiskLevel(riskProfile.getRiskLevel());

		riskProfileDTO.setActive(riskProfile.getActive());
		
		return riskProfileDTO;
	}

	public ResultDTO updateRiskProfile(RiskProfileDTO riskProfileDTO, RequestDTO requestDTO) {
		
		RiskProfile riskProfile = riskProfileDao.getById(riskProfileDTO.getRiskProfileId());
		
		riskProfile.setRiskProfileId(ControllerUtils.setValue(riskProfile.getRiskProfileId(), riskProfileDTO.getRiskProfileId()));
		
		riskProfile.setPortfolioId(ControllerUtils.setValue(riskProfile.getPortfolioId(), riskProfileDTO.getPortfolioId()));
		
		riskProfile.setName(ControllerUtils.setValue(riskProfile.getName(), riskProfileDTO.getName()));
		
		riskProfile.setRiskLevel(ControllerUtils.setValue(riskProfile.getRiskLevel(), riskProfileDTO.getRiskLevel()));
		
		riskProfile.setActive(ControllerUtils.setValue(riskProfile.getActive(), riskProfileDTO.getActive()));

        riskProfile = riskProfileDao.save(riskProfile);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RiskProfileDTO getRiskProfileDTOById(Integer riskProfileId) {
	
		RiskProfile riskProfile = riskProfileDao.getById(riskProfileId);
		
		RiskProfileConvertCriteriaDTO convertCriteria = new RiskProfileConvertCriteriaDTO();
		return(this.convertRiskProfileToRiskProfileDTO(riskProfile,convertCriteria));
	}

}
