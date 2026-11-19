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
@Table(name="risk_breachs")
@Getter @Setter @NoArgsConstructor
public class RiskBreach {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="risk_breach_id")
	private Integer riskBreachId;
    
  	@Column(name="risk_limit_id")
	private Integer riskLimitId;
    
  	@Column(name="actual_value")
	private Double actualValue;
    
  	@Column(name="severity")
	private String severity;
    
  	@Column(name="status")
	private String status;
    
  	@Column(name="detected_at")
	private Date detectedAt;
    

}
