/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.entidades;

import java.util.List;

/**
 *
 * @author Luciano
 */
public class Cartao {
    
    private String nomeCartao;
    
    private List<SuperClasse> stats;

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public List<SuperClasse> getStats() {
        return stats;
    }

    public void setStats(List<SuperClasse> stats) {
        this.stats = stats;
    }
    
    
    
}
