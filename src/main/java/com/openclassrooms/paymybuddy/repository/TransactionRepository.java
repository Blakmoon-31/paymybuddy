package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.dto.UserDto;
import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findBySenderUserDto(UserDto senderUserDto);

	List<Transaction> findByRecipientUserDto(UserDto recipientUserDto);

	void deleteBySenderUserDto(UserDto senderUserDto);

	List<Transaction> findAllBySenderUserDtoAndBilled(UserDto senderUserDto, boolean billed);

	List<Transaction> findAllByFee(Fee fee);

}
