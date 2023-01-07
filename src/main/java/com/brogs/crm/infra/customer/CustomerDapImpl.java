package com.brogs.crm.infra.customer;


import com.brogs.crm.domain.customer.Customer;
import com.brogs.crm.domain.customer.CustomerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerDapImpl implements CustomerDao {

    private final CustomerRepository customerRepository;
    @Override
    public Optional<Customer> findBySnsId(String snsId) {
        return customerRepository.findBySnsId(snsId);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findBySnsIdAndSnsType(String snsId, Customer.SnsType snsType) {
        return customerRepository.findBySnsIdAndSnsType(snsId, snsType);
    }

    @Override
    public Customer saveAndFlush(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }
}
