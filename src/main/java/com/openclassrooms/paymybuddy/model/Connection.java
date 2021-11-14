package com.openclassrooms.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.openclassrooms.paymybuddy.dto.model.UserDto;

import lombok.Data;

@Data
@Entity
@IdClass(ConnectionId.class)
@Table(name = "connection")
public class Connection {

	@ManyToOne(fetch = FetchType.EAGER)
	@Id
	@JoinColumn(name = "con_user_user_id", nullable = false)
	private UserDto userConnection;

	@ManyToOne(fetch = FetchType.EAGER)
	@Id
	@JoinColumn(name = "con_connection_user_id", nullable = false)
	private UserDto connectedUser;

	@Column(name = "con_connection_name", length = 45, nullable = false)
	private String nameConnectionUser;

}
