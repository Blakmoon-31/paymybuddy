package com.openclassrooms.paymybuddy.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ConnectionId implements Serializable {

	private int userConnection;

	private int connectedUser;

}
