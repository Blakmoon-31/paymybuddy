package com.openclassrooms.paymybuddy.dto;

import com.openclassrooms.paymybuddy.model.User;

public class MapUserDto {

	public UserDto convertUserToUserDto(User userToConvert) {

		UserDto convertedUserDto = new UserDto();

		convertedUserDto.setUserId(userToConvert.getUserId());
		convertedUserDto.setEmail(userToConvert.getEmail());
		convertedUserDto.setFirstName(userToConvert.getFirstName());
		convertedUserDto.setLastName(userToConvert.getLastName());

		return convertedUserDto;
	}
}
