package com.openclassrooms.paymybuddy.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import com.openclassrooms.paymybuddy.controller.ConnectionController;
import com.openclassrooms.paymybuddy.dto.model.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.model.UserDto;
import com.openclassrooms.paymybuddy.dto.service.MapUserDtoService;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.ConnectionService;
import com.openclassrooms.paymybuddy.service.UserService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ConnectionControllerTI {

	@Autowired
	private ConnectionController connectionController;

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private UserService userService;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private HttpSession httpSession;

	@BeforeAll
	public void initConnectionData() {
		Connection connectionToInit = new Connection();
		UserDto userDtoconnection = mapUserDtoService.getUserDtoById(7).get();
		UserDto userDtoConnected = mapUserDtoService.getUserDtoById(5).get();

		connectionToInit.setUserConnection(userDtoconnection);
		connectionToInit.setConnectedUser(userDtoConnected);
		connectionToInit.setNameConnectionUser("Test2");

		connectionService.saveConnection(connectionToInit);

		userDtoconnection = mapUserDtoService.getUserDtoById(1).get();
		userDtoConnected = mapUserDtoService.getUserDtoById(7).get();

		connectionToInit.setUserConnection(userDtoconnection);
		connectionToInit.setConnectedUser(userDtoConnected);
		connectionToInit.setNameConnectionUser("Test3");

		connectionService.saveConnection(connectionToInit);

		userDtoconnection = mapUserDtoService.getUserDtoById(7).get();
		userDtoConnected = mapUserDtoService.getUserDtoById(1).get();

		connectionToInit.setUserConnection(userDtoconnection);
		connectionToInit.setConnectedUser(userDtoConnected);
		connectionToInit.setNameConnectionUser("Test4");

		connectionService.saveConnection(connectionToInit);

	}

	@AfterAll
	public void resetConnectionData() {
		User user = userService.getUserById(7).get();
		connectionController.deleteConnectionsByUser(user);

	}

	@Test
	public void testSaveConnection() {
		ConnectionDto connectionDtoToSave = new ConnectionDto();
		connectionDtoToSave.setUserId(7);
		connectionDtoToSave.setConnectionName("Test");
		connectionDtoToSave.setConnectedUserEmail("jean.bon@mail.com");

		Model model = mock(Model.class);
		httpSession.setAttribute("userId", 7);

		String response = connectionController.saveConnection(connectionDtoToSave, model, httpSession);

		assertThat(response.equals("Test"));

	}

	@Test
	public void testGetConnections() {
		Collection<Connection> connectionsList = connectionController.getConnections();

		assertTrue(connectionsList.iterator().hasNext());
	}

	@Test
	public void testGetConnectionsByUserId() {
		Collection<Connection> connectionsListForAUser = connectionController.getConnectionsByUserId(7);

		assertThat(connectionsListForAUser.size()).isBetween(1, 3);
	}

	@Test
	public void testDeleteConnectionByUserIdAndConnectedUserId() {
		httpSession.setAttribute("userId", 7);

		connectionController.deleteConnectionByUserIdAndConnectedUserId(httpSession, 5);
		Collection<Connection> connectionsList = connectionController.getConnectionsByUserId(7);
		assertThat(connectionsList.size()).isEqualTo(1);
	}

	@Test
	public void testDeleteConnectionsByUser() {
		User user = userService.getUserById(1).get();
		connectionController.deleteConnectionsByUser(user);

		Collection<Connection> connectionList = connectionController.getConnectionsByUserId(1);

		assertFalse(connectionList.iterator().hasNext());
	}

}
