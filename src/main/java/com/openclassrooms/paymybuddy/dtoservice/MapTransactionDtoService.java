package com.openclassrooms.paymybuddy.dtoservice;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.dtorepository.MapTransactionDtoRepository;
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

}
