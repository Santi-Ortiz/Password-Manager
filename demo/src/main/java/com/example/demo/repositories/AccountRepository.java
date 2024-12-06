package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Account;
import com.example.demo.entities.App;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findAllByUser_UserId(Long userId);
    App findByApp_appId(Long appId);

}
