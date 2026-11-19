package com.RiskApp.dao;

import java.util.List;
import java.util.Date;

import com.RiskApp.dao.GenericDAO;
import com.RiskApp.domain.RiskProfile;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RiskProfileDAO extends GenericDAO<RiskProfile, Integer> {
  
	List<RiskProfile> findAll();
	


}

