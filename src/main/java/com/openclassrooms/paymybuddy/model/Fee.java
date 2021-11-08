package com.openclassrooms.paymybuddy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "fee")
public class Fee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fee_id")
	private int id;

	@Column(name = "fee_rate", nullable = false, precision = 3, scale = 2)
	private Double ratePercentage;

	@Column(name = "fee_start_date", nullable = true)
	private LocalDate startDate;

	@Column(name = "fee_end_date", nullable = true)
	private LocalDate endDate;

}
