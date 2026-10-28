package com.RiskApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RiskMeasurementDTO {

	private Integer riskMeasurementId;

	private Integer portfolioId;

	private String measurementType;

	private Double value;

	private Date measuredAt;






}
