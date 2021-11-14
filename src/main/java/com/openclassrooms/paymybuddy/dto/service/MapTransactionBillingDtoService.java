package com.openclassrooms.paymybuddy.dto.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.model.TransactionBillingDto;
import com.openclassrooms.paymybuddy.dto.repository.MapTransactionBillingDtoRepository;
import com.openclassrooms.paymybuddy.service.FeeService;
import com.openclassrooms.paymybuddy.service.UserService;

@Service
public class MapTransactionBillingDtoService {

	@Autowired
	private MapTransactionBillingDtoRepository mapTransactionBillingDtoRepository;

	@Autowired
	private FeeService feeService;

	@Autowired
	private UserService userService;

	public Collection<TransactionBillingDto> getTransactionsNotBilled() {

		List<TransactionBillingDto> transactionsNotBilled = mapTransactionBillingDtoRepository.findByBilled(false);
		for (TransactionBillingDto transaction : transactionsNotBilled) {
			transaction.setFeeRate(feeService.getFeeById(transaction.getFeeId()).get().getRatePercentage());
			transaction.setUserEmail(userService.getUserById(transaction.getSenderUserId()).get().getEmail());
		}

		return transactionsNotBilled;
	}

}
