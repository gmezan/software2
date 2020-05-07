package com.example.sw2.controller.sede;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sede")
public class SedeController {
    @GetMapping("/{p}") public String dummyGet(@PathVariable("p") String page){ return "sede/"+ page;}
}
    