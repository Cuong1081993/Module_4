package com.example.service.Customer;

import com.example.model.*;
import com.example.model.DTO.CustomerDTO;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private TransferRepository transferRepository;


    @Override
    public List<Customer> findAllByIdNot(long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Customer> findAllByIdNotAndDeletedIsFalse(Long id) {
        return customerRepository.findAllByIdNotAndDeletedIsFalse(id);
    }

    @Override
    public List<CustomerDTO> findAllByDeletedIsFalseDTO() {
        return customerRepository.findAllByDeletedIsFalseDTO();
    }

    @Override
    public List<CustomerDTO> findAllCustomerDTO() {
        return customerRepository.findAllCustomerDTO();
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);

        return customerRepository.save(customer);
    }

    @Override
    public void deleted(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deletedById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existByIdEqual(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Deposit deposit(Deposit deposit) {
        depositRepository.save(deposit);
        customerRepository.incrementBalance(deposit.getTransactionAmount(), deposit.getCustomer());
        return deposit;
    }

    @Override
    public void incrementBalance(BigDecimal transactionAmount, Customer customer) {
        customerRepository.incrementBalance(transactionAmount, customer);
    }

    @Override
    public void withdraw(Customer customer, Withdraw withdraw) {
        Long customerId = customer.getId();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        withdraw.setCustomer(customer);
        withdrawRepository.save(withdraw);
        customerRepository.decrementBalance(customerId, transactionAmount);
    }

    @Override
    public void transfer(Transfer transfer) {
//        Long senderId = transfer.getSender().getId();
//        BigDecimal transactionAmount = transfer.getTransactionAmount();
//        customerRepository.decrementBalance(senderId, transactionAmount);
//        Long recipient = transfer.getRecipient().getId();
//        BigDecimal transferAmount = transfer.getTransferAmount();
//        customerRepository.incrementBalance(recipient, transferAmount);
//
//        transferRepository.save(transfer);
    }


    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
