package com.openclassrooms.paymybuddy.dtorepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.UserDto;

public interface MapUserDtoRepository extends JpaRepository<UserDto, Integer> {

	Optional<UserDto> findByEmail(String email);

}
