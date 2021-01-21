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
public class SuperClasse {
    
    private Integer idGastoaCartao;
    private Integer idCartao;
    private Integer idUsuario;
    private Integer fechamentoFatura;
    private Integer vencimentoFatura;
    private String nomeUsuarioDoCartao;
    private String bandeira;
    private String numero;
    private String descricaoGasto;
    private String categoria;
    private Double valor;
    private int total;
    private Date dataDoGasto = new Date();

    public Integer getIdGastoaCartao() {
        return idGastoaCartao;
    }

    public void setIdGastoaCartao(Integer idGastoaCartao) {
        this.idGastoaCartao = idGastoaCartao;
    }

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuarioDoCartao() {
        return nomeUsuarioDoCartao;
    }

    public void setNomeUsuarioDoCartao(String nomeUsuarioDoCartao) {
        this.nomeUsuarioDoCartao = nomeUsuarioDoCartao;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricaoGasto() {
        return descricaoGasto;
    }

    public void setDescricaoGasto(String descricaoGasto) {
        this.descricaoGasto = descricaoGasto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getDataDoGasto() {
        return dataDoGasto;
    }

    public void setDataDoGasto(Date dataDoGasto) {
        this.dataDoGasto = dataDoGasto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getFechamentoFatura() {
        return fechamentoFatura;
    }

    public void setFechamentoFatura(Integer fechamentoFatura) {
        this.fechamentoFatura = fechamentoFatura;
    }

    public Integer getVencimentoFatura() {
        return vencimentoFatura;
    }

    public void setVencimentoFatura(Integer vencimentoFatura) {
        this.vencimentoFatura = vencimentoFatura;
    }
    
    

}
