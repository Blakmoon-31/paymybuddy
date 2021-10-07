package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_email", length = 255, nullable = false, unique = true)
	private String email;

	@Column(name = "user_password", length = 20, nullable = false)
	private String password;

	@Column(name = "user_first_name", length = 20, nullable = false)
	private String firstName;

	@Column(name = "user_last_name", length = 20, nullable = false)
	private String lastName;

	@Column(name = "user_bank_account", length = 42, nullable = true)
	private String bankAccount;

	@Column(name = "user_balance", nullable = false, precision = 9, scale = 2)
	private Double balance = 0.00;

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "connection", joinColumns = @JoinColumn(name = "con_user_user_id"), inverseJoinColumns = @JoinColumn(name = "con_connection_user_id"))
//	private List<User> connections;

	@OneToMany(mappedBy = "UserConnection")
	private List<Connection> connections = new ArrayList<>();

}
