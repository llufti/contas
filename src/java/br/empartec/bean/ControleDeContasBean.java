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
import br.empartec.dao.SupermercadoDao;
import br.empartec.entidades.CartaoCredito;
import br.empartec.entidades.Categorias;
import br.empartec.entidades.ControleDeMesSelecionado;
import br.empartec.entidades.Gastos;
import br.empartec.entidades.Receitas;
import br.empartec.entidades.ResumoDoMes;
import br.empartec.entidades.SuperClasse;
import br.empartec.entidades.Supermercado;
import br.empartec.entidades.TituloDescricao;
import br.empartec.entidades.Usuarios;
import br.empartec.grafico.ChartView;
import br.empartec.util.ErroSistema;
import br.empartec.visualizacao.Renderizacao;
import br.empartec.visualizacao.RenderizacaoSubTelas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

@Named
@ViewScoped
public class ControleDeContasBean implements Serializable {

    private Double total;
    private List<String> itensSupermercado = new ArrayList<>();
    private List<String> selectedCities = new ArrayList<>();
    @Inject
    ChartView chartView;
    private Supermercado supermercado;
    @Inject
    private SupermercadoDao supermercadoDao;
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
    private List<Supermercado> listSupermercado;
    private List<SelectItem> itensMercado;
    private String[] itensSelecionados;

