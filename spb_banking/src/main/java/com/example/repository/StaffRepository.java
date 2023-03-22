package com.example.repository;

import com.example.model.Staff;
import com.example.model.dto.StaffInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {


    @Query("SELECT NEW com.example.model.dto.StaffInfoDTO (" +
            "st.fullName, " +
                    "st.phone" +
                    ") " +
                    "FROM Staff AS st " +
                    "JOIN User AS us " +
                    "ON st.user = us " +
                    "AND us.username = :username"
    )
      Optional<StaffInfoDTO> getStaffInfoByUserName(@Param("username")String username);
}
