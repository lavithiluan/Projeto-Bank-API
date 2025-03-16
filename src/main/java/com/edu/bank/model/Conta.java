package com.edu.bank.model;

public class Conta {

    private Long id;
    private Double numero;
    private Double agencia;
    private String nomeTitular;
    private String cpfTitular;
    private String dataAbertura;
    private Double saldoInicial;
    private Boolean ativo;
    private String tipo;
    
    public Conta(Long id, Double numero, Double agencia, String nomeTitular, String cpfTitular, String dataAbertura,
            Double saldoInicial, Boolean ativo, String tipo) {
        this.id = id;
        this.numero = numero;
        this.agencia = agencia;
        this.nomeTitular = nomeTitular;
        this.cpfTitular = cpfTitular;
        this.dataAbertura = dataAbertura;
        this.saldoInicial = saldoInicial;
        this.ativo = ativo;
        this.tipo = tipo;
    }

    
    
    

    


    
}
