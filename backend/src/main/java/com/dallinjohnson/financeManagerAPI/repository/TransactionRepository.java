package com.dallinjohnson.financeManagerAPI.repository;

import com.dallinjohnson.financeManagerAPI.model.Category;
import com.dallinjohnson.financeManagerAPI.model.Transaction;
import com.dallinjohnson.financeManagerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findAllByUser(User user);
    List<Transaction> findByCategoryAndUser(Category category, User user);
    Optional<Transaction> findByIdAndUser(Long id, User user);
}
