package com.example.service.staff;

import com.example.model.Staff;
import com.example.model.User;
import com.example.model.dto.StaffInfoDTO;
import com.example.model.dto.StaffReqDTO;
import com.example.repository.StaffRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceIpm implements IStaffService{
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();

    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Staff getById(Long id) {
        return null;
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void deleted(Staff staff) {

    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existByIdEqual(Long id) {
        return false;
    }

    @Override
    public Optional<StaffInfoDTO> getStaffInfoByUsername(String username) {
        return staffRepository.getStaffInfoByUserName(username);
    }

    @Override
    public void create(StaffReqDTO staffReqDTO) {
        User user = new User();
        user.setId(staffReqDTO.getId());
        user.setUsername(staffReqDTO.getUsername());
        user.setPassword(passwordEncoder.encode(staffReqDTO.getPassword()));
        user.setRole(staffReqDTO.getRoleDTO().toRole());
        userRepository.save(user);

        Staff staff = staffReqDTO.toStaff(user);
        staffRepository.save(staff);
    }
}
