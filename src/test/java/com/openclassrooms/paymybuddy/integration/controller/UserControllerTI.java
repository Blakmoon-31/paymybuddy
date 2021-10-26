package com.openclassrooms.paymybuddy.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.controller.UserController;
import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTI {

	@Autowired
	private UserController userController;

	@BeforeAll
	public void initUserData() {
		User userForTest = new User();

		userForTest.setEmail("martel.charles@test.com");
		userForTest.setFirstName("Charles");
		userForTest.setLastName("Martel");
		userForTest.setPassword("toto");

		userController.saveUser(userForTest);
	}

	@AfterAll
	public void resetUserData() {
		Optional<User> userFound = userController.getUserByEmail("test.test@test.com");
		User userToReset = userFound.get();

		userController.deleteUser(userToReset);

	}

	@Test
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
	public void testGetUsers() {
		Iterable<User> usersList = userController.getUsers();

		assertTrue(usersList.iterator().hasNext());

	}

	@Test
	public void testGetUserById() {
		Optional<User> userFound = userController.getUserById(1);

		assertTrue(userFound.get().getFirstName().equals("Hayley"));
	}

	@Test
	public void testDeleteUserWithoutTransaction() {
		Optional<User> userFound = userController.getUserByEmail("martel.charles@test.com");
		User userToDelete = userFound.get();

		String response = userController.deleteUser(userToDelete);

		Optional<User> userDeleted = userController.getUserById(userToDelete.getUserId());

		assertTrue(userDeleted.isEmpty());
		assertThat(response).isEqualTo("User deleted");
	}

	@Test
	public void testDeleteUserWithTransactionWontDelete() {
		Optional<User> userFound = userController.getUserById(5);
		User userToTryToDelete = userFound.get();

		String response = userController.deleteUser(userToTryToDelete);

		Optional<User> userStillPresent = userController.getUserById(5);

		assertTrue(userStillPresent.isPresent());
		assertThat(response).isEqualTo("User not deleted : there are transactions for this user");
	}
}
