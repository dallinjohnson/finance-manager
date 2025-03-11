package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.AccountDTO;
import com.dallinjohnson.financeManagerAPI.model.Account;
import com.dallinjohnson.financeManagerAPI.model.User;
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

    public List<Account> findAll(User user) {
        return accountRepository.findAllByUser(user);
    }

    public Account findById(Long id, User user) {
        return accountRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
    }

    @Transactional
    public Account create(AccountDTO accountDTO, User user) {
        Account account = new Account();
        account.setName(accountDTO.name());
        account.setUser(user);
        return accountRepository.save(account);
    }

    @Transactional
    public Account update(Long id, AccountDTO accountDTO, User user) {
        Account account = this.findById(id, user);
        account.setName(accountDTO.name());
        return accountRepository.save(account);
    }

    @Transactional
    public void deleteById(Long id, User user) {
        Account account = this.findById(id, user);
        accountRepository.delete(account);
    }
}
