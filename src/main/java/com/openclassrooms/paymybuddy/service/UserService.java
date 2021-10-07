package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Collection<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void deleteByUser(User user) {
		userRepository.delete(user);
	}

}
