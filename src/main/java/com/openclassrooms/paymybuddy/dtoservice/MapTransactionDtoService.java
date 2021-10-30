package com.openclassrooms.paymybuddy.dtoservice;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.dtorepository.MapTransactionDtoRepository;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Service
public class MapTransactionDtoService {

	@Autowired
	private MapTransactionDtoRepository mapTransactionDtoRepository;

	@Autowired
	private ConnectionService connectionService;

	public List<TransactionDto> getTransactionsDtoBySenderId(int senderId) {

		List<TransactionDto> transactionsList = mapTransactionDtoRepository.findBySenderUserId(senderId);
		Collection<Connection> connectionsList = connectionService.getUserConnectionsByUserId(senderId);

		for (TransactionDto transaction : transactionsList) {
			int recipientId = transaction.getRecipientUserId();

			for (Connection connection : connectionsList) {

				if (recipientId == connection.getConnectedUser().getUserId()) {
					transaction.setRecipientConnectionName(connection.getNameConnectionUser());
				}
			}
		}

		return transactionsList;
	}

}
