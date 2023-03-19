package com.example.service.role;

import com.example.model.Role;
import com.example.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
