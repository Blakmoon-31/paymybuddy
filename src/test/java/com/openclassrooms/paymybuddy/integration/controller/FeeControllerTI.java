package com.openclassrooms.paymybuddy.integration.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.controller.FeeController;
import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.repository.FeeRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FeeControllerTI {

	@Autowired
	private FeeController feeController;

	@Autowired
	private FeeRepository feeRepository;

	@BeforeAll
	public void initFeeData() {
		Fee feeToInit = new Fee();

		feeToInit.setRatePercentage(0.6);
		feeToInit.setStartDate(LocalDate.of(2021, 01, 01));

		feeToInit = feeController.saveFee(feeToInit);
	}

	@AfterAll
	public void resetFeeData() {
		Collection<Fee> feesList = feeController.getFees();
		for (Fee f : feesList) {
			if (f.getRatePercentage() == 0.5) {
				f.setEndDate(null);
				feeRepository.save(f);
			} else {
				if (f.getRatePercentage() == 9.99) {
					feeRepository.delete(f);
				}
			}

		}
	}

	@Test
	public void testSaveFee() {
		Fee newFee = new Fee();

		newFee.setRatePercentage(9.99);
		newFee.setStartDate(LocalDate.now());

		newFee = feeController.saveFee(newFee);

		assertTrue(newFee.getId() != 0);

	}

	@Test
	public void testGetFees() {
		Iterable<Fee> feesList = feeController.getFees();

		assertTrue(feesList.iterator().hasNext());
	}

	@Test
	public void testGetFeeById() {
		Optional<Fee> fee = feeController.getFeeById(2);

		assertTrue(fee.get().getRatePercentage() == 0.4);
	}

	@Test
	public void testDeleteByFee() {
		Iterable<Fee> feesList = feeController.getFees();
		Fee feeToDelete = new Fee();

		for (Fee f : feesList) {
			if (f.getRatePercentage() == 0.6) {
				feeToDelete = f;
			}
		}

		feeController.deleteFee(feeToDelete);

		Optional<Fee> feeDeleted = feeController.getFeeById(feeToDelete.getId());

		assertTrue(feeDeleted.isEmpty());
	}

}
