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
public class Receitas {
    
    private Integer receitasId;
    private Integer receitasIdCliente = 1;
    private Integer receitasMesesRepetir = 1;
    private String receitasDesc;
    private Double receitasValor;
    private Date receitasData = new Date();

    public Integer getReceitasId() {
        return receitasId;
    }

    public void setReceitasId(Integer receitasId) {
        this.receitasId = receitasId;
    }

    public String getReceitasDesc() {
        return receitasDesc;
    }

    public void setReceitasDesc(String receitasDesc) {
        this.receitasDesc = receitasDesc;
    }

    public Double getReceitasValor() {
        return receitasValor;
    }

    public void setReceitasValor(Double receitasValor) {
        this.receitasValor = receitasValor;
    }   

    public Date getReceitasData() {
        return receitasData;
    }

    public void setReceitasData(Date receitasData) {
        this.receitasData = receitasData;
    }

    public Integer getReceitasMesesRepetir() {
        return receitasMesesRepetir;
    }

    public void setReceitasMesesRepetir(Integer receitasMesesRepetir) {
        this.receitasMesesRepetir = receitasMesesRepetir;
    }

    public Integer getReceitasIdCliente() {
        return receitasIdCliente;
    }

    public void setReceitasIdCliente(Integer receitasIdCliente) {
        this.receitasIdCliente = receitasIdCliente;
    }
    
    
    
}
