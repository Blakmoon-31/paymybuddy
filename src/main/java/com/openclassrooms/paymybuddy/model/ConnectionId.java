package com.openclassrooms.paymybuddy.model;

import java.io.Serializable;

import lombok.Data;

@Data
//@Embeddable
public class ConnectionId implements Serializable {

//	private int conUserUserId;
//
//	private int userId;
//
//	private int connectedId;

	private int userConnection;

	private int connectedUser;

}
