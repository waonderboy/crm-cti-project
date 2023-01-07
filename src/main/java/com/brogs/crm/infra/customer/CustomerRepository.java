package com.brogs.crm.infra.customer;

import com.brogs.crm.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findBySnsId(String snsId);

    Optional<Customer> findBySnsIdAndSnsType(String snsId, Customer.SnsType snsType);

    Customer saveAndFlush(Customer customer);
}
