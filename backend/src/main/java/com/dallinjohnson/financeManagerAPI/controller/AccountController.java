package com.dallinjohnson.financeManagerAPI.controller;

import com.dallinjohnson.financeManagerAPI.dto.AccountDTO;
import com.dallinjohnson.financeManagerAPI.model.Account;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts(@AuthenticationPrincipal User user) {
        return accountService.findAll(user);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId, @AuthenticationPrincipal User user) {
        Account account = accountService.findById(accountId, user);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountDTO accountDTO, @AuthenticationPrincipal User user) {
        Account newAccount = accountService.create(accountDTO, user);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId,
                                                 @Valid @RequestBody AccountDTO accountDTO,
                                                 @AuthenticationPrincipal User user
    ) {
        Account account = accountService.update(accountId, accountDTO, user);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId, @AuthenticationPrincipal User user) {
        accountService.deleteById(accountId, user);
        return ResponseEntity.noContent().build();
    }
}
