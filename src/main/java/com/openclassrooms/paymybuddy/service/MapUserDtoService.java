package com.openclassrooms.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.MapUserDtoRepository;

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

	public UserDto convertUserToUserDto(User userToConvert) {

		UserDto convertedUserDto = new UserDto();

		convertedUserDto.setUserId(userToConvert.getUserId());
		convertedUserDto.setEmail(userToConvert.getEmail());
		convertedUserDto.setFirstName(userToConvert.getFirstName());
		convertedUserDto.setLastName(userToConvert.getLastName());

		return convertedUserDto;
	}

}
