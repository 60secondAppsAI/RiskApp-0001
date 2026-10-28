package com.RiskApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseRiskProfileDTO {

	private Integer ownerId;

	private Integer riskProfileId;

	private Integer riskProfileStatus;
	
	private Integer nextOrPrevious;
}

