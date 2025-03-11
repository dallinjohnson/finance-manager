package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.AccountDTO;
import com.dallinjohnson.financeManagerAPI.model.Account;
import com.dallinjohnson.financeManagerAPI.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
    }

    @Transactional
    public Account create(AccountDTO accountDTO) {
        Account account = new Account();
        account.setName(accountDTO.name());
        return accountRepository.save(account);
    }

    @Transactional
    public Account update(Long id, AccountDTO accountDTO) {
        Account account = this.findById(id);
        account.setName(accountDTO.name());
        return accountRepository.save(account);
    }

    @Transactional
    public void deleteById(Long id) {
        Account account = this.findById(id);
        accountRepository.delete(account);
    }
}
