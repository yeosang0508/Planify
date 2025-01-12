package com.example.planify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/api/test")
    public String testApi() {
        return "react, spring boot 연결 성공!";
    }
}