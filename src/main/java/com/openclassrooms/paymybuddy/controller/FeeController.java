package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.service.FeeService;

@Controller
public class FeeController {

	@Autowired
	private FeeService feeService;

	public Collection<Fee> getFees() {
		return feeService.getFees();
	}

	public Optional<Fee> getFeeById(int feeId) {
		return feeService.getFeeById(feeId);
	}

	public Fee saveFee(Fee fee) {
		return feeService.saveFee(fee);
	}

	public void deleteFee(Fee fee) {
		feeService.deleteFeeByFee(fee);
	}
}
