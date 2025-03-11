package com.dallinjohnson.financeManagerAPI.repository;

import com.dallinjohnson.financeManagerAPI.model.Transaction;
import com.dallinjohnson.financeManagerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);
    Optional<Transaction> findByIdAndUser(Long id, User user);
}
