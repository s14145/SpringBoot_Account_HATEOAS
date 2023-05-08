package com.hateoas.repository;

import com.hateoas.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("TransferRepository")
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}