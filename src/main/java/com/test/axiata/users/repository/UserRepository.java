package com.test.axiata.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.axiata.users.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

    @Query("Select u from Users u Where u.email =:email")
    Optional<Users> findByEmail(@Param("email") String email);
    
    @Query("Select u from Users u Where u.isDeleted !=true")
    List<Users> findAllUser();
    
}
