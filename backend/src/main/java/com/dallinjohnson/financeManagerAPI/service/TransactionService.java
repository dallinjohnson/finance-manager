package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.TransactionDTO;
import com.dallinjohnson.financeManagerAPI.model.Account;
import com.dallinjohnson.financeManagerAPI.model.Transaction;
import com.dallinjohnson.financeManagerAPI.model.Category;
import com.dallinjohnson.financeManagerAPI.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(Long id) throws EntityNotFoundException {
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
    }

    @Transactional
    public Transaction create(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();

        Account account = accountService.findById(transactionDTO.accountId());
        Category category = categoryService.findById(transactionDTO.categoryId());

        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setDate(transactionDTO.date());
        transaction.setAmount(transactionDTO.amount());
        transaction.setDescription(transactionDTO.description());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction update(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = this.findById(id);

        Account account = accountService.findById(transactionDTO.accountId());
        Category category = categoryService.findById(transactionDTO.categoryId());

        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setDate(transactionDTO.date());
        transaction.setAmount(transactionDTO.amount());
        transaction.setDescription(transactionDTO.description());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteById(Long id) {
        Transaction transaction = this.findById(id);
        transactionRepository.delete(transaction);
    }
}
