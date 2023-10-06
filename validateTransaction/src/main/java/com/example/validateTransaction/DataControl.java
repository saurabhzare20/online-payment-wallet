package com.example.validateTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
public class DataControl {

    @Autowired
    private DataStorageService dataStorageService;

    @GetMapping("/storeData")
    public void storeData(@RequestParam String key, @RequestParam String value) {
        dataStorageService.storeData(key, value);
    }

    @GetMapping("/{key}")
    public String getData(@PathVariable String key) {
        String saz= dataStorageService.getData(key);
        return saz;
    }



}