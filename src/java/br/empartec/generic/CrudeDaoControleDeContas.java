package br.empartec.generic;

import br.empartec.util.ErroSistema;
import java.util.List;

public interface CrudeDaoControleDeContas<R, G, C, E> { //representa uma entidade

    public void resumoBuscarTotalDeReceitasDoMesAtual(R entidade) throws ErroSistema;

    public void resumoBuscarTotalDeGastosDoMesAtual(R entidade) throws ErroSistema;

    public void resumoBuscarSaldo(R entidade) throws ErroSistema;

    public void resumoBuscarResumoDoProximoMes(R entidade) throws ErroSistema;

    public void resumoBuscarResumoDoMesAnterior(R entidade) throws ErroSistema;

    public void resumoMesAtualEscreverNoMesSelecionado(R entidade) throws ErroSistema;

    //____________________Gastos____________
    public void gastosDeletarGastos(G entidade) throws ErroSistema;

    public void gastosSalvarGastos(G entidade, C cartao) throws ErroSistema;

    public void gastosAdicionarGastosPorCategoria(G entidade, String categoria) throws ErroSistema;

    public List<G> gastosBuscarTodosGastos(G entidade) throws ErroSistema;

    public List<G> buscarTotalDeGastosPorCategoria(G entidade) throws ErroSistema;

    //-------------------cartão de credito---------
    public void cartaoSalvarCartoes(C entidade) throws ErroSistema;

    public void cartaoDeletarCartao(C entidade) throws ErroSistema;

    public List<C> cartaoBuscarCartoes(C entidade) throws ErroSistema;

    //--------------------receitas-----------
    public void receitasSalvarReceitas(E receitas) throws ErroSistema;

    public List<E> receitasBuscarReceitas(E entidade ) throws ErroSistema;

    public void deletarReceita(E entidade) throws ErroSistema;

}
