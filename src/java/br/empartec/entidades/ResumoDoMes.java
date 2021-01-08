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
public class ResumoDoMes {
    private Integer receitas;
    private String relatorioDescricaoTelaAtual;
    private Integer despesas;
    private Integer saldo;
    private Date dataAtual = new Date();
    private String corDoSaldo;
    private Integer mesSelecionado;
    private Integer anoSelecionado;
    private String strMesSelecionado;

    public Integer getReceitas() {
        return receitas;
    }

    public void setReceitas(Integer receitas) {
        this.receitas = receitas;
    }

    public String getRelatorioDescricaoTelaAtual() {
        return relatorioDescricaoTelaAtual;
    }

    public void setRelatorioDescricaoTelaAtual(String relatorioDescricaoTelaAtual) {
        this.relatorioDescricaoTelaAtual = relatorioDescricaoTelaAtual;
    }
    

    public Integer getDespesas() {
        return despesas;
    }

    public void setDespesas(Integer despesas) {
        this.despesas = despesas;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }    

    public Date getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(Date dataAtual) {
        this.dataAtual = dataAtual;
    }

    public String getCorDoSaldo() {
        return corDoSaldo;
    }

    public void setCorDoSaldo(String corDoSaldo) {
        this.corDoSaldo = corDoSaldo;
    }

    public Integer getMesSelecionado() {
        return mesSelecionado;
    }

    public void setMesSelecionado(Integer mesSelecionado) {
        this.mesSelecionado = mesSelecionado;
    }

    public Integer getAnoSelecionado() {
        return anoSelecionado;
    }

    public void setAnoSelecionado(Integer anoSelecionado) {
        this.anoSelecionado = anoSelecionado;
    }

    public String getStrMesSelecionado() {
        return strMesSelecionado;
    }

    public void setStrMesSelecionado(String strMesSelecionado) {
        this.strMesSelecionado = strMesSelecionado;
    }         
    
}
