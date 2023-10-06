package com.signupservice.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequestMapping("/api")
public class MyController {

    @PostMapping ("/signIn")
    public int signUp(@RequestBody User user) throws SQLException {
        if(storeUser.signup(user.getEmail(),user.getPassword())){
            return storeUser.setUsername(user.getEmail());
        }
       return 0;
    }





    @PostMapping ("/register")
    public String   hello(@RequestBody User user) throws SQLException {
        if(!storeUser.cheakUsernameValide(user.getUsername())){
            return "username is already taken ";

        }
         if(!storeUser.cheakEmailValide(user.getEmail())){
             return"email is already taken";

         }


        return storeUser.insertData(user);







    }
    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping ("/add")
    public String add(@RequestParam int a, @RequestParam int b) {
        int sum = a + b;
        return "Sum: " + sum;
    }
}


