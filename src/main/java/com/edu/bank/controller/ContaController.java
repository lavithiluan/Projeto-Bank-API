package com.edu.bank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.edu.bank.model.Pix;
import com.edu.bank.model.Transacao;
import com.edu.bank.model.Conta;

@RestController
@RequestMapping("/contas")
public class ContaController {
private List<Conta> repository = new ArrayList<>();
    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    @ResponseStatus()
    public ResponseEntity<?> stakeholders() {
        String resp = """
            Projeto Bank
            -------------------------------------
            Membros: 
            - Thiago Henry Dias (Rm554522)
            """;
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/cadastro-conta")
    public ResponseEntity<Conta> create(@RequestBody Conta conta){
        log.info("Cadastrando... " + conta.getNomeTitular());
        repository.add(conta);
        return ResponseEntity.ok(conta);
    } 

    @GetMapping("/all")
    public List<Conta> getAll(){
        return repository;
    }

    @GetMapping("/{id}")
    public Conta getById(@PathVariable Long id){
        return findById(id);
     
    }

    @GetMapping("/cpf/{cpf}")
    public Conta getByCpf(@PathVariable String cpfTitular){ 
        return findByCpf(cpfTitular);
    }
    
    @PutMapping("/deposito")
    public ResponseEntity<Conta> deposito(@RequestBody Transacao deposito){
        if(deposito.getIdConta() == null || deposito.getValor() <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        log.info("Realizando deposito da conta..." + deposito.getIdConta());
        
        Conta conta = findById(deposito.getIdConta());
        
        if(conta == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        double novoSaldo = deposito.getValor() + conta.getSaldoInicial();
        conta.setSaldoInicial(novoSaldo);
        
        repository.remove(deposito.getIdConta());
        repository.add(conta); 

        return ResponseEntity.ok(conta);
    }

    @PutMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody Transacao saque){
        if(saque.getIdConta() == null || saque.getValor() <= 0){
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "ID da conta é obrigatório e o valor do saque deve ser positivo."));
        }
        Conta conta = findById(saque.getIdConta());
        if(conta == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro","Conta não encontrada"));
        }
        if(saque.getValor() > conta.getSaldoInicial()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(Map.of("Bad erro","Sem saldo suficiente para realizar o  saque"));
        }
        double novoSaldo = conta.getSaldoInicial() - saque.getValor();
        conta.setSaldoInicial(novoSaldo);

        repository.remove(saque.getIdConta());
        repository.add(conta);
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/pix")
    public ResponseEntity<?> pix(@RequestBody Pix pix){
        
        if(pix.getIdContaOrigem() == null || pix.getIdContaDestino() == null || pix.getValor() <= 0){
            return ResponseEntity.badRequest().body(null);
        }
        Conta origem = findById(pix.getIdContaOrigem());
        Conta destino = findById(pix.getIdContaDestino());
        if (origem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("erro", "Conta de origem não encontrada."));
        }
        if (destino == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("erro", "Conta de destino não encontrada."));
        }
    
        if (pix.getValor() > origem.getSaldoInicial()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("erro", "Saldo insuficiente na conta de origem para realizar a transferência."));
        }
    

        double novoSaldoOrigem = origem.getSaldoInicial() - pix.getValor();
        double novoSaldoDestino = destino.getSaldoInicial() + pix.getValor();
        origem.setSaldoInicial(novoSaldoOrigem);
        destino.setSaldoInicial(novoSaldoDestino);

        repository.remove(pix.getIdContaOrigem());
        repository.remove(pix.getIdContaDestino());
        repository.add(origem);
        repository.add(destino); 
        
        return ResponseEntity.ok(origem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Conta> delete(@PathVariable Long id){
        if(id == null){
            return ResponseEntity.badRequest().build();
        }

        Conta conta = findById(id);

        if(conta == null){
            return ResponseEntity.notFound().build();
        }
        conta.setStatus(false);
        
        repository.remove(conta);
        repository.add(conta);
        
        return ResponseEntity.ok(conta);

    }


    private Conta findByCpf(String cpf) {
        return repository.stream()
            .filter(c -> c.getCpfTitular().replaceAll("[^0-9]", "").equals(cpf.replaceAll("[^0-9]", "")))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    

    private Conta findById(Long id) {
        return repository
                    .stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                    );
    }
}
