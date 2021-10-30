package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.dtoservice.MapUserDtoService;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.ConnectionId;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;

@Service
public class ConnectionService {

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private ConnectionRepository connectionRepository;

	public Collection<Connection> getUserConnectionsByUserId(int userId) {
		UserDto userDto = mapUserDtoService.getUserDtoById(userId).get();
		Collection<Connection> connectionsList = connectionRepository.findByUserConnection(userDto);
		return connectionsList;
	}

	public Collection<Connection> getConnections() {
		Collection<Connection> connectionsAll = connectionRepository.findAll();
		return connectionsAll;
	}

	@Transactional
	public Connection saveConnection(Connection connection) {
		// If null or empty, the connection name is set with first and last name of the
		// connected user
		if (connection.getNameConnectionUser() == null || connection.getNameConnectionUser() == "") {
			UserDto userDtoConnected = mapUserDtoService.getUserDtoById(connection.getConnectedUser().getUserId())
					.get();
			String nameToSet = userDtoConnected.getFirstName() + " " + userDtoConnected.getLastName();
			connection.setNameConnectionUser(nameToSet);
		}

		return connectionRepository.save(connection);
	}

	@Transactional
	public void deleteConnectionByUserIdAndConnectedUserId(int userId, int userConnectedId) {
		ConnectionId connectionId = new ConnectionId();
		connectionId.setUserConnection(userId);
		connectionId.setConnectedUser(userConnectedId);
		connectionRepository.deleteById(connectionId);

	}

	@Transactional
	public void deleteConnectionsByUserConnection(User user) {
		UserDto userDto = mapUserDtoService.getUserDtoById(user.getUserId()).get();
		connectionRepository.deleteAllByUserConnection(userDto);
	}

	@Transactional
	public void deleteConnectionsByConnectedUser(User user) {
		UserDto userDto = mapUserDtoService.getUserDtoById(user.getUserId()).get();
		connectionRepository.deleteAllByConnectedUser(userDto);

	}

	public Optional<UserDto> searchByEmail(String email) {
		return mapUserDtoService.getUserDtoByEmail(email);
	}

}
