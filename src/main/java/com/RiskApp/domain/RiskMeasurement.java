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
@Table(name="risk_measurements")
@Getter @Setter @NoArgsConstructor
public class RiskMeasurement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="risk_measurement_id")
	private Integer riskMeasurementId;
    
  	@Column(name="portfolio_id")
	private Integer portfolioId;
    
  	@Column(name="measurement_type")
	private String measurementType;
    
  	@Column(name="value")
	private Double value;
    
  	@Column(name="measured_at")
	private Date measuredAt;
    

}
