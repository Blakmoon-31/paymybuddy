package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.paymybuddy.dto.model.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.model.TransactionDto;
import com.openclassrooms.paymybuddy.dto.service.MapConnectionDtoService;
import com.openclassrooms.paymybuddy.dto.service.MapTransactionBillingDtoService;
import com.openclassrooms.paymybuddy.dto.service.MapTransactionDtoService;
import com.openclassrooms.paymybuddy.model.Role;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.FeeService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class MainWebController {

	@Autowired
	private UserService userService;

	@Autowired
	private MapTransactionDtoService mapTransactionDtoService;

	@Autowired
	private MapConnectionDtoService mapConnectionDtoService;

	@Autowired
	private MapTransactionBillingDtoService mapTransactionBillingDtoService;

	@Autowired
	private FeeService feeService;

	@GetMapping("/register")
	public String showRegisterForm(User user) {
		return "/register";
	}

	@GetMapping("/login")
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
		Role userRole = userConnected.getUserRole();

		httpSession.setAttribute("userId", userConnected.getUserId());
		httpSession.setAttribute("userRole", userRole.getRoleName());

		return "redirect:/transfer";
	}

	@GetMapping("/transfer")
	public String showTransferPage(Model model, HttpSession httpSession) {

		// Controls if there is an active session, if not, back to login
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

	@GetMapping("/billing")
	public String showBillingPage(Model model, HttpSession httpSession) {

		// Controls if there is an active session, if not, back to login
		if (httpSession.getAttribute("userId") == null) {
			model.addAttribute("errorNotConnected", "You must connect first");
			return "/login";
		} else {
			int userId = (int) httpSession.getAttribute("userId");
			model.addAttribute("transactionsToBillList", mapTransactionBillingDtoService.getTransactionsNotBilled());
			model.addAttribute("feeList", feeService.getFees());

			User user = userService.getUserById(userId).get();
			model.addAttribute("user", user);

			return "/billing";
		}
	}

	@GetMapping("/profil")
	public String showProfilPage(Model model, HttpSession httpSession) {
		// Controls if there is an active session, if not, back to login
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
