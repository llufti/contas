package br.empartec.visualizacao;

public class Renderizacao {

    int controle = 1;
    int controle2 = 1;

    private String estadoTela = "despesasCategorias";
    private String gastoRepete = " ";
    private String receitaRepete = " ";
    private String btnVisualizarResumo = "exibir";
    private String lblTituloDescricao = "exibir";
    private String supermercado = "listaNova";
    private String txtNovaLista = "exibir";
    private String supermercadoDatableAcoes = "listaBtnSelecionar";

    public boolean isSuperTableExibirBtnSelecionarLista() {
        return "listaBtnSelecionar".equals(supermercadoDatableAcoes);
    }

    public void supermercadoExibirTableBtnSelecionarLista() {
        supermercadoDatableAcoes = "listaBtnSelecionar";
    }
    public boolean isSuperTableExibirBtnDeletarLista() {
        return "listaBtnDeletar".equals(supermercadoDatableAcoes);
    }

    public void supermercadoExibirTableBtnDeletarLista() {
        supermercadoDatableAcoes = "listaBtnDeletar";
    }
    public void supermercadoOcultarTableBtnDeletarLista() {
        supermercadoDatableAcoes = "";
    }
    public boolean isSuperTableExibirBtnEditarLista() {
        return "listaBtnEditar".equals(supermercadoDatableAcoes);
    }

    public void supermercadoExibirTableBtnEditarLista() {
        supermercadoDatableAcoes = "listaBtnEditar";
    }
    public boolean isSuperTableExibirBtnAdicionarItens() {
        return "itensListaAdicionar".equals(supermercadoDatableAcoes);
    }

    public void supermercadoExibirTableBtnAdicionarItens() {
        supermercadoDatableAcoes = "itensListaAdicionar";
    }

   
    public boolean isSupermercadoDataTableItensLista() {
        return "listaItens".equals(supermercado);
    }

    public void supermercadoExibirDataTableItensLista() {
        supermercado = "listaItens";
    }

    public void supermercadoOcultarDataTableItensLista() {
        supermercado = "";
    }
    public boolean isSupermercadoBtnNovaLista() {
        return "listaNova".equals(txtNovaLista);
    }

    public void supermercadoExibirBtnNovaLista() {
        supermercado = "txtNovaLista";
    }

    public void supermercadoOcultarBtnNovaLista() {
        txtNovaLista = "";
    }
    
     public boolean isSupermercadoTxtNovaLista() {
        return "novaTxt".equals(supermercado);
    }

    public void supermercadoExibirTxtNovaLista() {
        supermercado = "novaTxt";
    }

    public void supermercadoOcultarTxtNovaLista() {
        supermercado = "";
    }
     public boolean isSupermercadoDataTableLista() {
        return "listaDataTable".equals(supermercado);
    }

    public void supermercadoExibirDataTableLista() {
        supermercado = "listaDataTable";
    }

    public void supermercadoOcultarDataTableLista() {
        supermercado = "";
    }
    
      public boolean isSupermercadoItens() {
        return "itensSupermercado".equals(supermercado);
    }

    public void supermercadoExibirItensSupermercado() {
        supermercado = "itensSupermercado";
    }

    public void supermercadoOcultarItensSupermercado() {
        supermercado = "";
    }

    public boolean isSuperMercado() {
        return "mercadoSuper".equals(estadoTela);
    }

    public void exibirSupermercado() {
        estadoTela = "mercadoSuper";
    }

    public void ocultarSupermercado() {
        estadoTela = "";
    }

    public boolean isResumoDoMes() {
        return "exibir".equals(btnVisualizarResumo);
    }

    public boolean isGastosRepetem() {
        return "sim".equals(gastoRepete);
    }

    public boolean isReceitaRepetem() {
        return "simReceita".equals(receitaRepete);
    }

    public boolean isInserirDespesas() {
        return "despesasInserir".equals(estadoTela);
    }

    public boolean isInserirDespesasCartao() {
        return "cartaoInserirGastos".equals(estadoTela);
    }

    public boolean isInserirReceitas() {
        return "receitasInserir".equals(estadoTela);
    }

    public boolean isCategoriasDespesas() {
        return "despesasCategorias".equals(estadoTela);
    }

    public boolean isCategoriasReceitas() {
        return "receitasCategoria".equals(estadoTela);
    }

    public boolean isRelatorios() {
        return "relatorio".equals(estadoTela);
    }

    public boolean isTituloDescricao() {
        return "exibir".equals(lblTituloDescricao);
    }

    public void exibirTituloDescricao() {
        lblTituloDescricao = "exibir";
    }

    public void ocultarTituloDescricao() {
        lblTituloDescricao = "";
    }

    public void exibirResumoDoMes() {
        btnVisualizarResumo = "exibir";
    }

