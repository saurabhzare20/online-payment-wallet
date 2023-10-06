package com.example.logs;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/loadData/{id}")
    public HashMap<String, Object> greet(@PathVariable String id) throws SQLException {



          return loadLogs.lodLog(id);


    }
    @GetMapping("/loadBalance/{id}")
    public double loadBalance(@PathVariable String id) throws SQLException, ClassNotFoundException {
           return getBalance.getbalance(id);




    }

    @PostMapping("/add")
    public String add(@RequestParam int a, @RequestParam int b) {
        int sum = a + b;
        return "Sum: " + sum;
    }
}
