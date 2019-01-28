package edu.nju.wsql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping
    public String get() {
        return "redirect:/login";
    }
}
