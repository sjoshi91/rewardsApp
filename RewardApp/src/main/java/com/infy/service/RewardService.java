package com.infy.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.entity.Transaction;
import com.infy.repo.TransactionRepository;

@Service
public class RewardService {
	
	@Autowired
	private TransactionRepository repo;

	public Map<String, Integer> calculateMonthlyPoints(Long customerId) {
		Map<String, Integer> monthlyPoints = new HashMap<>();

		for (Month month : Month.values()) {
			LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), month, 1);
			LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
			List<Transaction> transactions = repo.findByCustomerIdAndDateBetween(customerId, startDate, endDate);

			int points = transactions.stream().mapToInt(this::calculatePoints).sum();

			monthlyPoints.put(month.name(), points);
		}

		return monthlyPoints;
	}

	public int calculatePoints(Transaction transaction) {
		double amount = transaction.getAmount();
		int points = 0;
		if (amount > 100) {
			points += (amount - 100) * 2;
			amount = 100;
		}
		if (amount > 50) {
			points += (amount - 50);
		}
		return points;
	}

	public int calculateTotalPoints(Long customerId) {
		LocalDate startDate = LocalDate.now().minusMonths(3);
		LocalDate endDate = LocalDate.now();
		List<Transaction> transactions = repo.findByCustomerIdAndDateBetween(customerId, startDate, endDate);

		return transactions.stream().mapToInt(this::calculatePoints).sum();
	}

}
