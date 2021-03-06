package com.openclassrooms.paymybuddy.dto.model;

import lombok.Data;

@Data
public class ConnectionDto {

	private int userId;

	private int connectedUserId;

	private String connectedUserEmail;

	private String connectedUserFullName;

	private String connectionName;

}
