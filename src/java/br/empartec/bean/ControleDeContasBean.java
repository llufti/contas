/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.bean;

import br.empartec.dao.CartaoCreditoDao;
import br.empartec.dao.GastosDao;
import br.empartec.dao.ReceitasDao;
import br.empartec.dao.ResumoDoMesDao;
import br.empartec.entidades.CartaoCredito;
import br.empartec.entidades.Categorias;
import br.empartec.entidades.ControleDeMesSelecionado;
import br.empartec.entidades.Gastos;
import br.empartec.entidades.Receitas;
import br.empartec.entidades.ResumoDoMes;
import br.empartec.entidades.SuperClasse;
import br.empartec.entidades.TituloDescricao;
import br.empartec.entidades.Usuarios;
import br.empartec.grafico.ChartView;
import br.empartec.util.ErroSistema;
import br.empartec.visualizacao.Renderizacao;
import br.empartec.visualizacao.RenderizacaoSubTelas;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ControleDeContasBean implements Serializable {

    @Inject
    ChartView chartView;
    private CartaoCredito cartaoCredito;
    private Gastos gastos;
    private Receitas receitas;
    private ResumoDoMes resumoDoMes;
    private Usuarios usuarios;
    private Renderizacao renderizacao;
    private Categorias categorias;
    private TituloDescricao tituloDescricao;
    private GastosDao gastosDao;
    private CartaoCreditoDao cartaoCreditoDao;
    private ReceitasDao receitasDao;
    private ControleDeMesSelecionado controleDeMesSelecionado;
    private ResumoDoMesDao resumoDoMesDao;
    private RenderizacaoSubTelas renderizacaoSubTelas;
    private SuperClasse superClasse;
    
    private List<Receitas> listaReceitas;
    private List<CartaoCredito> listaCartaoCredito;
    private List<CartaoCredito> listaCartoesCadastrados;
    private List<SuperClasse> listaDeGastosCartao;
    private List<Gastos> listaDeGastos;

    @PostConstruct
    public void init() {
        System.out.println("Entrou no bean");
        cartaoCredito = new CartaoCredito();
        gastos = new Gastos();
        receitas = new Receitas();
        resumoDoMes = new ResumoDoMes();
        usuarios = new Usuarios();
        renderizacao = new Renderizacao();
        gastosDao = new GastosDao();
        cartaoCreditoDao = new CartaoCreditoDao();
        tituloDescricao = new TituloDescricao();
        receitasDao = new ReceitasDao();
        controleDeMesSelecionado = new ControleDeMesSelecionado();
        resumoDoMesDao = new ResumoDoMesDao();
        renderizacaoSubTelas = new RenderizacaoSubTelas();
        superClasse = new SuperClasse();
        controleDeMesSelecionado.definirDataEscolhida();
        tituloDescricao.setTituloDescricao("CATEGORIAS");

        try {
            exibirTelaCategoriasGasto();
            calculoDoResumoDoMes();
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //------------------------resumo do mes-----------------
    public void calculoDoResumoDoMes() throws ErroSistema {
        resumoDoMesDao.resumoBuscarTotalDeGastos(resumoDoMes, ControleDeMesSelecionado.intMes, ControleDeMesSelecionado.intAno);
        resumoDoMesDao.resumoBuscarTotalDeReceitasDoProximoMes(resumoDoMes, ControleDeMesSelecionado.intMes, ControleDeMesSelecionado.intAno);
        resumoDoMesDao.resumoBuscarSaldo(resumoDoMes);
    }

    //-------------------------navegação-----
    public void selececionarProximoMes() throws ErroSistema {
        controleDeMesSelecionado.selecionarProximoMêsAno();
        calculoDoResumoDoMes();
        if (renderizacao.isDataTableDeGastos()) {
            gastoBuscarTodosGastos();
        } else if (renderizacao.isDataTableDeReceitas()) {
            receitasBuscarReceitas();
        }
    }

    public void selececionarMesAnterior() throws ErroSistema {
        controleDeMesSelecionado.selecionarMesAnterior();
        calculoDoResumoDoMes();
        if (renderizacao.isDataTableDeGastos()) {
            gastoBuscarTodosGastos();
        } else if (renderizacao.isDataTableDeReceitas()) {
            receitasBuscarReceitas();
        }
    }

    public void exibirTelaCategoriasGasto() throws ErroSistema {
        tituloDescricao.setTituloDescricao("CATEGORIAS");
        renderizacao.exibirCategoriaDespesas();
        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacao.ocultaTxtGastoRepete();
        renderizacaoSubTelas.tituloSemMenuExibir();

    }

    public void exibirTelaInserirReceitas() throws ErroSistema {
        tituloDescricao.setTituloDescricao("RECEITAS");
        gastos = new Gastos();
        receitas = new Receitas();
        renderizacao.exibirInserirReceitas();

        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacao.ocultaTxtGastoRepete();
    }

    public void exibirTelaRelatorios() throws ErroSistema {
        gastoBuscarTodosGastos();
        renderizacao.exibirRelatorio();
        renderizacao.exibirDataTableDeGastos();
        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacaoSubTelas.tituloComMenuRelatoriosExibir();
        tituloDescricao.setTituloDescricao("DESPESAS");
    }

    // ------------------------------despesas---------------------
    public void salvarDespesas() {
        try {
            gastosDao.salvarGastos(gastos);
            gastos = new Gastos();
            tituloDescricao.setTituloDescricao("CATEGORIAS");
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            renderizacao.exibirCategoriaDespesas();

            chartView.createBarModels();
            calculoDoResumoDoMes();
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem("Não foi possivel salvar", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void gastosAdicionarGastosPorCategoria(String categoria) throws ErroSistema {
        tituloDescricao.setTituloDescricao(categoria);
        gastosDao.gastosAdicionarGastosPorCategoria(gastos, categoria);
        renderizacao.exibirInserirDespesas();
        renderizacao.exibirSelecionarProximoMes();
    }

    public void gastoBuscarTodosGastos() {
        try {
            listaDeGastos = gastosDao.gastosBuscarTodosGastos(gastos, usuarios);
            if (listaDeGastos == null || listaDeGastos.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void gastosDeletarGastos(Gastos gastos) throws ErroSistema {
        try {
            chartView.createBarModels();
            gastosDao.gastosDeletarGastos(gastos);
            listaDeGastos.remove(gastos);
            gastoBuscarTodosGastos();
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
            calculoDoResumoDoMes();
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void gastosEditarGastos(Gastos gastos) {
        this.gastos = gastos;
        renderizacao.exibirInserirDespesas();

    }

    //----------------------Gastos Cartão------------------------------------
    public void cartaoEditarCartao(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
        renderizacao.exibirTelaInserirCartao();
    }

    public void cartaoDeletarCartao(CartaoCredito cartaoCredito) throws ErroSistema {
        try {
            cartaoCreditoDao.cartaoDeletarCartao(cartaoCredito);
            listaCartaoCredito.remove(cartaoCredito);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void cartaoInserirGastosComCartao(CartaoCredito cartaoCredito) throws ErroSistema {
        this.cartaoCredito = cartaoCredito;
        renderizacao.ocultarSelecionarProximoMes();
        renderizacao.exibirInserirDespesasCartao();
    }

    public void cartaoSalvarGastosComCartao() throws ErroSistema {
        cartaoCreditoDao.cartaoSalvarGastosComCartao(cartaoCredito, usuarios, gastos);
        gastos = new Gastos();
        adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
    }

    public void cartaoAdicionarCartao() {
        cartaoCreditoDao = new CartaoCreditoDao();
        tituloDescricao.setTituloDescricao("ADICIONAR");
        renderizacao.exibirTelaInserirCartao();

    }

    public void cartaoExibirTelaSelecionarCartao() throws ErroSistema {
        cartaoBuscarCartoes();
        tituloDescricao.setTituloDescricao("CARTÕES");
        renderizacao.exibirTelaSelecionarCartao();
        renderizacaoSubTelas.tituloComMenuCartaoDeCreditoExibir();

    }

    public void cartaoSalvarCartao() {
        try {
            cartaoCreditoDao.cartaoSalvarCartoes(cartaoCredito, usuarios);
            cartaoExibirTelaSelecionarCartao();
            cartaoCredito = new CartaoCredito();
            adicionarMensagem("Cartão Salvo com Sucesso!", FacesMessage.SEVERITY_INFO);
            renderizacao.exibirTelaSelecionarCartao();
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cartaoBuscarCartoes() {
        try {
            listaCartaoCredito = cartaoCreditoDao.cartaoBuscarCartoes(cartaoCredito);
            if (listaCartaoCredito == null || listaCartaoCredito.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    public void cartaoBuscarGastosCartoes() {
        try {
            cartaoBuscarCartoesCadastrados();
            listaDeGastosCartao = cartaoCreditoDao.cartaoBuscarGastosCartoes(cartaoCredito, gastos, superClasse,usuarios);
            System.out.println("registros encontrados " + listaDeGastosCartao.size());
                    
            if (listaDeGastosCartao == null || listaDeGastosCartao.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cartaoBuscarCartoesCadastrados() {
        try {
            listaCartoesCadastrados = cartaoCreditoDao.cartaoBuscarCatoesCadastrados(cartaoCredito, usuarios);
            cartaoCredito.setIdCartao(listaCartoesCadastrados.get(0).getIdCartao());
            if (listaCartoesCadastrados == null || listaCartoesCadastrados.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    //-------------------------receitas------------
    public void receitaDeletarReceita(Receitas receitas) throws ErroSistema {
        try {
            receitasDao.deletarReceita(receitas);
            listaReceitas.remove(receitas);
            receitasBuscarReceitas();
            renderizacao.exibirDataTableDeReceitas();
            chartView.createBarModels();
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
            calculoDoResumoDoMes();
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void receitasEditarReceita(Receitas receitas) {
        this.receitas = receitas;
        renderizacao.exibirInserirReceitas();
    }

    public void salvarReceita() throws ErroSistema {

        try {
            receitasDao.receitasSalvarReceitas(receitas);
            receitas = new Receitas();
            chartView.createBarModels();
            exibirTabelaReceitas();
            adicionarMensagem("Receita adicionada!", FacesMessage.SEVERITY_INFO);
            calculoDoResumoDoMes();

        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void exibirTabelaReceitas() {
        tituloDescricao.setTituloDescricao("RECEITAS");
        receitasBuscarReceitas();
        renderizacao.exibirRelatorio();
        renderizacao.exibirDataTableDeReceitas();
        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacaoSubTelas.tituloComMenuRelatoriosExibir();
    }

    public void receitasBuscarReceitas() {
        try {
            listaReceitas = receitasDao.receitasBuscarReceitas(receitas);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public List<CartaoCredito> getListaCartoesCadastrados() {
        return listaCartoesCadastrados;
    }

    public void setListaCartoesCadastrados(List<CartaoCredito> listaCartoesCadastrados) {
        this.listaCartoesCadastrados = listaCartoesCadastrados;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Gastos getGastos() {
        return gastos;
    }

    public void setGastos(Gastos gastos) {
        this.gastos = gastos;
    }

    public Receitas getReceitas() {
        return receitas;
    }

    public void setReceitas(Receitas receitas) {
        this.receitas = receitas;
    }

    public ResumoDoMes getResumoDoMes() {
        return resumoDoMes;
    }

    public void setResumoDoMes(ResumoDoMes resumoDoMes) {
        this.resumoDoMes = resumoDoMes;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Renderizacao getRenderizacao() {
        return renderizacao;
    }

    public void setRenderizacao(Renderizacao renderizacao) {
        this.renderizacao = renderizacao;
    }

    public List<Receitas> getListaReceitas() {
        return listaReceitas;
    }

    public void setListaReceitas(List<Receitas> listaReceitas) {
        this.listaReceitas = listaReceitas;
    }

    public List<CartaoCredito> getListaCartaoCredito() {
        return listaCartaoCredito;
    }

    public void setListaCartaoCredito(List<CartaoCredito> listaCartaoCredito) {
        this.listaCartaoCredito = listaCartaoCredito;
    }

    public List<Gastos> getListaDeGastos() {
        return listaDeGastos;
    }

    public void setListaDeGastos(List<Gastos> listaDeGastos) {
        this.listaDeGastos = listaDeGastos;
    }

    public GastosDao getGastosDao() {
        return gastosDao;
    }

    public void setGastosDao(GastosDao gastosDao) {
        this.gastosDao = gastosDao;
    }

    public Categorias getCategorias() {
        return categorias;
    }

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    public CartaoCreditoDao getCartaoCreditoDao() {
        return cartaoCreditoDao;
    }

    public void setCartaoCreditoDao(CartaoCreditoDao cartaoCreditoDao) {
        this.cartaoCreditoDao = cartaoCreditoDao;
    }

    public TituloDescricao getTituloDescricao() {
        return tituloDescricao;
    }

    public void setTituloDescricao(TituloDescricao tituloDescricao) {
        this.tituloDescricao = tituloDescricao;
    }

    public ReceitasDao getReceitasDao() {
        return receitasDao;
    }

    public void setReceitasDao(ReceitasDao receitasDao) {
        this.receitasDao = receitasDao;
    }

    public ControleDeMesSelecionado getControleDeMesSelecionado() {
        return controleDeMesSelecionado;
    }

    public void setControleDeMesSelecionado(ControleDeMesSelecionado controleDeMesSelecionado) {
        this.controleDeMesSelecionado = controleDeMesSelecionado;
    }

    public ResumoDoMesDao getResumoDoMesDao() {
        return resumoDoMesDao;
    }

    public void setResumoDoMesDao(ResumoDoMesDao resumoDoMesDao) {
        this.resumoDoMesDao = resumoDoMesDao;
    }

    public RenderizacaoSubTelas getRenderizacaoSubTelas() {
        return renderizacaoSubTelas;
    }

    public void setRenderizacaoSubTelas(RenderizacaoSubTelas renderizacaoSubTelas) {
        this.renderizacaoSubTelas = renderizacaoSubTelas;
    }

    public SuperClasse getSuperClasse() {
        return superClasse;
    }

    public void setSuperClasse(SuperClasse superClasse) {
        this.superClasse = superClasse;
    }

    public List<SuperClasse> getListaDeGastosCartao() {
        return listaDeGastosCartao;
    }

    public void setListaDeGastosCartao(List<SuperClasse> listaDeGastosCartao) {
        this.listaDeGastosCartao = listaDeGastosCartao;
    }
    
    

}
