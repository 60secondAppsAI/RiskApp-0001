package com.RiskApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseRiskBreachDTO {

	private Integer ownerId;

	private Integer riskBreachId;

	private Integer riskBreachStatus;
	
	private Integer nextOrPrevious;
}

