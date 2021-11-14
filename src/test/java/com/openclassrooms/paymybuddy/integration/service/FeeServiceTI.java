package com.openclassrooms.paymybuddy.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.service.FeeService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FeeServiceTI {

	@Autowired
	private FeeService feeService;

	@Test
	public void testGetFeeForToday() {
		Fee feeForToday = feeService.getFeeForToday();

		assertThat(feeForToday.getRatePercentage()).isEqualTo(0.5);
	}

}
