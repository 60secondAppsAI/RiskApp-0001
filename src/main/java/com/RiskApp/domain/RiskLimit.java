package com.RiskApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="risk_limits")
@Getter @Setter @NoArgsConstructor
public class RiskLimit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="risk_limit_id")
	private Integer riskLimitId;
    
  	@Column(name="risk_profile_id")
	private Integer riskProfileId;
    
  	@Column(name="limit_type")
	private String limitType;
    
  	@Column(name="limit_value")
	private Double limitValue;
    
  	@Column(name="warning_threshold")
	private Double warningThreshold;
    

}
