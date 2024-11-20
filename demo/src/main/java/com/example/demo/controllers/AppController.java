package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.AppService;


@RestController
@RequestMapping("/api/app")
public class AppController {

    @Autowired
    private AppService appService;
}
