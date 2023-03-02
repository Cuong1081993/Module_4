package com.example.controller;


import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.Withdraw;
import com.example.service.Customer.ICustomerService;
import com.example.service.Deposit.IDepositService;
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

    @GetMapping
    public String showListCustomer(Model model) {
        List<Customer> customers = customerService.findAllByDeletedIsFalse();

        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customers", new Customer());

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
            if (customer.getDeleted()) {
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
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer ID invalid");

        } else {
            Customer customer = customerOptional.get();
            model.addAttribute("errors", null);
            model.addAttribute("customers", customer);
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
    @GetMapping("/transfer/{customerId}")
    public String showTransferForm(@PathVariable Long customerId, Model model,){

    }

    @PostMapping("/create")
    public String create(@Validated Customer customer, BindingResult bindingResult, Model model) {

        new Customer().validate(customer, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            return "customer/create";
        }
        model.addAttribute("errors", false);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        return "redirect:/customers";
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
    public String deposit(@PathVariable Long customerId, Model model, Deposit deposit) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("errors", true);
            model.addAttribute("message", "Customer ID invalid");
        } else {
            Customer customer = customerOptional.get();
            BigDecimal currentBalance = customer.getBalance();
            BigDecimal transactionAmount = deposit.getTransactionAmount();
            BigDecimal newBalance = currentBalance.add(transactionAmount);
            customer.setBalance(newBalance);

            customerService.deposit(customer, deposit);
            model.addAttribute("errors", false);
            model.addAttribute("customers", customer);
            model.addAttribute("deposit", new Deposit());
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
            model.addAttribute("withdraw",new Withdraw());
        }
        return "customer/withdraw";
    }

}
