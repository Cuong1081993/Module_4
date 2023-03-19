package com.example.controller.api;

import com.example.exception.DataInputException;
import com.example.exception.EmailExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.model.dto.CustomerDTO;
import com.example.model.dto.DepositDTO;
import com.example.model.dto.TransferDTO;
import com.example.model.dto.WithdrawDTO;
import com.example.service.customer.ICustomerService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {
        List<CustomerDTO> customerDTOS = customerService.findAllByDeletedIsFalseDTO();
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> findById(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer is inValid");
        }
        Customer customer = customerOptional.get();
        CustomerDTO customerDTO = customer.toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CustomerDTO customerDTO) {
        Boolean existsEmail = customerService.existsByEmail((customerDTO.getEmail()));
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
        customer.setId(null);
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

        customerService.deposit(customer,deposit);
        customer = customerService.findById(customerId).get();

        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.CREATED);
    }

    @PostMapping("/withdraw/{customerId}")
    public ResponseEntity<?> withdraw(@PathVariable Long customerId, @RequestBody WithdrawDTO withdrawDTO) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer = customerOptional.get();
        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(withdrawDTO.getTransactionAmount()));

        Withdraw withdraw = new Withdraw();
        withdraw.setTransactionAmount(transactionAmount);
        withdraw.setCustomer(customer);

        customerService.withdraw(customer,withdraw);
        customer = customerService.findById(customerId).get();
        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Validated @RequestBody TransferDTO transferDTO, BindingResult bindingResult) {
        new TransferDTO().validate(transferDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        String senderIdStr = transferDTO.getSenderId();
        String recipientIdStr = transferDTO.getRecipientId();

        Long senderId = Long.parseLong(senderIdStr);
        Long recipientId = Long.parseLong(recipientIdStr);

        Optional<Customer> senderOptional = customerService.findById(senderId);
        Customer sender = null;
        if (!senderOptional.isPresent()) {
            throw new NullPointerException("Sender invalid");
        }
        Optional<Customer> recipientOptional = customerService.findById(recipientId);
        Customer recipient = null;
        if (!recipientOptional.isPresent()) {
            throw new NullPointerException("Recipient invalid");
        }

        sender = senderOptional.get();
        recipient = recipientOptional.get();
        if (sender.equals(recipient)) {
            throw new DataInputException("Sender not same Recipient");
        }
        BigDecimal currentSenderBalance = sender.getBalance();
        BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(transferDTO.getTransferAmount()));
        Long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (currentSenderBalance.compareTo(transferAmount) < 0) {
            throw new DataInputException("Sender balance not enough to transfer transaction");
        }
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer.setTransferAmount(transferAmount);
        transfer.setFees(fees);
        transfer.setFeeAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);

        customerService.transfer(transfer);
        sender.setBalance(sender.getBalance().subtract(transactionAmount));
        recipient.setBalance((recipient.getBalance()).add(transferAmount));

        CustomerDTO senderDTO = sender.toCustomerDTO();
        CustomerDTO recipientDTO = recipient.toCustomerDTO();

        Map<String, CustomerDTO> result = new HashMap<>();
        result.put("sender", senderDTO);
        result.put("recipient", recipientDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
