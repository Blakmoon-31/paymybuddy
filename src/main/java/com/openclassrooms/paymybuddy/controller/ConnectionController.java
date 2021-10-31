package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.dtoservice.MapConnectionDtoService;
import com.openclassrooms.paymybuddy.dtoservice.MapUserDtoService;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Controller
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private MapConnectionDtoService mapConnectionDtoService;

	public Collection<Connection> getConnectionsByUserId(int userId) {
		return connectionService.getUserConnectionsByUserId(userId);
	}

	public Collection<Connection> getConnections() {
		return connectionService.getConnections();
	}

	public Optional<UserDto> searchByEmail(String email) {
		return connectionService.searchByEmail(email);
	}

	@PostMapping("connection")
	public String saveConnection(ConnectionDto connectionDto, Model model, HttpSession httpSession) {
		Connection connectionToSave = new Connection();

		// Verify if the email to add exists
		Optional<UserDto> userSearch = mapUserDtoService.getUserDtoByEmail(connectionDto.getConnectedUserEmail());

		if (userSearch.isPresent()) {
			connectionToSave.setUserConnection(
					mapUserDtoService.getUserDtoById((int) httpSession.getAttribute("userId")).get());
			connectionToSave
					.setConnectedUser(mapUserDtoService.getUserDtoByEmail(connectionDto.getConnectedUserEmail()).get());
			connectionToSave.setNameConnectionUser(connectionDto.getConnectionName());

			connectionService.saveConnection(connectionToSave);
			return "redirect:connection";

		} else {
			Collection<ConnectionDto> connectionsList = mapConnectionDtoService
					.getConnectionsDtoByUserId((int) httpSession.getAttribute("userId"));
			ConnectionDto connectionDtoNew = new ConnectionDto();

			model.addAttribute("connectionDto", connectionDtoNew);
			model.addAttribute("connectionsList", connectionsList);
			model.addAttribute("errorEmail", "Email " + connectionDto.getConnectedUserEmail() + " not found !");
			return "connection";

		}

	}

	@DeleteMapping("deleteConnection/{id}")
	public String deleteConnectionByUserIdAndConnectedUserId(HttpSession httpSession,
			@PathVariable("id") int userConnectedId) {
		connectionService.deleteConnectionByUserIdAndConnectedUserId((int) httpSession.getAttribute("userId"),
				userConnectedId);
		return "redirect:/connection";

	}

	public void deleteConnectionsByUser(User user) {
		connectionService.deleteConnectionsByUserConnection(user);
	}

}
