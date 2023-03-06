package com.example.controller;


import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Transfer;
import com.example.model.Withdraw;
import com.example.service.Customer.ICustomerService;
import com.example.service.Deposit.IDepositService;
import com.example.service.Transfer.ITransferService;
import com.example.service.Withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/customers", "/"})
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private ITransferService transferService;

    @GetMapping
    public String showListCustomer(Model model) {
        List<Customer> customers = customerService.findAllByDeletedIsFalse();

        model.addAttribute("customers", customers);
        return "customer/list";
    }


    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";

    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer ID invalid");
        } else {
            Customer customer = customerOptional.get();
            model.addAttribute("errors", false);
            model.addAttribute("customers", customer);
        }
        return "/customer/edit";

    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(Model model, @PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer Id invalid");
            model.addAttribute("customers", new Customer());
        } else {
            Customer customer = customerOptional.get();
            if (customer.isDeleted()) {
                model.addAttribute("errors", true);
                model.addAttribute("message", "This customer is suspended");
            } else {
                model.addAttribute("errors", null);
            }
            model.addAttribute("customers", customer);
        }
        return "customer/delete";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositForm(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Customer ID invalid");
            return "error/404";

        } else {
            Customer customer = customerOptional.get();
            model.addAttribute("error", null);
            model.addAttribute("customer", customer);
            model.addAttribute("deposit", new Deposit());
        }
        return "customer/deposit";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawForm(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer ID invalid");
        } else {
            Customer customer = customerOptional.get();
            model.addAttribute("errors", false);
            model.addAttribute("customers", customer);
            model.addAttribute("withdraw", new Withdraw());
        }
        return "customer/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferForm(@PathVariable Long senderId, Model model) {
        Optional<Customer> senderOptional = customerService.findById(senderId);
        if (!senderOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender ID invalid");
        } else {
            List<Customer> recipients = customerService.findAllByIdNotAndDeletedIsFalse(senderId);
            Customer sender = senderOptional.get();

            model.addAttribute("error", null);
            model.addAttribute("sender", sender);
            model.addAttribute("recipients", recipients);
            model.addAttribute("transfer", new Transfer());
        }
        return "customer/transfer";

    }

    @PostMapping("/create")
    public String create(Customer customer, BindingResult bindingResult, Model model) {

        new Customer().validate(customer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            model.addAttribute("customer", customer);
            return "customer/create";
        }
        model.addAttribute("errors", false);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        model.addAttribute("customer", new Customer());

        model.addAttribute("success", true);
        model.addAttribute("message", "Create Successfully !");

        return "redirect:/customers/create";
    }

    @PostMapping("/edit/{customerId}")
    public String edit(@PathVariable Long customerId, Customer customer) {

        customer.setId(customerId);
        customerService.save(customer);

        return "redirect:/customers";
    }

    @PostMapping("/delete/{customerId}")
    public String delete(Model model, @PathVariable Long customerId) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer Id invalid");
            model.addAttribute("customers", new Customer());
        } else {
            Customer customer = customerOptional.get();

            customer.setDeleted(true);

            customerService.save(customer);
            model.addAttribute("errors", false);
            model.addAttribute("customers", customer);
        }
        return "redirect:/customers";
    }

    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @Validated Deposit deposit, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            model.addAttribute("deposit",deposit);
            return "customer/deposit";
        }
            Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer ID invalid");
            model.addAttribute("customer", customerOptional.get());
        } else {
            Customer customer = customerOptional.get();
            BigDecimal currentBalance = customer.getBalance();
            BigDecimal transactionAmount = deposit.getTransactionAmount();
            BigDecimal newBalance = currentBalance.add(transactionAmount);
            customer.setBalance(newBalance);

            customerService.deposit(customer, deposit);

            model.addAttribute("errors", false);
            model.addAttribute("deposit", deposit);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deposit Successfully");
            model.addAttribute("customer", customer);
        }

        return "customer/deposit";
    }

    @PostMapping("/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, Withdraw withdraw, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer ID invalid");
        } else {
            Customer customer = customerOptional.get();
            BigDecimal currentBalance = customer.getBalance();
            BigDecimal transactionAmount = withdraw.getTransactionAmount();
            BigDecimal newBalance = currentBalance.subtract(transactionAmount);
            customer.setBalance(newBalance);

            customerService.withdraw(customer, withdraw);

            model.addAttribute("errors", false);
            model.addAttribute("customers", customer);
            model.addAttribute("withdraw", new Withdraw());
        }
        return "customer/withdraw";
    }

    @PostMapping("/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, Transfer transfer, Model model) {
        Optional<Customer> senderOptional = customerService.findById(senderId);

        List<Customer> recipients = customerService.findAllByIdNot(senderId);

        if (!senderOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender ID is invalid");
        } else {
            Customer sender = senderOptional.get();
            Long recipientId = transfer.getRecipient().getId();

            if (recipientId.equals(senderId)) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Sender can't be same with Recipient");
            }
            Optional<Customer> recipientOptional = customerService.findById(recipientId);
            if (!recipientOptional.isPresent()) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Recipient ID  is invalid");
            } else {
                BigDecimal senderBalance = sender.getBalance();
                BigDecimal transferAmount = transfer.getTransferAmount();
                Long fees = 10L;
                BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees).divide(BigDecimal.valueOf(100L)));
                BigDecimal transactionAmount = transferAmount.add(feesAmount);

                if (senderBalance.compareTo(transactionAmount) < 0) {
                    model.addAttribute("error", true);
                    model.addAttribute("message", "Balance not enough to transfer");
                } else {
                    Customer recipient = recipientOptional.get();

                    transfer.setSender(sender);
                    transfer.setTransferAmount(transferAmount);
                    transfer.setFees(fees);
                    transfer.setRecipient(recipient);
                    transfer.setTransactionAmount(transactionAmount);
                    transfer.setFeeAmount(feesAmount);

                    customerService.transfer(transfer);
                    model.addAttribute("error", false);
                    sender.setBalance(sender.getBalance().subtract(transactionAmount));
                }
            }


            model.addAttribute("sender", sender);
            model.addAttribute("recipients", recipients);
            model.addAttribute("transfer", new Transfer());
        }
        return "customer/transfer";
    }
}
