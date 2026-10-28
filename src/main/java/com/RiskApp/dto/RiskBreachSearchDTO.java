package com.RiskApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RiskBreachSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer riskBreachId;
	
	private Integer riskLimitId;
	
	private Double actualValue;
	
	private String severity;
	
	private String status;
	
	private Date detectedAt;
	
}
