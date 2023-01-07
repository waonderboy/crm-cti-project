package com.brogs.crm.domain.customer;

import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> findBySnsId(String snsId);

    Customer save(Customer customer);

    Optional<Customer> findBySnsIdAndSnsType(String snsId, Customer.SnsType snsType);

    Customer saveAndFlush(Customer customer);
}
