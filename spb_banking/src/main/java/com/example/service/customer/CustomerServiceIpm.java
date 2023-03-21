package com.example.service.customer;

import com.example.exception.ResourceNotFoundException;
import com.example.model.*;
import com.example.model.dto.CustomerDTO;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceIpm implements ICustomerService {
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
    public List<Customer> findAllByIdNot(long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Customer> findAllByIdNotAndDeletedIsFalse(Long id) {
        return null;
    }

    @Override
    public List<CustomerDTO> findAllByDeletedIsFalseDTO() {
        return customerRepository.findAllByDeletedIsFalseDTO();
    }

    @Override
    public List<Customer> findAllByDeletedIsFalseAndIdNot(Long senderId) {
        return customerRepository.findAllByDeletedIsFalseAndIdNot(senderId);
    }

    @Override
    public List<CustomerDTO> findAllCustomerDTO() {
        return null;
    }

    @Override
    public void deposit(Customer customer, Deposit deposit) {
        Long customerId = customer.getId();
        BigDecimal transactionAmount = deposit.getTransactionAmount();

        deposit.setCustomer(customer);
        depositRepository.save(deposit);
        customerRepository.incrementBalance(customerId, transactionAmount);
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
        Long senderId = transfer.getSender().getId();
        BigDecimal transactionAmount = transfer.getTransferAmount();
        customerRepository.decrementBalance(senderId, transactionAmount);

        Long recipient = transfer.getRecipient().getId();
        BigDecimal transferAmount = transfer.getTransferAmount();
        customerRepository.incrementBalance(recipient, transferAmount);
        transferRepository.save(transfer);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO, Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer not found");
        }
        Customer customer = customerOptional.get();
        if (customerDTO.isDeleted() == true) {
            customer.setDeleted(customerDTO.isDeleted());
        }
        if (customerOptional.get().getBalance() != null) {
            customer.setBalance(customerOptional.get().getBalance());
        }
        if (customerDTO.getEmail() != null) {
            customer.setEmail(customerDTO.getEmail());
        }
        if (customerDTO.getFullName() != null) {
            customer.setFullName(customerDTO.getFullName());
        }
        if (customerDTO.getPhone() != null) {
            customer.setPhone(customerDTO.getPhone());
        }
        if (customerDTO.getBalance()!=null){
            customer.setBalance(customerDTO.getBalance());
        }
        if (customerDTO.getLocationRegion() != null) {
            customer.setLocationRegion(customerDTO.getLocationRegion().toLocationRegion());
        }
        customerRepository.save(customer);
        return customer.toCustomerDTO();


    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
