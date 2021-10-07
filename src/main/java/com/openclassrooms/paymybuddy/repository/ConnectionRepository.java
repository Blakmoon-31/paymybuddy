package com.openclassrooms.paymybuddy.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.ConnectionId;

public interface ConnectionRepository extends JpaRepository<Connection, ConnectionId> {

	Collection<Connection> findByConnectionIdUserId(int userId);

}
