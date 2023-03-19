package com.example.model;

import com.example.model.dto.CustomerDTO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Customer extends ModalGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(nullable = false,unique = true)
    private String email;
    private String phone;
    @Column(precision = 12,scale = 0,nullable = false,updatable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "customer")
    private List<Deposit> deposits;
    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> sender;
    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> recipient;
    @OneToMany(targetEntity = Withdraw.class)
    private List<Withdraw> withdraws;

    @ManyToOne
    @JoinColumn(name = "location_region_id",referencedColumnName = "id",nullable = false)

    private LocationRegion locationRegion;

    public CustomerDTO toCustomerDTO(){
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }
}
