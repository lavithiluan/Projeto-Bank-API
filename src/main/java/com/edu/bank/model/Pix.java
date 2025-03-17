package com.edu.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pix {
    @JsonProperty("idContaOrigem")
    private Long idContaOrigem;
    @JsonProperty("idContaDestino")
    private Long idContaDestino;
    @JsonProperty("valor")
    private double valor;


    public Long getIdContaOrigem() {
        return idContaOrigem;
    }
    public void setIdContaOrigem(Long idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }
    public Long getIdContaDestino() {
        return idContaDestino;
    }
    public void setIdContaDestino(Long idContaDestino) {
        this.idContaDestino = idContaDestino;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }


    
}
