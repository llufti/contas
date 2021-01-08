package br.empartec.generic;

import br.empartec.util.ErroSistema;
import br.empartec.visualizacao.Renderizacao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class CrudeBeanControleDeContas<R, G, C, E, U, D extends CrudeDaoControleDeContas> {

    private R resumoDoMes;
    private G gastos;
    private E receitas;
    private C cartao;
    private U usuarios;

    private List<E> listaReceitas;
    private List<C> listaCartaoCredito;
    private List<G> listaDeGastos;
    private List<G> listaDeGastosCategoria;
    private List<R> resumoDoMesLista;

    private Renderizacao renderizacao;

    @PostConstruct
    public void init() {
        System.out.println("Entrou no bean");
        resumoDoMes = criarNovoResumoDoMes();
        gastos = criarNovoGasto();
        cartao = criarNovoCartao();
        receitas = criarNovoReceita();
        usuarios = criarNovoUsuario();
        renderizacao = new Renderizacao();
        try {
            calculoResumoDoMes();
            getDao().resumoMesAtualEscreverNoMesSelecionado(resumoDoMes);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //-------------------------navegação-----
    public void exibirTelaCategoriasGasto() throws ErroSistema {

        renderizacao.exibirCategoriaDespesas();
        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacao.ocultaTxtGastoRepete();
        calculoResumoDoMes();
    }

    public void exibirTelaInserirReceitas() throws ErroSistema {
        gastos = criarNovoGasto();
        receitas = criarNovoReceita();
        renderizacao.exibirInserirReceitas();

        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacao.ocultaTxtGastoRepete();
        calculoResumoDoMes();
    }

    public void exibirTelaRelatorios() throws ErroSistema {
        gastosBuscarTodosGastos();
        renderizacao.exibirRelatorio();
        renderizacao.exibirDataTableDeGastos();
        renderizacao.ocultarDataTableColunaDelEdit();
    }

    //------------------resumo do mes ---------
    public void calculoResumoDoMes() throws ErroSistema {
        getDao().resumoBuscarTotalDeGastosDoMesAtual(resumoDoMes);
        getDao().resumoBuscarTotalDeReceitasDoMesAtual(resumoDoMes);
        getDao().resumoBuscarSaldo(resumoDoMes);
    }

    public void resumoBuscarProximoMes() throws ErroSistema {
        renderizacao.ocultarDataTableColunaDelEdit();
        getDao().resumoBuscarResumoDoProximoMes(resumoDoMes);
        getDao().resumoBuscarSaldo(resumoDoMes);
        gastosBuscarTodosGastos();
        buscarTotalDeGastosDasCategorias();
        receitasBuscarReceitas();
    }

    public void resumoBuscarMesAnterior() throws ErroSistema {
        renderizacao.ocultarDataTableColunaDelEdit();
        getDao().resumoBuscarResumoDoMesAnterior(resumoDoMes);
        getDao().resumoBuscarSaldo(resumoDoMes);
        gastosBuscarTodosGastos();
        buscarTotalDeGastosDasCategorias();
        receitasBuscarReceitas();

    }

    //__________________gastos__________
    public void gastosDeletarGastos(G gastos) throws ErroSistema {
        try {
            getDao().gastosDeletarGastos(gastos);
            calculoResumoDoMes();
            gastosBuscarTodosGastos();

            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void gastosEditarGastos(G gastos) {
        this.gastos = gastos;
        renderizacao.exibirInserirDespesas();

    }

    public void gastosBuscarTodosGastos() throws ErroSistema {
        listaDeGastos = getDao().gastosBuscarTodosGastos(gastos);

    }

    public void buscarTotalDeGastosDasCategorias() {
        try {
            listaDeGastosCategoria = getDao().buscarTotalDeGastosPorCategoria(gastos);

        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void gastosAdicionarGastosPorCategoria(String categoria) throws ErroSistema {
        getDao().gastosAdicionarGastosPorCategoria(gastos, categoria);
        renderizacao.exibirInserirDespesas();
        renderizacao.exibirSelecionarProximoMes();
    }

    public void exibirTabelaTodosGastos() throws ErroSistema {
        gastosBuscarTodosGastos();
        renderizacao.exibirDataTableDeGastos();
        renderizacao.ocultarDataTableColunaDelEdit();
    }

    public void gastosSalvarGastos() throws ErroSistema {
        try {
            getDao().gastosSalvarGastos(gastos, cartao);
            calculoResumoDoMes();

            gastos = criarNovoGasto();
            renderizacao.exibirCategoriaDespesas();

            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);

        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void exibirTabelaCategorias() {
        buscarTotalDeGastosDasCategorias();
        renderizacao.exibirDataTableTotalCategorias();
        renderizacao.ocultarDataTableColunaDelEdit();
    }

    // ---------------------catao--------------
    public void cartaoEditarCartao(C cartao) {
        this.cartao = cartao;
        renderizacao.exibirTelaInserirCartao();
    }

    public void cartaoDeletarCartao(C cartao) throws ErroSistema {
        try {
            getDao().cartaoDeletarCartao(cartao);
            listaCartaoCredito.remove(cartao);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void cartaoAdicionarCartao() {
        cartao = criarNovoCartao();
        renderizacao.exibirTelaInserirCartao();

    }

    public void cartaoExibirTelaSelecionarCartao() throws ErroSistema {
        cartaoBuscarCartoes();
        renderizacao.exibirTelaSelecionarCartao();
    }

    public void cartaoSalvarCartao() {
        try {
            getDao().cartaoSalvarCartoes(cartao);
            cartaoExibirTelaSelecionarCartao();
            cartao = criarNovoCartao();
            adicionarMensagem("Cartão Salvo com Sucesso!", FacesMessage.SEVERITY_INFO);
            renderizacao.exibirTelaSelecionarCartao();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cartaoBuscarCartoes() {
        try {
            listaCartaoCredito = getDao().cartaoBuscarCartoes(cartao);
            if (listaCartaoCredito == null || listaCartaoCredito.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cartaoAdicionarGastosPorCategoriaCartao(String categoria, C cartao) throws ErroSistema {
        getDao().gastosAdicionarGastosPorCategoria(gastos, categoria);
        this.cartao = cartao;
        renderizacao.ocultarSelecionarProximoMes();
        renderizacao.exibirInserirDespesas();
    }

    //---------------------receitas-------------------
    public void receitaDeletarReceita(E receitas) throws ErroSistema {
        try {
            getDao().deletarReceita(receitas);
            calculoResumoDoMes();
            listaReceitas.remove(receitas);
            receitasBuscarReceitas();
            renderizacao.exibirDataTableDeReceitas();
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void receitasEditarReceita(E receitas) {
        this.receitas = receitas;
        renderizacao.exibirInserirReceitas();
    }

    public void salvarReceita() throws ErroSistema {

        try {
            getDao().receitasSalvarReceitas(receitas);
            receitas = criarNovoReceita();
            calculoResumoDoMes();
            adicionarMensagem("Receita adicionada!", FacesMessage.SEVERITY_INFO);

        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void exibirTabelaReceitas() {
        receitasBuscarReceitas();
        renderizacao.exibirRelatorio();
        renderizacao.exibirDataTableDeReceitas();
        renderizacao.ocultarDataTableColunaDelEdit();
    }

    public void receitasBuscarReceitas() {
        try {
            listaReceitas = getDao().receitasBuscarReceitas(receitas);

        } catch (ErroSistema ex) {
            Logger.getLogger(CrudeBeanControleDeContas.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public abstract D getDao();

    public abstract R criarNovoResumoDoMes();

    public abstract G criarNovoGasto();

    public abstract E criarNovoReceita();

    public abstract C criarNovoCartao();

    public abstract U criarNovoUsuario();

    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public Renderizacao getRenderizacao() {
        return renderizacao;
    }

    public void setRenderizacao(Renderizacao renderizacao) {
        this.renderizacao = renderizacao;
    }

    public R getResumoDoMes() {
        return resumoDoMes;
    }

    public void setResumoDoMes(R resumoDoMes) {
        this.resumoDoMes = resumoDoMes;
    }

    public List<R> getResumoDoMesLista() {
        return resumoDoMesLista;
    }

    public void setResumoDoMesLista(List<R> resumoDoMesLista) {
        this.resumoDoMesLista = resumoDoMesLista;
    }

    public G getGastos() {
        return gastos;
    }

    public void setGastos(G gastos) {
        this.gastos = gastos;
    }

    public List<G> getListaDeGastos() {
        return listaDeGastos;
    }

    public void setListaDeGastos(List<G> listaDeGastos) {
        this.listaDeGastos = listaDeGastos;
    }

    public C getCartao() {
        return cartao;
    }

    public void setCartao(C cartao) {
        this.cartao = cartao;
    }

    public List<C> getListaCartaoCredito() {
        return listaCartaoCredito;
    }

    public void setListaCartaoCredito(List<C> listaCartaoCredito) {
        this.listaCartaoCredito = listaCartaoCredito;
    }

    public E getReceitas() {
        return receitas;
    }

    public void setReceitas(E receitas) {
        this.receitas = receitas;
    }

    public List<E> getListaReceitas() {
        return listaReceitas;
    }

    public void setListaReceitas(List<E> listaReceitas) {
        this.listaReceitas = listaReceitas;
    }

    public List<G> getListaDeGastosCategoria() {
        return listaDeGastosCategoria;
    }

    public void setListaDeGastosCategoria(List<G> listaDeGastosCategoria) {
        this.listaDeGastosCategoria = listaDeGastosCategoria;
    }

    public U getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(U usuarios) {
        this.usuarios = usuarios;
    }
    

}
