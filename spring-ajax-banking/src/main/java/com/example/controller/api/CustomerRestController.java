package com.example.controller.api;

import com.example.Utils.AppUtils;
import com.example.exception.EmailExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Customer;
import com.example.model.DTO.CustomerDTO;
import com.example.model.DTO.DepositDTO;
import com.example.model.Deposit;
import com.example.service.Customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {

        List<CustomerDTO> customerDTOS = customerService.findAllByDeletedIsFalseDTO();

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> findById(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer is invalid");
        }
        Customer customer = customerOptional.get();
        CustomerDTO customerDTO = customer.toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CustomerDTO customerDTO) {

        Boolean existsEmail = customerService.existsByEmail(customerDTO.getEmail());
        if (existsEmail) {
            throw new EmailExistsException("Email is exists");
        }
        customerDTO.setId(null);
        customerDTO.setBalance(BigDecimal.ZERO);
        customerDTO.getLocationRegion().setId(null);

        Customer customer = customerDTO.toCustomer();
        customer = customerService.save(customer);

        customerDTO = customer.toCustomerDTO();
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<?> update(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer not found");
        }

        Customer customer = customerDTO.toCustomer();

        customer.setId(customerId);
        customer.setDeleted(customerDTO.isDeleted());
        customer.setBalance(customerOptional.get().getBalance());
        customerService.save(customer);
        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/deposits/{customerId}")
    public ResponseEntity<?> deposit(@PathVariable Long customerId, @RequestBody DepositDTO depositDTO) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer = customerOptional.get();

        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(depositDTO.getTransactionAmount()));

        Deposit deposit = new Deposit();
        deposit.setTransactionAmount(transactionAmount);
        deposit.setCustomer(customer);

        customerService.deposit(deposit);
        customer = customerService.findById(customerId).get();

        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

}
