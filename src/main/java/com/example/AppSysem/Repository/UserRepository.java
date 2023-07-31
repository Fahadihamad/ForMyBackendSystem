package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users,Integer> {
    Optional < Users> findById(Integer id);

    void deleteUserById(Integer id);

//    List<Object> findByIds(String userName);

    Optional<Users> findByUserName(String userName);

    Users findStaffByUserName(String userName);
//    Optional <User> findByUserName(String userName);
}
