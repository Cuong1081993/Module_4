package com.example.controller;

import com.example.model.Transfer;
import com.example.service.Transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class TransferController {
    @Autowired
    private ITransferService transferService;
    @GetMapping("/transfer")
    public String showHistory(Model model){
        List<Transfer> transfers = transferService.findAll();

        model.addAttribute("transfers",transfers);
        return "customer/transferHistory";
    }
}
