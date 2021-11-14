package com.openclassrooms.paymybuddy.dto.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.model.TransactionDto;
import com.openclassrooms.paymybuddy.dto.model.UserDto;
import com.openclassrooms.paymybuddy.dto.repository.MapTransactionDtoRepository;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Service
public class MapTransactionDtoService {

	@Autowired
	private MapTransactionDtoRepository mapTransactionDtoRepository;

	@Autowired
	private MapUserDtoService mapUserDtoService;

	@Autowired
	private ConnectionService connectionService;

	public List<TransactionDto> getTransactionsDtoBySenderId(int senderId) {

		List<TransactionDto> transactionsList = mapTransactionDtoRepository.findBySenderUserId(senderId);
		Collection<Connection> connectionsList = connectionService.getUserConnectionsByUserId(senderId);

		for (TransactionDto transaction : transactionsList) {
			int recipientId = transaction.getRecipientUserId();
			// First set name in transactionDto with full name in case the recipient is no
			// more in connections list
			UserDto recipientDto = mapUserDtoService.getUserDtoById(recipientId).get();
			transaction.setRecipientConnectionName(recipientDto.getFirstName() + " " + recipientDto.getLastName());

			for (Connection connection : connectionsList) {

				if (recipientId == connection.getConnectedUser().getUserId()) {
					transaction.setRecipientConnectionName(connection.getNameConnectionUser());
					transaction.setRecipientUserEmail(connection.getConnectedUser().getEmail());
				}
			}
		}

		return transactionsList;
	}

	public List<TransactionDto> getTransactionsDtoByRecipientId(int recipientId) {

		List<TransactionDto> transactionsList = mapTransactionDtoRepository.findByRecipientUserId(recipientId);
		Collection<Connection> connectionsList = connectionService.getUserConnectionsByUserId(recipientId);

		for (TransactionDto transaction : transactionsList) {
			int senderId = transaction.getSenderUserId();
			// First set name in transactionDto with full name in case the sender is no
			// more in connections list
			UserDto senderDto = mapUserDtoService.getUserDtoById(senderId).get();
			transaction.setRecipientConnectionName(senderDto.getFirstName() + " " + senderDto.getLastName());

			for (Connection connection : connectionsList) {

				if (senderId == connection.getConnectedUser().getUserId()) {
					transaction.setRecipientConnectionName(connection.getNameConnectionUser());
					transaction.setRecipientUserEmail(connection.getConnectedUser().getEmail());
				}
			}
		}

		return transactionsList;
	}

}