    public void ocultarResumoDoMes() {
        btnVisualizarResumo = "ocultar";
    }

    public void exibirInserirDespesas() {
        estadoTela = "despesasInserir";
    }

    public void exibirInserirDespesasCartao() {
        estadoTela = "cartaoInserirGastos";
    }

    public void ocultarInserirDespesasCartao() {
        estadoTela = " ";
    }

    public void exibirInserirReceitas() {
        estadoTela = "receitasInserir";

    }

    public void exibirCategoriaDespesas() {
        estadoTela = "despesasCategorias";

    }

    public void exibirCategoriaReceitas() {
        estadoTela = "receitasCategoria";
    }

    public void exibirRelatorio() {
        estadoTela = "relatorio";

    }

    public void ocultarRelatorio() {
        estadoTela = "";
    }

    public boolean isSelecionarCartao() {
        return "cartaoSelecionar".equals(estadoTela);
    }

    public boolean isInserirCartao() {
        return "cartaoInserir".equals(estadoTela);
    }

    public void exibirTelaInserirCartao() {
        estadoTela = "cartaoInserir";
    }

    public void exibirTelaSelecionarCartao() {
        estadoTela = "cartaoSelecionar";

    }

    public void exibirMesesQueRepete() {
        controle++;

        if (controle == 2) {
            gastoRepete = "sim";

        } else if (controle > 2) {
            gastoRepete = "";
            controle = 1;
        }

    }

    public void exibirMesesQueRepeteReceita() {
        controle2++;

        if (controle2 == 2) {
            receitaRepete = "simReceita";

        } else if (controle2 > 2) {
            receitaRepete = "";
            controle2 = 1;
        }

    }

    public void ocultaTxtGastoRepete() {
        receitaRepete = "";
    }
    private String relatorio = "dataTableGastos";
    private String colunaEditaDeleta = "";

    public boolean isColunaEditar() {
        return "colunaEdita".equals(colunaEditaDeleta);
    }

    public boolean isColunaDeletar() {
        return "colunaDeleta".equals(colunaEditaDeleta);
    }

    public boolean isDataTableTotalCategorias() {
        return "totalCategoriasDataTable".equals(relatorio);
    }

    public boolean isDataTableDeReceitas() {
        return "dataTableReceitas".equals(relatorio);
    }

    public void ocultarDataTableColunaDelEdit() {
        colunaEditaDeleta = "";
    }

    public boolean isDataTableDeGastos() {
        return "dataTableGastos".equals(relatorio);
    }

    public void exibirDataTableDeGastos() {
        relatorio = "dataTableGastos";
    }

    public void ocultarDataTableDeGastos() {
        relatorio = " ";
    }

    public void exibirDataTableDeReceitas() {
        relatorio = "dataTableReceitas";
    }

    public void exibirDataTableTotalCategorias() {
        relatorio = "totalCategoriasDataTable";
    }

    public void exibirColunaEdita() {
        colunaEditaDeleta = "colunaEdita";
    }

    public void exibirColunaDeleta() {
        colunaEditaDeleta = "colunaDeleta";
    }

    public boolean isCartaoTodasAsDespesas() {
        return "despesasTodasCartao".equals(relatorio);
    }

    public void exibirTodasDespesasDoCartao() {
        relatorio = "despesasTodasCartao";
    }

    public void ocultarTodasDespesasDoCartao() {
        relatorio = "";
    }

    private String colunaCartaoInfo = "vencimentoFechamento";
    private String colunaCartao = "selecionar";

    public boolean isColunanaVencimentoFechamento() {
        return "vencimentoFechamento".equals(colunaCartaoInfo);
    }

    public boolean isColunanaBandeiraNumero() {
        return "bandeiraNumero".equals(colunaCartaoInfo);
    }

    public boolean isColunaSelecionarCartao() {
        return "selecionar".equals(colunaCartao);
    }

    public boolean isColunaDeletarCartao() {
        return "deletar".equals(colunaCartao);
    }

    public boolean isColunaEditarCartao() {
        return "editar".equals(colunaCartao);
    }

    public void exibirVencimentoFechamento() {
        colunaCartaoInfo = "vencimentoFechamento";
    }

    public void exibirBandeiraNumero() {
        colunaCartaoInfo = "bandeiraNumero";
    }

    public void exibirDeletar() {
        colunaCartao = "deletar";
    }

    public void exibirEditar() {
        colunaCartao = "editar";
    }

    public void exibirSelecionar() {
        colunaCartao = "selecionar";
    }

    private String proximoMes = "exibir";

    public boolean isSelecionarProximoMes() {
        return "exibir".equals(proximoMes);
    }

    public void exibirSelecionarProximoMes() {
        proximoMes = "exibir";
    }

    public void ocultarSelecionarProximoMes() {
        proximoMes = "ocultar";
    }
}
