package com.openclassrooms.paymybuddy.dto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.model.UserDto;

public interface MapUserDtoRepository extends JpaRepository<UserDto, Integer> {

	Optional<UserDto> findByEmail(String email);

}
