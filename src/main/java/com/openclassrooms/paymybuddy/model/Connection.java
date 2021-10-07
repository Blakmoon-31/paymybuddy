package com.openclassrooms.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.openclassrooms.paymybuddy.dto.UserDto;

import lombok.Data;

@Data
@Entity
@Table(name = "connection")
public class Connection {

	@EmbeddedId
	private ConnectionId connectionId;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("userId")
	@JoinColumn(name = "con_user_user_id", nullable = false)
	private UserDto UserConnection;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("connectionId")
	@JoinColumn(name = "con_connection_user_id", nullable = false)
	private UserDto connectedUser;

	@Column(name = "con_connection_name", length = 45, nullable = false)
	private String nameConnectionUser;

}
