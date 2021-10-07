package com.openclassrooms.paymybuddy.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.model.Fee;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class FeeControllerTest {

	@Autowired
	private FeeController feeController;

	@Test
	@Order(1)
	public void testSaveFee() {
		Fee newFee = new Fee();

		newFee.setRatePercentage(9.99);
		newFee.setStartDate(LocalDate.now());

		newFee = feeController.saveFee(newFee);

		assertTrue(newFee.getId() != 0);

	}

	@Test
	@Order(2)
	public void testGetFees() {
		Iterable<Fee> feesList = feeController.getFees();

		assertTrue(feesList.iterator().hasNext());
	}

	@Test
	@Order(3)
	public void testGetFeeById() {
		Optional<Fee> fee = feeController.getFeeById(2);

		assertTrue(fee.get().getRatePercentage() == 0.4);
	}

	@Test
	@Order(4)
	public void testDeleteByFee() {
		Iterable<Fee> feesList = feeController.getFees();
		Fee feeToDelete = new Fee();

		for (Fee f : feesList) {
			if (f.getRatePercentage() == 9.99) {
				feeToDelete = f;
			}
		}

		feeController.deleteFee(feeToDelete);

		Optional<Fee> feeDeleted = feeController.getFeeById(feeToDelete.getId());

		assertTrue(feeDeleted.isEmpty());
	}

}
