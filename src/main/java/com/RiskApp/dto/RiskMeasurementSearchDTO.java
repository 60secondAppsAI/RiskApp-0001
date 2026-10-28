package com.RiskApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RiskMeasurementSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer riskMeasurementId;
	
	private Integer portfolioId;
	
	private String measurementType;
	
	private Double value;
	
	private Date measuredAt;
	
}
