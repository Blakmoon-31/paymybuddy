package com.openclassrooms.paymybuddy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.openclassrooms.paymybuddy.dto.UserDto;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tra_id")
	private int id;

	@Column(name = "tra_date", nullable = false)
	private LocalDateTime date;

	@Column(name = "tra_amount", nullable = false, precision = 9, scale = 2)
	private Double amount;

	@Column(name = "tra_description", length = 100)
	private String description;

	@Column(name = "tra_billed", nullable = false)
	private boolean billed = false;

	@ManyToOne
	@JoinColumn(name = "tra_sender_user_id", nullable = false)
	private UserDto senderUserDto;

	@ManyToOne
	@JoinColumn(name = "tra_recipient_user_id", nullable = false)
	private UserDto recipientUserDto;

	@ManyToOne
	@JoinColumn(name = "tra_fee_rate_fee_id", nullable = false)
	private Fee fee;

}
