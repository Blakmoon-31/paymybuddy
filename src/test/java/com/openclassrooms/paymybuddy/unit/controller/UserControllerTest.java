package com.openclassrooms.paymybuddy.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.openclassrooms.paymybuddy.controller.UserController;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@BeforeEach
	private void setUpPerTest() {
		User user = new User();
		user.setUserId(9);
		user.setEmail("user@test.com");
		user.setFirstName("Charles");
		user.setLastName("Martel");
		user.setPassword("toto");

		Optional<User> testUser = Optional.of(user);

		when(userService.saveUser(any(User.class))).thenReturn(null);

	}

	@Test
	public void testSaveUserEmailExists() {
		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		User userToTest = new User();
		userToTest.setUserId(99);
		userToTest.setFirstName("John");
		userToTest.setLastName("Doe");
		userToTest.setEmail("user@test.com");

		String response = userController.saveUser(userToTest, result, model);
		assertThat(response.equals("register"));
	}
}
