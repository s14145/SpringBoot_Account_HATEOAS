package com.hateoas.repository;

import com.hateoas.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("AccountRepository")
public interface AccountRepository extends JpaRepository<Account, Long> {
}