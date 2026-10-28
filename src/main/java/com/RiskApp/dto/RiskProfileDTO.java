package com.RiskApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RiskProfileDTO {

	private Integer riskProfileId;

	private Integer portfolioId;

	private String name;

	private String riskLevel;

	private Boolean active;






}
