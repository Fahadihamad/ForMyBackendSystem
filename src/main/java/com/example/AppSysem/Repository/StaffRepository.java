package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Staffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staffs,Integer> {
    Optional<Staffs> findUserById(Integer id);

    void deleteUserById(Integer id);
}
