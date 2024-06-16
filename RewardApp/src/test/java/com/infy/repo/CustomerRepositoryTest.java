package com.infy.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.infy.entity.Customer;
import com.infy.repo.CustomerRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository custRepo;

	@Test
	public void testFindCustomerById() {
		Customer customer = new Customer();
		customer.setName("Test Customer");
		custRepo.save(customer);

		Customer foundCustomer = custRepo.findById(customer.getId()).orElse(null);
		assertThat(foundCustomer).isNotNull();
		assertThat(foundCustomer.getName()).isEqualTo("Test Customer");
	}

}
