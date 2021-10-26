package com.openclassrooms.paymybuddy.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.exceptions.PayMyBuddyException;
import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.FeeRepository;

@Service
public class FeeService {

	@Autowired
	private FeeRepository feeRepository;

	@Autowired
	private TransactionService transactionService;

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

	@Transactional
	public Fee saveFee(Fee feeToSave) {

		// Controls if end date is >= to start date if both not null
		if (feeToSave.getStartDate() != null && feeToSave.getEndDate() != null) {
			if (feeToSave.getEndDate().isBefore(feeToSave.getStartDate()))
				throw new PayMyBuddyException("The end date can't be anterior to start date");
		}

		// If id is not empty, updates the fee
		if (feeToSave.getId() > 0) {
			// Rate can't be modified
			Fee existingFee = feeRepository.getById(feeToSave.getId());
			if (existingFee.getRatePercentage() != feeToSave.getRatePercentage())
				throw new PayMyBuddyException("The fee rate can't be modified");
			return feeRepository.save(feeToSave);

		} else {

			// Search existing fee with no end date
			List<Fee> feesList = feeRepository.findAll();
			if (feesList.size() > 0) {
				for (Fee f : feesList) {
					if (f.getEndDate() == null) {
						// Update this fee with an end date equals to the day before start date of new
						// fee
						Fee formerFee = f;
						formerFee.setEndDate(feeToSave.getStartDate().minusDays(1));
						feeRepository.save(formerFee);
					}
				}
			} else {
				// If list is empty, so newFee is the first one, creating a fee for before with
				// a rate = 0 and ending the day before newFee start date
				Fee firstFee = new Fee();
				firstFee.setRatePercentage(0.00);
				firstFee.setEndDate(feeToSave.getStartDate().minusDays(1));
				feeRepository.save(firstFee);
			}

			return feeRepository.save(feeToSave);
		}
	}

	@Transactional
	public void deleteFeeByFee(Fee fee) {
		// Fee can be delete only if assigned to no transaction
		List<Transaction> transactionforFee = transactionService.getTransactionsByFee(fee);
		if (transactionforFee.size() > 0)
			throw new PayMyBuddyException("The fee is assigned to a transaction, il can't by deleted");
		feeRepository.delete(fee);
	}

}
