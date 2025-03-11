package com.dallinjohnson.financeManagerAPI.repository;

import com.dallinjohnson.financeManagerAPI.model.Account;
import com.dallinjohnson.financeManagerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdAndUser(Long id, User user);
    List<Account> findAllByUser(User user);
}
