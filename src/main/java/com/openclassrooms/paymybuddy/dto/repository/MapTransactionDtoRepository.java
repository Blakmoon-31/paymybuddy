package com.openclassrooms.paymybuddy.dto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.model.TransactionDto;

public interface MapTransactionDtoRepository extends JpaRepository<TransactionDto, Integer> {

	List<TransactionDto> findBySenderUserId(int senderId);

	List<TransactionDto> findByRecipientUserId(int recipientId);

}
