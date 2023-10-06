package com.example.validateTransaction;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataStorageService {
    private final Map<String, String> dataStore = new HashMap<>();

    public void storeData(String key, String value) {
        dataStore.put(key, value);
    }

    public String getData(String key) {
        return dataStore.get(key);
    }
}
