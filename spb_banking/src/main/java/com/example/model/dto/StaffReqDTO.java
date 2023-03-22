package com.example.model.dto;

import com.example.model.Role;
import com.example.model.Staff;
import com.example.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffReqDTO {
    private Long id;
    private String username;
    private String password;
    private RoleDTO roleDTO;
    private String fullName;
    private String phone;

    public Staff toStaff(User user){
        return new Staff()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone)
                .setUser(user)
                ;
    }
}
