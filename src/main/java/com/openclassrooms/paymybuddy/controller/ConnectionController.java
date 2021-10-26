package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	public Collection<Connection> getConnectionsByUserId(int userId) {
		return connectionService.getUserConnectionsByUserid(userId);
	}

	public Collection<Connection> getConnections() {
		return connectionService.getConnections();
	}

	public Optional<UserDto> searchByEmail(String email) {
		return connectionService.searchByEmail(email);
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
