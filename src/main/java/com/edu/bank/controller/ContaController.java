package com.edu.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {



@GetMapping("/")
public String home() {
    return "Banco Digital API - Equipe: João, Maria, Pedro";
}

    
}
