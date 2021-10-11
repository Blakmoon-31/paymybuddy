package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

//	@GetMapping("/connections/{userId}")
	public Collection<Connection> getConnectionsByUserId(int userId) {
		return connectionService.getUserConnectionsByUserid(userId);
	}

//	@GetMapping("/connections")
	public Collection<Connection> getConnections() {
		return connectionService.getConnections();
	}

	public Connection saveConnection(Connection connection) {
		return connectionService.saveConnection(connection);
	}

	public void deleteConnectionByUserIdAndConnectedUserId(int userId, int userConnectedId) {
		connectionService.deleteConnectionByUserIdAndConnectedUserId(userId, userConnectedId);

	}

	public void deleteConnectionsByUser(User user) {
		connectionService.deleteConnectionsByUserConnection(user);
	}
}
