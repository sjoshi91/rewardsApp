package com.infy.runner;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.infy.entity.Customer;
import com.infy.entity.Transaction;
import com.infy.repo.CustomerRepository;
import com.infy.repo.TransactionRepository;

@Component
public class DataLoader implements CommandLineRunner {
	
	@Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private TransactionRepository transactionRepo;

	@Override
	public void run(String... args) throws Exception {
		Customer customer1 = new Customer();
        customer1.setName("John Doe");

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");

        customerRepo.saveAll(Arrays.asList(customer1, customer2));

        Transaction t1 = new Transaction();
        t1.setDate(LocalDate.now().minusMonths(1));
        t1.setAmount(120);
        t1.setCustomer(customer1);

        Transaction t2 = new Transaction();
        t2.setDate(LocalDate.now().minusMonths(2));
        t2.setAmount(75);
        t2.setCustomer(customer1);

        Transaction t3 = new Transaction();
        t3.setDate(LocalDate.now().minusMonths(1));
        t3.setAmount(200);
        t3.setCustomer(customer2);

        transactionRepo.saveAll(Arrays.asList(t1, t2, t3));
	}

}
