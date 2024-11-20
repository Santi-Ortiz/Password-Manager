package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.App;

public interface AppRepository extends JpaRepository<App,Long> {
   
}
