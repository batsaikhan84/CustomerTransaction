package com.customer.relationship.controller;

import com.customer.relationship.Repository.TransactionRepository;
import com.customer.relationship.Repository.UserRepository;
import com.customer.relationship.exception.CustomerTransactionNotFoundException;
import com.customer.relationship.exception.UserNotFoundException;
import com.customer.relationship.model.Transactions;
import com.customer.relationship.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
public class TransactionController {
    @Autowired
    public TransactionRepository transactionRepository;
    @Autowired
    public UserRepository userRepository;
    @GetMapping("/users/{userId}/transactions")
    public Page<Transactions> getTransactions(@PathVariable(value = "userId") Long userId, Pageable pageable) {
        return transactionRepository.findByUserId(userId, pageable);
    }
    @GetMapping("/users/{userId}/transactions/{transactionId}")
    public ResponseEntity<Transactions> getTransaction(@PathVariable(value = "userId") Long userId, @PathVariable(value = "transactionId") Long transactionId) {
        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundException("ID: " + userId + " not found");
        }
        return transactionRepository.findByIdAndUserId(transactionId, userId).map(transaction -> ResponseEntity.ok().body(transaction)).
                orElseThrow(() -> new CustomerTransactionNotFoundException("ID: " + transactionId + " not found"));
    }
    @PostMapping("/user/{userId}/transactions")
    public Transactions addTransaction(@PathVariable(value = "userId") Long userId, @RequestBody Transactions transactions) {
        return userRepository.findById(userId).map(user -> {
            transactions.setUser(user);
            return transactionRepository.save(transactions);
        }).orElseThrow(() -> new CustomerTransactionNotFoundException("ID: " + userId + " not found"));
    }
    @PutMapping("/users/{userId}/transactions/{transactionId}")
    public Transactions updateTransaction(@PathVariable(value = "userId") Long userId, @PathVariable(value = "transactionId") Long transactionId,
                                          @RequestBody Transactions transactionDetails) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("ID: " + userId + " not found");
        }
        return transactionRepository.findById(transactionId).map(transaction -> {
            transaction.setAmount(transactionDetails.getAmount());
            return transactionRepository.save(transaction);
        }).orElseThrow(() -> new CustomerTransactionNotFoundException("ID: " + userId + " not found"));
    }
}
