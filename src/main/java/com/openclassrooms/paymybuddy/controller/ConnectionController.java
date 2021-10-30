package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	public Collection<Connection> getConnectionsByUserId(int userId) {
		return connectionService.getUserConnectionsByUserId(userId);
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

	@GetMapping("deleteConnection/{id}")
	public String deleteConnectionByUserIdAndConnectedUserId(@PathVariable("id") int userConnectedId,
			HttpSession httpSession) {
		connectionService.deleteConnectionByUserIdAndConnectedUserId((int) httpSession.getAttribute("userId"),
				userConnectedId);
		return "redirect:/profil";

	}

	public void deleteConnectionsByUser(User user) {
		connectionService.deleteConnectionsByUserConnection(user);
	}
}
