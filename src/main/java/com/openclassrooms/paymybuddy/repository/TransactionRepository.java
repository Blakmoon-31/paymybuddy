package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findBySenderUser(User senderUser);

	List<Transaction> findByRecipientUser(User recipientUser);

	void deleteBySenderUser(User userSender);
}
