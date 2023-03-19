package com.example.repository;

import com.example.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

}
