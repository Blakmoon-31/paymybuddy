package com.openclassrooms.paymybuddy.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.ConnectionId;

public interface ConnectionRepository extends JpaRepository<Connection, ConnectionId> {

	Collection<Connection> findByUserConnection(UserDto userDto);

	@Transactional
	void deleteAllByUserConnection(UserDto userDto);

}
