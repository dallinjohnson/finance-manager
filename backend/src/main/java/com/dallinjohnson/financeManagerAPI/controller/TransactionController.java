package com.dallinjohnson.financeManagerAPI.controller;

import com.dallinjohnson.financeManagerAPI.dto.TransactionDTO;
import com.dallinjohnson.financeManagerAPI.dto.TransactionFilterDTO;
import com.dallinjohnson.financeManagerAPI.model.Transaction;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions(@AuthenticationPrincipal User user, @Valid @ModelAttribute TransactionFilterDTO filter) {
        return transactionService.findAll(filter, user);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId, @AuthenticationPrincipal User user) {
        Transaction transaction = transactionService.findById(transactionId, user);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
                                                         @AuthenticationPrincipal User user
    ) {
        Transaction transaction = transactionService.create(transactionDTO, user);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId,
                                                         @Valid @RequestBody TransactionDTO transactionDTO,
                                                         @AuthenticationPrincipal User user
    ) {
        Transaction transaction = transactionService.update(transactionId, transactionDTO, user);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long transactionId, @AuthenticationPrincipal User user) {
        transactionService.deleteById(transactionId, user);
        return ResponseEntity.noContent().build();
    }
}
