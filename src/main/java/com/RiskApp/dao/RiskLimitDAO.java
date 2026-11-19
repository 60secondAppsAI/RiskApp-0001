package com.RiskApp.dao;

import java.util.List;
import java.util.Date;

import com.RiskApp.dao.GenericDAO;
import com.RiskApp.domain.RiskLimit;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RiskLimitDAO extends GenericDAO<RiskLimit, Integer> {
  
	List<RiskLimit> findAll();
	


}

