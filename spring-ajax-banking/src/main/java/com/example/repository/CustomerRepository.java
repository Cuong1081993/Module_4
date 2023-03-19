package com.example.repository;

import com.example.model.Customer;
import com.example.model.DTO.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByIdNotAndDeletedIsFalse(Long id);

    List<Customer> findAllByDeletedIsFalse();

    @Query("SELECT NEW com.example.model.DTO.CustomerDTO (" +
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

    @Modifying
    @Query("UPDATE Customer AS cus " +
            "SET cus.balance = cus.balance + :transactionAmount " +
            "WHERE cus = :customer"
    )
    void incrementBalance(@Param("transactionAmount") BigDecimal transactionAmount, @Param("customer") Customer customer);
    @Modifying
    @Query("UPDATE Customer As c Set c.balance = c.balance - :transactionAmount WHERE c.id = :customerId")
    void decrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);

    @Query("SELECT NEW com.example.model.DTO.CustomerDTO(cus.id," +
            "cus.fullName," +
            "cus.email," +
            "cus.phone,cus." +
            "balance," +
            "cus.locationRegion) " +
            "FROM Customer as cus"
    )
    List<CustomerDTO> findAllCustomerDTO();

    Boolean existsByEmail(String email);
}
