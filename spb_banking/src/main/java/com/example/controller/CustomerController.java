package com.example.controller;


import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;


@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public String showListPage(Model model){
        String username = appUtils.getPrincipalUsername();
        model.addAttribute("username",username);

        return "customers/list";
    }
}
