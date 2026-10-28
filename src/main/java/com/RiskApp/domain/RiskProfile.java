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
@Table(name="risk_profiles")
@Getter @Setter @NoArgsConstructor
public class RiskProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="risk_profile_id")
	private Integer riskProfileId;
    
  	@Column(name="portfolio_id")
	private Integer portfolioId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="risk_level")
	private String riskLevel;
    
  	@Column(name="active")
	private Boolean active;
    

}
