package com.openclassrooms.paymybuddy.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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

	public Fee saveFee(Fee newFee) {

		// Search existing fee with no end date
		List<Fee> feesList = feeRepository.findAll();
		if (feesList.size() > 0) {
			for (Fee f : feesList) {
				if (f.getEndDate() == null) {
					// Update this fee with an end date equals to the day before start date of new
					// fee
					Fee formerFee = f;
					formerFee.setEndDate(newFee.getStartDate().minusDays(1));
					feeRepository.save(formerFee);
				}
			}
		} else {
			// If list is empty, so newFee is the first one, creating a fee for before with
			// a rate = 0 and ending the day before newFee start date
			Fee firstFee = new Fee();
			firstFee.setRatePercentage(0.00);
			firstFee.setEndDate(newFee.getStartDate().minusDays(1));
			feeRepository.save(firstFee);
		}

		return feeRepository.save(newFee);
	}

	public void deleteFeeByFee(Fee fee) {
		feeRepository.delete(fee);
	}

}
