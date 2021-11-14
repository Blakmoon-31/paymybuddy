package com.openclassrooms.paymybuddy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.model.UserDto;
import com.openclassrooms.paymybuddy.dto.service.MapUserDtoService;
import com.openclassrooms.paymybuddy.model.Role;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private RoleService roleService;

	public Collection<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public User saveUser(User user) {

		// If the user has an id, it's an update, else it's a creation so control of the
		// email (unique constraint)
		if (user.getUserId() >= 1) {
			return userRepository.save(user);
		} else {
			Optional<User> existsEmail = userRepository.findByEmail(user.getEmail());
			if (existsEmail.isEmpty()) {
				Role role = roleService.getRoleByRoleId(1).get();
				user.setUserRole(role);
				return userRepository.save(user);
			} else {
				return null;
			}
		}

	}

	@Transactional
	public String deleteByUser(User user) {

		boolean userDeleted = false;
		UserDto userDto = mapUserDtoService.getUserDtoById(user.getUserId()).get();

		List<Transaction> transactionsAsSenderFound = transactionService.getTransactionsBySenderUserDto(userDto);

		// Verify if user has made a transaction
		if (transactionsAsSenderFound.size() == 0) {
			List<Transaction> transactionsAsRecipientFound = transactionService
					.getTransactionsByRecipientUserDto(userDto);

			// If no, verify if user has received a transaction
			if (transactionsAsRecipientFound.size() == 0) {

				// If no too, delete user
				connectionService.deleteConnectionsByUserConnection(user);
				connectionService.deleteConnectionsByConnectedUser(user);
				userRepository.delete(user);
				userDeleted = true;
			}
		}

		if (userDeleted) {
			return "User deleted";
		} else {
			return "User not deleted : there are transactions for this user";
		}

	}

	public Double getBalanceByUserId(int userId) {
		return userRepository.findBalanceByUserId(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Objects.requireNonNull(username);
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Role role = user.getUserRole();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
	}

}
