package com.infy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.entity.Customer;
import com.infy.entity.Transaction;
import com.infy.repo.CustomerRepository;
import com.infy.repo.TransactionRepository;

@SpringBootTest
public class RewardServiceTest {
	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardService rewardService;

	private Customer customer;
	private Transaction transaction1;
	private Transaction transaction2;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		customer = new Customer();
		customer.setId(1L);
		customer.setName("Test Customer");

		transaction1 = new Transaction();
		transaction1.setAmount(120);
		transaction1.setDate(LocalDate.now().minusDays(10));
		transaction1.setCustomer(customer);

		transaction2 = new Transaction();
		transaction2.setAmount(75);
		transaction2.setDate(LocalDate.now().minusDays(40));
		transaction2.setCustomer(customer);

		when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(customer));
	}

	@Test
	public void testCalculateMonthlyPoints() {
		when(transactionRepository.findByCustomerIdAndDateBetween(1L, LocalDate.now().withMonth(1).withDayOfMonth(1),
				LocalDate.now().withMonth(1).withDayOfMonth(LocalDate.now().withMonth(1).lengthOfMonth())))
				.thenReturn(Arrays.asList(transaction1));

		when(transactionRepository.findByCustomerIdAndDateBetween(1L, LocalDate.now().withMonth(2).withDayOfMonth(1),
				LocalDate.now().withMonth(2).withDayOfMonth(LocalDate.now().withMonth(2).lengthOfMonth())))
				.thenReturn(Arrays.asList(transaction2));

		Map<String, Integer> monthlyPoints = rewardService.calculateMonthlyPoints(1L);
		assertThat(monthlyPoints.get("JANUARY")).isEqualTo(90);
		assertThat(monthlyPoints.get("FEBRUARY")).isEqualTo(25);
	}

	@Test
	public void testCalculateTotalPoints() {
		LocalDate startDate = LocalDate.now().minusMonths(3);
		LocalDate endDate = LocalDate.now();
		when(transactionRepository.findByCustomerIdAndDateBetween(1L, startDate, endDate))
				.thenReturn(Arrays.asList(transaction1, transaction2));

		int totalPoints = rewardService.calculateTotalPoints(1L);
		assertThat(totalPoints).isEqualTo(115);
	}
}
