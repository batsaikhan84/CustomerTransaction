package com.customer.relationship.controller;

import com.customer.relationship.Repository.TransactionRepository;
import com.customer.relationship.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class TransactionController {
    @Autowired
    public TransactionRepository transactionRepository;
    @GetMapping("/transactions")
    public String getTransactions(Model model) {
        List<Transactions> transactionList = new ArrayList<>();
        model.addAttribute("transactions", transactionList);
        return "transactionList";
    }
}
