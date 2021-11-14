package com.openclassrooms.paymybuddy.dto.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class TransactionDto {

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

	@Column(name = "tra_sender_user_id", nullable = false)
	private int senderUserId;

	@Column(name = "tra_recipient_user_id", nullable = false)
	private int recipientUserId;

	@Transient
	private String recipientConnectionName;

	@Transient
	private String recipientUserEmail;

}
