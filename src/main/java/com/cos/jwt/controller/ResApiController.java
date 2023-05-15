package com.cos.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ResApiController {

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    };
}
