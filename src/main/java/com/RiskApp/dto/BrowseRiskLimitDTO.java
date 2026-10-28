package com.RiskApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseRiskLimitDTO {

	private Integer ownerId;

	private Integer riskLimitId;

	private Integer riskLimitStatus;
	
	private Integer nextOrPrevious;
}

