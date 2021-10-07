package com.openclassrooms.paymybuddy.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_email", length = 255, nullable = false, unique = true)
	private String email;

	@Column(name = "user_first_name", length = 20, nullable = false)
	private String firstName;

	@Column(name = "user_last_name", length = 20, nullable = false)
	private String lastName;

}
