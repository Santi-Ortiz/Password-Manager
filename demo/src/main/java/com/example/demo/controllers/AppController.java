package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.App;
import com.example.demo.services.AppService;


@RestController
@RequestMapping("/api/app")
public class AppController {

    @Autowired
    private AppService appService;

    // http://localhost:8090/api/app/find/{id}
    @GetMapping("/find/{id}")
    public ResponseEntity<App> getAppById(@PathVariable("id") Long id){
        App app = appService.getAppById(id);
        if(app != null) {
            return new ResponseEntity<>(app, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new App(), HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8090/api/app/agregar
    @PostMapping("/add")
    public ResponseEntity<String> addApp(@RequestBody App app) {
        try {
            appService.saveApp(app);
            return new ResponseEntity<>("App creada", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la app", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // http://localhost:8090/api/app/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateApp(@PathVariable("id") Long id, @RequestBody App app) {
        
        if(appService.getAppById(id) == null){
            return new ResponseEntity<>("La app no existe", HttpStatus.NOT_FOUND);
        }

        try {
            appService.updateApp(app);
            return new ResponseEntity<>("App actualizada", HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la app", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    // http://localhost:8090/api/app/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApp(@PathVariable("id") Long id) {
        
        if(appService.getAppById(id) == null){
            return new ResponseEntity<>("La app no existe", HttpStatus.NOT_FOUND);
        }

        try {
            appService.deleteApp(id);
            return new ResponseEntity<>("App eliminada", HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la app", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}
