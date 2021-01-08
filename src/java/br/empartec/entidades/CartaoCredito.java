/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.entidades;

import java.util.Date;

/**
 *
 * @author Luciano
 */
public class CartaoCredito {
    private Integer idCartao;
    private String nome;
    private Integer fechamentoFatura;
    private Integer vencimentoFatura;
    private String bandeira;
    private String numero;
    private Date data = new Date();

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFechamentoFatura() {
        return fechamentoFatura;
    }

    public void setFechamentoFatura(Integer fechamentoFatura) {
        this.fechamentoFatura = fechamentoFatura;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getVencimentoFatura() {
        return vencimentoFatura;
    }

    public void setVencimentoFatura(Integer vencimentoFatura) {
        this.vencimentoFatura = vencimentoFatura;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
