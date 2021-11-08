package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
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
		return "/register";
	}

	@GetMapping({ "/login" })
	public String showLoginForm(User user) {
		return "/login";
	}

	@GetMapping({ "/logoff" })
	public String logOffSession(User user, HttpSession httpSession) {
		httpSession.removeAttribute("userId");
		return "redirect:/login";
	}

	@GetMapping("/loginValidate")
	public String loginValidate(Model model, HttpSession httpSession) {

		String testEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		User userConnected = userService.getUserByEmail(testEmail).get();
		httpSession.setAttribute("userId", userConnected.getUserId());

		return "redirect:/transfer";
	}

	@GetMapping("/transfer")
	public String showTransferPage(Model model, HttpSession httpSession) {

		String testEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		User userConnected = userService.getUserByEmail(testEmail).get();
		httpSession.setAttribute("userId", userConnected.getUserId());

		// Control if there is an active session, if not, back to login
		if (httpSession.getAttribute("userId") == null) {
			model.addAttribute("errorNotConnected", "You must connect first");
			return "/login";
		} else {
			int userId = (int) httpSession.getAttribute("userId");
			model.addAttribute("transactionsList", mapTransactionDtoService.getTransactionsDtoBySenderId(userId));
			model.addAttribute("transactionsReceivedList",
					mapTransactionDtoService.getTransactionsDtoByRecipientId(userId));
			model.addAttribute("connectionsList", mapConnectionDtoService.getConnectionsDtoByUserId(userId));

			User user = userService.getUserById(userId).get();
			model.addAttribute("user", user);
			TransactionDto newTransaction = new TransactionDto();
			model.addAttribute("transactionDto", newTransaction);
			return "/transfer";
		}
	}

	@GetMapping("/profil")
	public String showProfilPage(Model model, HttpSession httpSession) {
		// Control if there is an active session, if not, back to login
		if (httpSession.getAttribute("userId") == null) {
			return "/login";
		} else {
			int userId = (int) httpSession.getAttribute("userId");
			model.addAttribute("user", userService.getUserById(userId).get());
			model.addAttribute("connectionsList", mapConnectionDtoService.getConnectionsDtoByUserId(userId));

			return "/profil";
		}
	}

	@GetMapping("/connection")
	public String showAddConnectionPage(Model model, HttpSession httpSession) {
		Collection<ConnectionDto> connectionsList = mapConnectionDtoService
				.getConnectionsDtoByUserId((int) httpSession.getAttribute("userId"));
		ConnectionDto connectionDtoNew = new ConnectionDto();

		model.addAttribute("connectionDto", connectionDtoNew);
		model.addAttribute("connectionsList", connectionsList);

		return "/connection";
	}

}
