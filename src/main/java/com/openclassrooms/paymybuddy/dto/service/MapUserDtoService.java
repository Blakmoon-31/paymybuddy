package com.openclassrooms.paymybuddy.dto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.model.UserDto;
import com.openclassrooms.paymybuddy.dto.repository.MapUserDtoRepository;

@Service
public class MapUserDtoService {

	@Autowired
	private MapUserDtoRepository mapUserDtoRepository;

	public Optional<UserDto> getUserDtoById(int id) {
		return mapUserDtoRepository.findById(id);
	}

	public Optional<UserDto> getUserDtoByEmail(String email) {
		return mapUserDtoRepository.findByEmail(email);
	}

}
