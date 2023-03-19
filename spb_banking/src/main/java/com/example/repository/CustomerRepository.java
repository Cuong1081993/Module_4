package com.example.repository;

import com.example.model.Customer;
import com.example.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByDeletedIsFalse();

    @Query("SELECT NEW com.example.model.dto.CustomerDTO (" +
            "cus.id, " +
            "cus.fullName, " +
            "cus.email, " +
            "cus.phone, " +
            "cus.balance, " +
            "cus.locationRegion" +
            ") " +
            "FROM Customer AS cus " +
            "WHERE cus.deleted = false "
    )
    List<CustomerDTO> findAllByDeletedIsFalseDTO();

    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE Customer as c" +
            "SET c.balance = c.balance + :transactionAmount" +
            "WHERE c.id = :customerId"
    )
    void incrementBalance(@Param("customerId") Long customerId,@Param("transactionAmount") BigDecimal transactionAmount);

    @Modifying
    @Query("UPDATE Customer  as c " +
            "SET c.balance = c.balance  - : transactionAmount" +
            "WHERE c.id = :customerId"
    )
    void decrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount")BigDecimal transactionAmount);
}

