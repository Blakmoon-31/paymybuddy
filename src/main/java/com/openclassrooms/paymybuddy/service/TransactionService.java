package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Collection<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	public Optional<Transaction> getTranscationById(int id) {
		return transactionRepository.findById(id);
	}

	public List<Transaction> getTransactionBySenderUser(User senderUser) {
		return transactionRepository.findBySenderUser(senderUser);
	}

	public List<Transaction> getTransactionByRecipientUser(User recipientUser) {
		return transactionRepository.findByRecipientUser(recipientUser);
	}

	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

}
