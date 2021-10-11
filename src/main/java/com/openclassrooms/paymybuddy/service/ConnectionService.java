package com.openclassrooms.paymybuddy.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.ConnectionId;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;

@Service
public class ConnectionService {

	@Autowired
	private UserService userService;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private ConnectionRepository connectionRepository;

	public Collection<Connection> getUserConnectionsByUserid(int userId) {
		UserDto userDto = mapUserDtoService.convertUserToUserDto(userService.getUserById(userId).get());
		Collection<Connection> connectionsList = connectionRepository.findByUserConnection(userDto);
		return connectionsList;
	}

	public Collection<Connection> getConnections() {
		Collection<Connection> connectionsAll = connectionRepository.findAll();
		return connectionsAll;
	}

	public Connection saveConnection(Connection connection) {
		return connectionRepository.save(connection);
	}

	public void deleteConnectionByUserIdAndConnectedUserId(int userId, int userConnectedId) {
		ConnectionId connectionId = new ConnectionId();
		connectionId.setUserConnection(userId);
		connectionId.setConnectedUser(userConnectedId);
		connectionRepository.deleteById(connectionId);

	}

	public void deleteConnectionsByUserConnection(User user) {
		UserDto userDto = mapUserDtoService.getUserDtoById(user.getUserId()).get();
		connectionRepository.deleteAllByUserConnection(userDto);
	}

}
