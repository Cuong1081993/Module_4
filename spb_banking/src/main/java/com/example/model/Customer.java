package com.example.model;

import com.example.model.dto.CustomerDTO;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Customer extends ModalGeneral implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull(message = "Phone  is required")
    @NumberFormat(pattern = "^[0-9\\-\\+]{9,15}$")
    private String phone;

    @Column(precision = 12, scale = 0, nullable = false, updatable = false)
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
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)

    private LocationRegion locationRegion;

    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
        String fullName = customer.getFullName();
        String email = customer.getEmail();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName","", "Full name is required");
        } else {
            if (fullName.length() < 4 || fullName.length() > 25) {
                errors.rejectValue("fullName", "","Full name length between 4 to 25");
            }
        }

        if (email.length() == 0) {
            errors.rejectValue("email", "", "Email is required");
        } else {
            if (!email.matches("^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
                errors.rejectValue("email", "","Email must be match with the pattern");
            }
        }
    }
}
