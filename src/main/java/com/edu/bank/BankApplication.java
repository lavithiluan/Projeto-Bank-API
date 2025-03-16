package com.edu.bank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
@RestController
@RequestMapping("/")
class HomeController {
    @GetMapping
    public String home() {
        return "Banco Digital API - Equipe: João, Maria, Pedro";
    }
}

@RestController
@RequestMapping("/contas")
class ContaController {
    private final List<Conta> contas = new ArrayList<>();

    @PostMapping
    public Conta cadastrarConta(@Valid @RequestBody Conta novaConta) {
        contas.add(novaConta);
        return novaConta;
    }
}

}
