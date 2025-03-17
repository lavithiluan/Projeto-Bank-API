package com.edu.bank.model;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Conta {
    private Long id;
    private String nomeTitular;
    private String cpfTitular;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura;
    private Double saldoInicial;
    private TipoConta tipoconta;
    private Boolean status;


    public Conta(Long id, String nomeTitular, String cpfTitular, LocalDate dataAbertura, Double saldoInicial, TipoConta tipoconta, Boolean status) {
        this.id = (id == null) ? Math.abs(new Random().nextLong()) : id;
        this.nomeTitular = isNotNull(nomeTitular);
        this.cpfTitular = isNotNull (cpfTitular);
        this.dataAbertura = dataAbertura;
        setDataAbertura(dataAbertura);
        setSaldoInicial(saldoInicial);
        this.tipoconta = tipoconta;
        this.status = status;
    }

    
    private String isNotNull(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new InvalidParameterException("O valor não pode ser nulo ou vazio");
        }
        return valor;
    }

    private static boolean isDateValid(LocalDate date) {        
        return !date.isAfter(LocalDate.now());
    }

    private boolean isSaldoValid(Double saldo) { 
        if (saldo == null) {
            throw new InvalidParameterException("O valor do saldo não pode ser nulo");
        }
        return saldo > 0;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNomeTitular() {
        return nomeTitular;
    }


    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = isNotNull(nomeTitular);
    }


    public String getCpfTitular() {
        return cpfTitular;
    }


    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = isNotNull(cpfTitular);
    }


    public LocalDate getDataAbertura() {
        return dataAbertura;
    }


    public void setDataAbertura(LocalDate dataAbertura) {
        if (dataAbertura == null || !isDateValid(dataAbertura)) { 
            throw new InvalidParameterException("A data de abertura não pode ser nula ou futura");  
    }
        this.dataAbertura = dataAbertura;
    }


    public Double getSaldoInicial() {
        return saldoInicial;
    }


    public void setSaldoInicial(Double saldoInicial) {
        if (!isSaldoValid(saldoInicial)) {
            throw new InvalidParameterException("O saldo inicial não pode ser nulo ou negativo");
        }
        this.saldoInicial = saldoInicial;
    }


    public TipoConta getTipoconta() {
        return tipoconta;
    }


    public void setTipoconta(TipoConta tipoconta) {
        this.tipoconta = tipoconta;
    }


    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    } 

    @Override
    public String toString() {
        return String.format("Conta [id=%s, nomeTitular=%s, cpfTitular=%s, dataAbertura=%s, saldoInicial=%s, tipoconta=%s, status=%s]", id, nomeTitular, cpfTitular, dataAbertura, saldoInicial, tipoconta, status);
    }
    
    
    
    

    

    
}
