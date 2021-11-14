package com.openclassrooms.paymybuddy.dto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.dto.model.TransactionBillingDto;

public interface MapTransactionBillingDtoRepository extends JpaRepository<TransactionBillingDto, Integer> {

	List<TransactionBillingDto> findByBilled(boolean billed);

}
