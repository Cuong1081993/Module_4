package com.example.repository;

import com.example.model.Customer;
import com.example.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query(value = "select cus  from Customer cus  where cus.id <> ?1")
    public List<Customer> findAllByIdNot(Long id);
}
