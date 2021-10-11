package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.UserDto;

public interface MapUserDtoRepository extends JpaRepository<UserDto, Integer> {

}
