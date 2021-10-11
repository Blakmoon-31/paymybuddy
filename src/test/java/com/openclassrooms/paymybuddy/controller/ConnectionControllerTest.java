package com.openclassrooms.paymybuddy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.MapUserDtoService;
import com.openclassrooms.paymybuddy.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ConnectionControllerTest {

	@Autowired
	private ConnectionController connectionController;

	@Autowired
	private UserService userService;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Test
	@Order(1)
	public void testSaveConnection() {
		Connection connectionToSave = new Connection();
		UserDto userDtoconnection = mapUserDtoService.getUserDtoById(7).get();
		UserDto userDtoConnected = mapUserDtoService.getUserDtoById(6).get();

		connectionToSave.setUserConnection(userDtoconnection);
		connectionToSave.setConnectedUser(userDtoConnected);
		connectionToSave.setNameConnectionUser("Test");

		Connection connectionSaved = connectionController.saveConnection(connectionToSave);

		assertThat(connectionSaved.getNameConnectionUser()).isEqualTo("Test");

	}

	@Test
	@Order(2)
	public void testGetConnections() {
		Collection<Connection> connectionsList = connectionController.getConnections();

		assertTrue(connectionsList.iterator().hasNext());
	}

	@Test
	@Order(3)
	public void testGetConnectionsByUserId() {
		Collection<Connection> connectionsListFoAUser = connectionController.getConnectionsByUserId(7);

		assertThat(connectionsListFoAUser.size()).isEqualTo(1);
	}

	@Test
	@Order(4)
	public void testDeleteConnectionByUserIdAndConnectedUserId() {
		Connection connectionToSave = new Connection();
		UserDto userDtoconnection = mapUserDtoService.getUserDtoById(7).get();
		UserDto userDtoConnected = mapUserDtoService.getUserDtoById(5).get();

		connectionToSave.setUserConnection(userDtoconnection);
		connectionToSave.setConnectedUser(userDtoConnected);
		connectionToSave.setNameConnectionUser("Test2");

		connectionController.saveConnection(connectionToSave);

		connectionController.deleteConnectionByUserIdAndConnectedUserId(7, 6);
		Collection<Connection> connectionsList = connectionController.getConnectionsByUserId(7);
		assertThat(connectionsList.size()).isEqualTo(1);
	}

	@Test
	@Order(5)
	public void testDeleteConnectionsByUser() {
		User user = userService.getUserById(7).get();
		connectionController.deleteConnectionsByUser(user);

		Collection<Connection> connectionList = connectionController.getConnectionsByUserId(7);

		assertFalse(connectionList.iterator().hasNext());
	}
}
