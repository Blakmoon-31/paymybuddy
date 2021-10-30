package com.openclassrooms.paymybuddy.dtorepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.TransactionDto;

public interface MapTransactionDtoRepository extends JpaRepository<TransactionDto, Integer> {

	List<TransactionDto> findBySenderUserId(int senderId);

}
