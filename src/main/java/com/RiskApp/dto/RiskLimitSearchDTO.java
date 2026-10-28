package com.RiskApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RiskLimitSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer riskLimitId;
	
	private Integer riskProfileId;
	
	private String limitType;
	
	private Double limitValue;
	
	private Double warningThreshold;
	
}
