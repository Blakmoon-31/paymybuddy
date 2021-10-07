package com.openclassrooms.paymybuddy.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Fee;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

	@Query("FROM Fee WHERE (endDate is null OR endDate >= :today) AND (startDate is null OR startDate <= :today)")
	Fee findByDate(LocalDate today);

	@Query("FROM Fee WHERE (endDate is null OR endDate >= :transactionDate) AND (startDate is null OR startDate <= :transactionDate)")
	Fee findBytransactionDate(LocalDate transactionDate);

}
