package com.openclassrooms.paymybuddy.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.repository.FeeRepository;

@Service
public class FeeService {

	@Autowired
	private FeeRepository feeRepository;

	public Collection<Fee> getFees() {
		return feeRepository.findAll();
	}

	public Optional<Fee> getFeeById(int id) {
		return feeRepository.findById(id);
	}

	public Fee getFeeForToday() {
		LocalDate today = LocalDate.now();

		return feeRepository.findByDate(today);
	}

	public Fee getFeeForTransactionDate(LocalDate transactionDate) {
		return feeRepository.findBytransactionDate(transactionDate);
	}

	public Fee saveFee(Fee fee) {
		return feeRepository.save(fee);
	}

	public void deleteFeeByFee(Fee fee) {
		feeRepository.delete(fee);
	}

}
