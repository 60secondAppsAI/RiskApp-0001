package com.RiskApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RiskBreachDTO {

	private Integer riskBreachId;

	private Integer riskLimitId;

	private Double actualValue;

	private String severity;

	private String status;

	private Date detectedAt;






}
