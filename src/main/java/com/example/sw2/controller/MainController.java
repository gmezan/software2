package com.example.sw2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping("/{p}")
    public String cont(@PathVariable("p") String page){
        return page;
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

}
