package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Controller
public class ConnectionController {

	private static Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	private ConnectionService connectionService;

//	@GetMapping("/connections/{userId}")
	public Collection<Connection> getConnectionById(int userId) {
		logger.info("Étape ConnectionController by id");
		return connectionService.getUserConnectionsByid(userId);
	}

//	@GetMapping("/connections")
	public Collection<Connection> getConnections() {
		logger.info("Étape ConnectionController All");
		return connectionService.getConnections();
	}

}