    int contador;

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
        supermercado = new Supermercado();
        criarListaSupermercado();
        try {
            calculoDoResumoDoMes();
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void criarListaSupermercado() {
        itensMercado = new ArrayList<>();
        SelectItemGroup cafeDaManha = new SelectItemGroup("Mercearia");
        cafeDaManha.setSelectItems(new SelectItem[]{
            new SelectItem("Arroz", "Arroz"),
            new SelectItem("Feijão", "Feijão"),
            new SelectItem("Óleo", "Óleo"),
            new SelectItem("Azeite de oliva", "Azeite de oliva"),
            new SelectItem("Vinagre", "Vinagre"),
            new SelectItem("Açúcar", "Açúcar"),
            new SelectItem("Milho para pipoca", "Milho para pipoca"),
            new SelectItem("Farinha de trigo", "Farinha de trigo"),
            new SelectItem("Fermento em pó", "Fermento em pó"),
            new SelectItem("Aveia", "Aveia"),
            new SelectItem("Cereais", "Cereais"),
            new SelectItem("Amido de milho", "Amido de milho"),
            new SelectItem("Farinha de mandioca", "Farinha de mandioca"),
            new SelectItem("Extrato de tomate", "Extrato de tomate"),
            new SelectItem("Macarrão", "Macarrão"),
            new SelectItem("Queijo ralado", "Queijo ralado"),
            new SelectItem("Enlatados", "Enlatados"),
            new SelectItem("Bolachas", "Bolachas"),
            new SelectItem("Petiscos", "Petiscos"),
            new SelectItem("Pães", "Pães"),
            new SelectItem("Maionese", "Maionese"),
            new SelectItem("Ketchup", "Ketchup"),
            new SelectItem("Mostarda", "Mostarda"),
            new SelectItem("Manteiga", "Manteiga"),
            new SelectItem("Requeijão", "Requeijão"),
            new SelectItem("Frios", "Frios"),
            new SelectItem("Mel", "Mel"),
            new SelectItem("Sal", "Sal"),
            new SelectItem("Temperos secos", "Temperos secos"),
            new SelectItem("Especiarias", "Especiarias"),});

        SelectItemGroup cafeTarde = new SelectItemGroup("Café Da Tarde");
        cafeTarde.setSelectItems(new SelectItem[]{
            new SelectItem("Ovos", "Ovos"),
            new SelectItem("Verduras", "Verduras"),
            new SelectItem("Legumes", "Legumes"),
            new SelectItem("Vegetais variados", "Vegetais variados"),
            new SelectItem("Frutas da estação", "Frutas da estação"),
            new SelectItem("Cebola", "Cebola"),
            new SelectItem("Alho", "Alho"),
            new SelectItem("Ervas e temperos frescos", "Ervas e temperos frescos")
        });

        itensMercado.add(cafeTarde);
        itensMercado.add(cafeDaManha);

    }

    //------------------------Supermercado-----------------
    public void exibirListaDeItens() {
        limparListaItensSelecionados();
        renderizacao.supermercadoExibirItensSupermercado();
    }

    public void limparListaItensSelecionados() {
        if (itensSelecionados != null) {
            Arrays.fill(itensSelecionados, null);
        }
    }

    public void salvarItensSupermercado() throws ErroSistema {
        try {
            supermercadoDao.salvarItensSupermercado(supermercado);
            int idLista = supermercadoDao.supermercadoBuscarIdLista();
            supermercado.setIdListaCompra(idLista);
            supermercadoBuscarItensPeloIdLista();
            renderizacao.supermercadoExibirDataTableItensLista();
            supermercado = new Supermercado();
            adicionarMensagem("Itens Salvos Com Sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void supermercadoSalvarItensDuranteCompra() throws ErroSistema {
        try {
            supermercadoDao.salvarItensSupermercadoDuranteCompra(supermercado);
            renderizacao.supermercadoExibirDataTableItensLista();
            supermercadoBuscarItensPeloIdLista();
            supermercado.setItem("");
            adicionarMensagem("Itens Salvos Com Sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void supermercadoExibirTxtNovaLista() {
        limparListaItensSelecionados();
        supermercado = new Supermercado();
        supermercadoBuscarTodasListas();
        renderizacao.supermercadoExibirDataTableLista();
        renderizacao.supermercadoExibirTxtNovaLista();
    }

    public void supermercadoSelecionarLista(Supermercado supermercado) throws ErroSistema {
        this.supermercado = supermercado;
        supermercadoBuscarItensPeloIdLista();
        supermercadoSomarValorTotalListaPeloId();
        renderizacao.supermercadoExibirDataTableItensLista();
        renderizacao.supermercadoOcultarTableBtnDeletarLista();

    }

    public void supermercadoEditarNomeLista(Supermercado supermercado) throws ErroSistema {
        this.supermercado = supermercado;
        renderizacao.supermercadoExibirTxtNovaLista();
    }

    public void supermercadoSelecionarItemLista(Supermercado supermercado) {
        this.supermercado = supermercado;

    }

    public void supermercadoBuscarTodasListas() {
        try {
            listSupermercado = supermercadoDao.supermercadoBuscarListas(supermercado);
            if (listSupermercado == null || listSupermercado.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void supermercadoBuscarItensPeloIdLista() {
        try {
            listSupermercado = supermercadoDao.supermercadoBuscarItensPeloIdLista(supermercado);
            if (listSupermercado == null || listSupermercado.size() < 1) {
                adicionarMensagem("Nao ha cadastros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void supermercadoSalvarLista() {
        try {
            if (!"".equals(supermercado.getDescricaoLista())) {
                supermercadoDao.salvarListaSupermercado(supermercado, usuarios);
                adicionarMensagem("Lista Salva com Sucesso!", FacesMessage.SEVERITY_INFO);
                if (supermercado.getIdListaCompra() == null) {
                    renderizacao.supermercadoExibirItensSupermercado();
                } else {
                    supermercadoDao.supermercadoRenomearNomeDaLista(supermercado, usuarios);
                    supermercadoBuscarTodasListas();
                    renderizacao.supermercadoExibirBtnNovaLista();
                    renderizacao.supermercadoExibirTableBtnSelecionarLista();
                }
            } else {
                adicionarMensagem("Insira o nome da lista!", FacesMessage.SEVERITY_ERROR);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void supermercadoDeletarLista(Supermercado supermercado) throws ErroSistema {
        try {
            this.supermercado = supermercado;
            supermercadoDao.supermercadoDeletarLista(supermercado);
            supermercadoDao.supermercadoDeletarItensLista(supermercado);
            listSupermercado.remove(supermercado);
            renderizacao.supermercadoExibirTableBtnSelecionarLista();

            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void supermercadoDeletarItensPeloId(Supermercado supermercado) throws ErroSistema {
        try {
            this.supermercado = supermercado;
            supermercado.setItem("");
            supermercadoDao.supermercadoDeletarItensPeloId(supermercado);
            listSupermercado.remove(supermercado);
            renderizacao.supermercadoOcultarTableBtnDeletarLista();
            supermercadoBuscarItensPeloIdLista();
            supermercadoSomarValorTotalListaPeloId();
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    //------------------------resumo do mes-----------------
    public void calculoDoResumoDoMes() throws ErroSistema {
        resumoDoMesDao.resumoBuscarTotalDeGastos(resumoDoMes, ControleDeMesSelecionado.intMes, ControleDeMesSelecionado.intAno);
        resumoDoMesDao.resumoBuscarTotalDeGastosCartao(resumoDoMes, ControleDeMesSelecionado.intMes, ControleDeMesSelecionado.intAno);
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
        } else if (renderizacao.isCartaoTodasAsDespesas()) {
            cartaoBuscarGastosCartoes();
        }
    }

    public void selececionarMesAnterior() throws ErroSistema {
        controleDeMesSelecionado.selecionarMesAnterior();
        calculoDoResumoDoMes();
        if (renderizacao.isDataTableDeGastos()) {
            gastoBuscarTodosGastos();
        } else if (renderizacao.isDataTableDeReceitas()) {
            receitasBuscarReceitas();
        } else if (renderizacao.isCartaoTodasAsDespesas()) {
            cartaoBuscarGastosCartoes();
        }
    }

    public void exibirTelaCategoriasGasto() throws ErroSistema {
        tituloDescricao.setTituloDescricao("CATEGORIAS");
        renderizacao.exibirCategoriaDespesas();
        renderizacao.ocultarDataTableColunaDelEdit();
        renderizacao.ocultaTxtGastoRepete();
        renderizacaoSubTelas.tituloSemMenuExibir();
        renderizacao.ocultarTodasDespesasDoCartao();

    }

    public void exibirTelaSupermercado() throws ErroSistema {
        supermercadoBuscarTodasListas();
        renderizacaoSubTelas.tituloSemMenuOcultar();
        renderizacao.supermercadoExibirBtnNovaLista();
        renderizacao.supermercadoExibirDataTableLista();
        renderizacao.exibirSupermercado();
        renderizacao.supermercadoExibirTableBtnSelecionarLista();

    }

    public void exibirTelaInserirReceitas() throws ErroSistema {
        tituloDescricao.setTituloDescricao("RECEITAS");
        gastos = new Gastos();
        receitas = new Receitas();
        renderizacao.exibirInserirReceitas();
        renderizacao.ocultarTodasDespesasDoCartao();
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

    public void cartaoEditarGastoCartao(SuperClasse superClasse) {

        this.superClasse = superClasse;
        gastos.setDescGasto(superClasse.getDescricaoGasto());
        gastos.setValor(superClasse.getValor());
        gastos.setId(superClasse.getIdGastoaCartao());
        cartaoCredito.setFechamentoFatura(superClasse.getFechamentoFatura());
        cartaoCredito.setVencimentoFatura(superClasse.getVencimentoFatura());
        cartaoCredito.setIdCartao(superClasse.getIdCartao());
        cartaoCredito.setNome(superClasse.getNomeUsuarioDoCartao());
        cartaoCredito.setBandeira(superClasse.getBandeira());
        cartaoCredito.setNumero(superClasse.getNumero());
        renderizacao.exibirInserirDespesasCartao();
        renderizacao.ocultarTodasDespesasDoCartao();
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

    public void cartaoDeletarGastoCartao(SuperClasse superClasse) throws ErroSistema {
        try {
            cartaoCreditoDao.cartaoDeletarGastoCartao(superClasse);
            calculoDoResumoDoMes();
            listaDeGastosCartao.remove(superClasse);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(ControleDeContasBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void cartaoInserirGastosComCartao(CartaoCredito cartaoCredito) throws ErroSistema {
        this.cartaoCredito = cartaoCredito;
        renderizacao.ocultarSelecionarProximoMes();
        tituloDescricao.setTituloDescricao("CARTÃO");
        renderizacaoSubTelas.tituloSemMenuExibir();
        renderizacao.exibirInserirDespesasCartao();
    }

    public void cartaoSalvarGastosComCartao() throws ErroSistema {
        cartaoCreditoDao.cartaoSalvarGastosComCartao(cartaoCredito, usuarios, gastos);
        calculoDoResumoDoMes();
        gastos = new Gastos();
        adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
    }

    public void cartaoAdicionarCartao() {
        cartaoCredito = new CartaoCredito();
        cartaoCreditoDao = new CartaoCreditoDao();
        tituloDescricao.setTituloDescricao("ADICIONAR");
        renderizacao.exibirTelaInserirCartao();

    }

    public void cartaoExibirTelaSelecionarCartao() throws ErroSistema {
        cartaoBuscarCartoes();
        tituloDescricao.setTituloDescricao("CARTÃO");
        renderizacao.exibirTelaSelecionarCartao();
        renderizacao.ocultarTodasDespesasDoCartao();
        renderizacao.exibirSelecionar();

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

    public void cartaoBuscarIdNumero() {
        try {
            listaCartaoCredito = cartaoCreditoDao.cartaoBuscarIdCartoesPeloUsuario(cartaoCredito);
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
            cartaoBuscarIdNumero();
            tituloDescricao.setTituloDescricao("CARTÕES");

            listaDeGastosCartao = cartaoCreditoDao.cartaoBuscarGastosCartoes(cartaoCredito, gastos, superClasse, usuarios);

            renderizacao.exibirTodasDespesasDoCartao();
            renderizacao.ocultarInserirDespesasCartao();

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

    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    public SupermercadoDao getSupermercadoDao() {
        return supermercadoDao;
    }

    public void setSupermercadoDao(SupermercadoDao supermercadoDao) {
        this.supermercadoDao = supermercadoDao;
    }

    public List<String> getItensSupermercado() {
        return itensSupermercado;
    }

    public void setItensSupermercado(List<String> itensSupermercado) {
        this.itensSupermercado = itensSupermercado;
    }

    public List<String> getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(List<String> selectedCities) {
        this.selectedCities = selectedCities;
    }

    public List<Supermercado> getListSupermercado() {
        return listSupermercado;
    }

    public void setListSupermercado(List<Supermercado> listSupermercado) {
        this.listSupermercado = listSupermercado;
    }

    public void supermercadoSomarValorTotalListaPeloId() throws ErroSistema {
        Double totalTodosItensDaLista;
        totalTodosItensDaLista = supermercadoDao.supermercadoBuscarTotalTodosItensListaPeloId(supermercado.getIdListaCompra());
        supermercado.setTotalTodosItens(totalTodosItensDaLista);
    }

    public void onCellEdit(CellEditEvent event) throws ErroSistema {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            String teste = newValue.getClass().getName();
            if (teste.contains("Integer")) {
                supermercadoDao.supermercadoSalvarQuantidadeItem(supermercado, usuarios);
                int quantidade = supermercadoDao.supermercadoBuscarQuantidadePeloIdItem(supermercado);
                Double preco = supermercadoDao.supermercadoBuscarPrecoPeloIdItem(supermercado);
                total = quantidade * preco;
                supermercadoDao.supermercadoSalvarTotalItem(supermercado, usuarios);
                supermercadoSomarValorTotalListaPeloId();
            } else {
                supermercadoSomarValorTotalListaPeloId();
                supermercadoDao.supermercadoSalvarValorItem(supermercado, usuarios);
                int quantidade = supermercadoDao.supermercadoBuscarQuantidadePeloIdItem(supermercado);
                Double preco = supermercadoDao.supermercadoBuscarPrecoPeloIdItem(supermercado);
                total = quantidade * preco;
                supermercadoDao.supermercadoSalvarTotalItem(supermercado, usuarios);
                supermercadoSomarValorTotalListaPeloId();
            }
        }
    }

    public List<SelectItem> getItensMercado() {
        return itensMercado;
    }

    public void setItensMercado(List<SelectItem> itensMercado) {
        this.itensMercado = itensMercado;
    }

    public String[] getItensSelecionados() {
        return itensSelecionados;
    }

    public void setItensSelecionados(String[] itensSelecionados) {
        this.itensSelecionados = itensSelecionados;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
