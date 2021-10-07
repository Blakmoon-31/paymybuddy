package com.openclassrooms.paymybuddy.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;

@Service
public class ConnectionService {

	private static Logger logger = LoggerFactory.getLogger(ConnectionService.class);

	@Autowired
	private ConnectionRepository connectionRepository;

	public Collection<Connection> getUserConnectionsByid(int userId) {
		logger.info("Étape ConnectionService findById envoie recherche");
		Collection<Connection> connectionsList = connectionRepository.findByConnectionIdUserId(userId);
		logger.info("Étape ConnectionService findById retour résultat");
		return connectionsList;
	}

	public Collection<Connection> getConnections() {
		logger.info("Étape ConnectionService findAll envoie recherche");
		Collection<Connection> connectionsAll = connectionRepository.findAll();
		logger.info("Étape ConnectionService findAll retour résultat");
		return connectionsAll;
	}

}
