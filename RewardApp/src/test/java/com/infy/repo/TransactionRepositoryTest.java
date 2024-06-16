package com.infy.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.infy.entity.Customer;
import com.infy.entity.Transaction;
import com.infy.repo.CustomerRepository;
import com.infy.repo.TransactionRepository;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {
	
	@Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private CustomerRepository customerRepo;
    
    @Test
    public void testFindTransactionsByCustomerIdAndDateBetween() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customerRepo.save(customer);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(120);
        transaction1.setDate(LocalDate.now().minusMonths(1));
        transaction1.setCustomer(customer);
        transactionRepo.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(75);
        transaction2.setDate(LocalDate.now().minusMonths(2));
        transaction2.setCustomer(customer);
        transactionRepo.save(transaction2);

        LocalDate startDate = LocalDate.now().minusMonths(3).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        List<Transaction> transactions = transactionRepo.findByCustomerIdAndDateBetween(customer.getId(), startDate, endDate);
        assertThat(transactions).hasSize(2);
    }

}
