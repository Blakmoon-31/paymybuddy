package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.dtoservice.MapConnectionDtoService;
import com.openclassrooms.paymybuddy.dtoservice.MapTransactionDtoService;
import com.openclassrooms.paymybuddy.dtoservice.MapUserDtoService;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class MainWebController {

	@Autowired
	private UserService userService;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private MapTransactionDtoService mapTransactionDtoService;

	@Autowired
	private MapConnectionDtoService mapConnectionDtoService;

	@GetMapping("/register")
	public String showRegisterForm(User user) {
		return "register";
	}

	@GetMapping({ "/login", "/" })
	public String showLoginForm(User user) {
		return "login";
	}

	@GetMapping({ "/logoff" })
	public String logOffSession(User user, HttpSession httpSession) {
		httpSession.removeAttribute("userId");
		return "redirect:login";
	}

	@PostMapping("/login")
	public String loginValidate(User userLogin, BindingResult result, Model model, HttpSession httpSession) {
		Optional<User> userExists = userService.getUserByEmail(userLogin.getEmail());

		if (userExists.isEmpty()) {
			ObjectError error = new ObjectError("globalError", "User not found");
			result.addError(error);
			return "login";
		} else if (!userExists.get().getPassword().equals(userLogin.getPassword())) {
			ObjectError error = new ObjectError("globalError", "Wrong email or password");
			result.addError(error);
			return "login";
		}

		httpSession.setAttribute("userId", userExists.get().getUserId());
		model.addAttribute("user", userExists.get());

		model.addAttribute("connectionsList",
				mapConnectionDtoService.getConnectionsDtoByUserId(userExists.get().getUserId()));

		Optional<UserDto> userDto = mapUserDtoService.getUserDtoById(userExists.get().getUserId());
		model.addAttribute("transactionsList",
				mapTransactionDtoService.getTransactionsDtoBySenderId(userDto.get().getUserId()));

		return "redirect:transfer";
	}

	@GetMapping("/transfer")
	public String showTransferPage(Model model, HttpSession httpSession) {

		// Control if there is an active session, if not, back to login
		if (httpSession.getAttribute("userId") == null) {
			return "login";
		} else {
			int userId = (int) httpSession.getAttribute("userId");
			Optional<UserDto> userDto = mapUserDtoService.getUserDtoById(userId);
			model.addAttribute("transactionsList",
					mapTransactionDtoService.getTransactionsDtoBySenderId(userDto.get().getUserId()));

			model.addAttribute("connectionsList", mapConnectionDtoService.getConnectionsDtoByUserId(userId));

			Optional<User> user = userService.getUserById(userId);
			model.addAttribute("user", user.get());
			return "transfer";
		}
	}

	@GetMapping("/profil")
	public String showProfilPage(Model model, HttpSession httpSession) {
		// Control if there is an active session, if not, back to login
		if (httpSession.getAttribute("userId") == null) {
			return "login";
		} else {
			int userId = (int) httpSession.getAttribute("userId");
			model.addAttribute("user", userService.getUserById(userId).get());
			model.addAttribute("connectionsList", mapConnectionDtoService.getConnectionsDtoByUserId(userId));
			return "profil";
		}
	}

	@GetMapping("connection")
	public String showAddConnectionPage(Model model, HttpSession httpSession) {
		Collection<ConnectionDto> connectionsList = mapConnectionDtoService
				.getConnectionsDtoByUserId((int) httpSession.getAttribute("userId"));
		model.addAttribute("connectionsList", connectionsList);
		return "connection";
	}
}
