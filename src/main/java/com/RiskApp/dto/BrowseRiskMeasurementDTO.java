package com.RiskApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseRiskMeasurementDTO {

	private Integer ownerId;

	private Integer riskMeasurementId;

	private Integer riskMeasurementStatus;
	
	private Integer nextOrPrevious;
}

