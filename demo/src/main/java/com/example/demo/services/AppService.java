package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.App;
import com.example.demo.repositories.AppRepository;

import jakarta.transaction.Transactional;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    @Transactional
    public App saveApp(App app) {
        return appRepository.save(app);
    }


    @Transactional
    public App updateApp(App app) {
        if (appRepository.findById(app.getAppId()).isPresent()) {
            App newApp = appRepository.findById(app.getAppId()).get();
            appRepository.save(newApp);
            return newApp;
        } else {
            throw new IllegalStateException("App no encontrada");
        }
    }

    @Transactional
    public void deleteApp(Long appId) {
        if (appRepository.findById(appId).isPresent()) {
            appRepository.deleteById(appId);
        } else {
            throw new IllegalStateException("App no encontrada");
        }
    }

    public App getAppById(Long appId) {
        return appRepository.findById(appId)
            .orElseThrow(() -> new IllegalStateException("App no encontrada"));
    }
    
}
