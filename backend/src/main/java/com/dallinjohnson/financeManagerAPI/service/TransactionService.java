package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.TransactionDTO;
import com.dallinjohnson.financeManagerAPI.dto.TransactionFilterDTO;
import com.dallinjohnson.financeManagerAPI.model.Account;
import com.dallinjohnson.financeManagerAPI.model.Transaction;
import com.dallinjohnson.financeManagerAPI.model.Category;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.repository.TransactionRepository;
import com.dallinjohnson.financeManagerAPI.repository.specification.TransactionSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public List<Transaction> findAll(TransactionFilterDTO filter, User user) {
        Specification<Transaction> spec = Specification
                .where(TransactionSpecifications.forUser(user))
                .and(TransactionSpecifications.withFilters(filter));
        return transactionRepository.findAll(spec);
    }

    public Transaction findById(Long id, User user) {
        return transactionRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
    }

    public List<Transaction> findByCategory(Long categoryId, User user) {
        Category category = categoryService.findById(categoryId, user);
        return transactionRepository.findByCategoryAndUser(category, user);
    }

    @Transactional
    public Transaction create(TransactionDTO transactionDTO, User user) {
        Transaction transaction = new Transaction();

        Account account = accountService.findById(transactionDTO.accountId(), user);
        Category category = categoryService.findById(transactionDTO.categoryId(), user);

        transaction.setTitle(transactionDTO.title());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setDate(transactionDTO.date());
        transaction.setAmount(transactionDTO.amount());
        transaction.setDescription(transactionDTO.description());
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction update(Long id, TransactionDTO transactionDTO, User user) {
        Transaction transaction = this.findById(id, user);

        Account account = accountService.findById(transactionDTO.accountId(), user);
        Category category = categoryService.findById(transactionDTO.categoryId(), user);

        transaction.setTitle(transactionDTO.title());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setDate(transactionDTO.date());
        transaction.setAmount(transactionDTO.amount());
        transaction.setDescription(transactionDTO.description());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteById(Long id, User user) {
        Transaction transaction = this.findById(id, user);
        transactionRepository.delete(transaction);
    }
}
