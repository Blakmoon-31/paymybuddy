package com.openclassrooms.paymybuddy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {

	@Autowired
	private UserController userController;

	@Test
	@Order(1)
	public void testSaveUser() {
		User userToSave = new User();

		userToSave.setEmail("test.test@test.com");
		userToSave.setFirstName("John");
		userToSave.setLastName("Test");
		userToSave.setPassword("toto");

		userToSave = userController.saveUser(userToSave);

		assertTrue(userToSave.getUserId() > 0);

	}

	@Test
	@Order(2)
	public void testGetUsers() {
		Iterable<User> usersList = userController.getUsers();

		assertTrue(usersList.iterator().hasNext());

	}

	@Test
	@Order(3)
	public void testGetUserById() {
		Optional<User> userFound = userController.getUserById(1);

		assertTrue(userFound.get().getFirstName().equals("Hayley"));
	}

	@Test
	@Order(4)
	public void testDeleteUserWithoutTransaction() {
		Optional<User> userFound = userController.getUserByEmail("test.test@test.com");
		User userToDelete = userFound.get();

		String response = userController.deleteUser(userToDelete);

		Optional<User> userDeleted = userController.getUserById(userToDelete.getUserId());

		assertTrue(userDeleted.isEmpty());
		assertThat(response).isEqualTo("User deleted");
	}

	@Test
	@Order(5)
	public void testDeleteUserWithTransactionWontDelete() {
		Optional<User> userFound = userController.getUserById(5);
		User userToTryToDelete = userFound.get();

		String response = userController.deleteUser(userToTryToDelete);

		Optional<User> userStillPresent = userController.getUserById(5);

		assertTrue(userStillPresent.isPresent());
		assertThat(response).isEqualTo("User not deleted : there are transactions for this user");
	}
}
