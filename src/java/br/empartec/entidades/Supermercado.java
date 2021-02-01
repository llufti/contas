/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Luciano
 */
public class Supermercado {
    private Integer idSupermercado;
    private Integer idListaCompra;
    private Integer quantidade;
    private String descricaoLista;
    private String item;
    private Date dataLista = new Date();
    private Double preco;
    private Double total;
    private Double totalTodosItens;

    public Integer getIdSupermercado() {
        return idSupermercado;
    }

    public void setIdSupermercado(Integer idSupermercado) {
        this.idSupermercado = idSupermercado;
    }

    public Integer getIdListaCompra() {
        return idListaCompra;
    }

    public void setIdListaCompra(Integer idListaCompra) {
        this.idListaCompra = idListaCompra;
    }

    public String getDescricaoLista() {
        return descricaoLista;
    }

    public void setDescricaoLista(String descricaoLista) {
        this.descricaoLista = descricaoLista;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    
    public Date getDataLista() {
        return dataLista;
    }

    public void setDataLista(Date dataLista) {
        this.dataLista = dataLista;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalTodosItens() {
        return totalTodosItens;
    }

    public void setTotalTodosItens(Double totalTodosItens) {
        this.totalTodosItens = totalTodosItens;
    }
    
    
    
}
