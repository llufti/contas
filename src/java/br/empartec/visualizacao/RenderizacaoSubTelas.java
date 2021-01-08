/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.visualizacao;

/**
 *
 * @author Luciano
 */
public class RenderizacaoSubTelas {

    private String exibirTituloComMenuRelatorios = "none";
    private String exibirTituloComMenuCartao = "none";

    private String tituloTelaAtualSemMenu = "menuSemTitulo";

    public boolean isTituloSemMenu() {
        return "menuSemTitulo".equals(tituloTelaAtualSemMenu);
    }

    public void tituloSemMenuExibir() {
        tituloTelaAtualSemMenu = "menuSemTitulo";
         setExibirTituloComMenuRelatorios("none");
        setExibirTituloComMenuCartao("none");
    }

    public void tituloSemMenuOcultar() {
        tituloTelaAtualSemMenu = "";
    }

    public boolean isTituloComMenuRelatorios() {
        return "relatorioComTitulo".equals(tituloTelaAtualSemMenu);
    }

    public void tituloComMenuRelatoriosExibir() {
        tituloTelaAtualSemMenu = "relatorioComTitulo";
        setExibirTituloComMenuRelatorios("true");
        setExibirTituloComMenuCartao("none");
    }

    public boolean isTituloComMenuCartaoDeCredito() {
        return "cartaoDeCreditoComMenu".equals(tituloTelaAtualSemMenu);
    }

    public void tituloComMenuCartaoDeCreditoExibir() {
        tituloTelaAtualSemMenu = "cartaoDeCreditoComMenu";
        setExibirTituloComMenuRelatorios("none");
        setExibirTituloComMenuCartao("true");
    }

    public String getExibirTituloComMenuRelatorios() {
        return exibirTituloComMenuRelatorios;
    }

    public void setExibirTituloComMenuRelatorios(String exibirTituloComMenuRelatorios) {
        this.exibirTituloComMenuRelatorios = exibirTituloComMenuRelatorios;
    }

    public String getExibirTituloComMenuCartao() {
        return exibirTituloComMenuCartao;
    }

    public void setExibirTituloComMenuCartao(String exibirTituloComMenuCartao) {
        this.exibirTituloComMenuCartao = exibirTituloComMenuCartao;
    }

}
