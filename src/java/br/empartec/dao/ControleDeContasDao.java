/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.dao;

import br.empartec.entidades.CartaoCredito;
import br.empartec.entidades.Gastos;
import br.empartec.entidades.Receitas;
import br.empartec.entidades.ResumoDoMes;

import br.empartec.generic.CrudeDaoControleDeContas;
import br.empartec.util.ErroSistema;
import br.empartec.util.FabricaConexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luciano
 */
public class ControleDeContasDao implements CrudeDaoControleDeContas<ResumoDoMes, Gastos, CartaoCredito, Receitas> {

    int controleMesAnoParaBusca = 0;

    @Override
    public void resumoBuscarTotalDeReceitasDoMesAtual(ResumoDoMes entidade) throws ErroSistema {
        int mesAtualGastos = mesAtual(entidade);
        int anoAtualGastos = anoAtual(entidade);
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM receitas WHERE MONTH(dataReceita) LIKE ? AND YEAR(dataReceita) LIKE ?");
            ps.setInt(1, mesAtualGastos);
            ps.setInt(2, anoAtualGastos);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    entidade.setReceitas(0);
                } else {
                    entidade.setReceitas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    @Override
    public void resumoBuscarTotalDeGastosDoMesAtual(ResumoDoMes entidade) throws ErroSistema {
        int mesAtualGastos = mesAtual(entidade);
        int anoAtualGastos = anoAtual(entidade);

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastos WHERE MONTH(data) LIKE ?  AND YEAR(data) LIKE ?");
            ps.setInt(1, mesAtualGastos);
            ps.setInt(2, anoAtualGastos);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    entidade.setDespesas(0);
                } else {

                    entidade.setDespesas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }
    }

    @Override
    public void resumoBuscarResumoDoProximoMes(ResumoDoMes entidade) throws ErroSistema {
        controleMesAnoParaBusca++;
        resumoBuscarTotalDeGastosDoProximoMes(entidade);
        resumoBuscarTotalDeReceitasDoProximoMes(entidade);
    }

    @Override
    public void resumoBuscarResumoDoMesAnterior(ResumoDoMes entidade) throws ErroSistema {
        controleMesAnoParaBusca--;
        resumoBuscarTotalDeRceitasDoMesAnterior(entidade);
        resumoBuscarTotalDeGastosDoMesAnterior(entidade);
    }

    public void resumoBuscarTotalDeGastosDoProximoMes(ResumoDoMes entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        entidade.setStrMesSelecionado(format.format(cal.getTime().getTime()));
        strMes = entidade.getStrMesSelecionado().substring(0, 2);
        strAno = entidade.getStrMesSelecionado().substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastos WHERE MONTH(data) LIKE ?  AND YEAR(data) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    entidade.setDespesas(0);
                } else {
                    entidade.setDespesas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }

    }

    public void resumoBuscarTotalDeReceitasDoProximoMes(ResumoDoMes entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        entidade.setStrMesSelecionado(format.format(cal.getTime().getTime()));
        strMes = entidade.getStrMesSelecionado().substring(0, 2);
        strAno = entidade.getStrMesSelecionado().substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM receitas WHERE MONTH(dataReceita) LIKE ?  AND YEAR(dataReceita) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    entidade.setReceitas(0);
                } else {
                    entidade.setReceitas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }

    }

    public void resumoBuscarTotalDeGastosDoMesAnterior(ResumoDoMes entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        entidade.setStrMesSelecionado(format.format(cal.getTime().getTime()));
        strMes = entidade.getStrMesSelecionado().substring(0, 2);
        strAno = entidade.getStrMesSelecionado().substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastos WHERE MONTH(data) LIKE ?  AND YEAR(data) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    entidade.setDespesas(0);
                } else {

                    entidade.setDespesas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }

    }

    public void resumoBuscarTotalDeRceitasDoMesAnterior(ResumoDoMes entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        entidade.setStrMesSelecionado(format.format(cal.getTime().getTime()));
        strMes = entidade.getStrMesSelecionado().substring(0, 2);
        strAno = entidade.getStrMesSelecionado().substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);

       

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM receitas WHERE MONTH(dataReceita) LIKE ?  AND YEAR(dataReceita) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    entidade.setReceitas(0);
                } else {

                    entidade.setReceitas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }

    }

    @Override
    public void resumoBuscarSaldo(ResumoDoMes entidade) throws ErroSistema {
        int receitas = entidade.getReceitas();
        int despesas = entidade.getDespesas();
        int saldo = receitas - despesas;
        entidade.setSaldo(saldo);
        if (saldo < 0) {
            entidade.setCorDoSaldo("red");
        } else {
            entidade.setCorDoSaldo("blue");
        }
    }

    @Override
    public void resumoMesAtualEscreverNoMesSelecionado(ResumoDoMes entidade) throws ErroSistema {

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        Format format = new SimpleDateFormat("MM-yyyy");
        entidade.setStrMesSelecionado(format.format(cal.getTime().getTime()));
    }

    public int mesAtual(ResumoDoMes entidade) {
        int mesAtual;
        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        mesAtual = cal.get(Calendar.MONTH) + 1;

        return mesAtual;
    }

    public int anoAtual(ResumoDoMes entidade) {
        int anoAtual;
        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getDataAtual());
        anoAtual = cal.get(Calendar.YEAR);
        return anoAtual;
    }

    //-----------------------------gastos-----------------------------------------------------------------
    @Override
    public void gastosDeletarGastos(Gastos entidade) throws ErroSistema {

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from gastos where id = ?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }

    }

    @Override
    public void gastosSalvarGastos(Gastos entidade, CartaoCredito cartao) throws ErroSistema {
        int f;
        int j;
        int h;
        int fechamento;
        int diaAtual;
        int vencimento = 0;

        int mes = entidade.getGastosRepete();
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (cartao.getFechamentoFatura() == null) {  // se a categoria não for cartão de credito

                if (entidade.getProximoMes().equals("nao")) {
                    int i;

                    for (i = 0; i < mes; i++) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(entidade.getData());
                        cal.add(Calendar.MONTH, i);
                        if (entidade.getId() == null) {
                            ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`) VALUES (?,?,?,?)");
                        } else {
                            ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=? where id=?");
                            ps.setInt(5, entidade.getId());
                        }
                        ps.setString(1, entidade.getCategoria());
                        ps.setString(2, entidade.getDescGasto());
                        ps.setDouble(3, entidade.getValor());
                        ps.setDate(4, new Date(cal.getTime().getTime()));

                        ps.execute();

                    }
                } else {

                    Calendar calMesProximo = Calendar.getInstance();
                    calMesProximo.setTime(entidade.getData());
                    calMesProximo.add(Calendar.MONTH, 1);
                    if (entidade.getId() == null) {
                        ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`) VALUES (?,?,?,?)");
                    } else {
                        ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=? where id=?");
                        ps.setInt(5, entidade.getId());
                    }
                    ps.setString(1, entidade.getCategoria());
                    ps.setString(2, entidade.getDescGasto());
                    ps.setDouble(3, entidade.getValor());
                    ps.setDate(4, new Date(calMesProximo.getTime().getTime()));

                    ps.execute();
                }
            } // fim categoria não cartão
            else { // se for cartão
                fechamento = cartao.getFechamentoFatura();
                vencimento = cartao.getVencimentoFatura();
                Calendar calCartao = Calendar.getInstance();
                diaAtual = calCartao.get(Calendar.DAY_OF_MONTH);
                calCartao.setTime(entidade.getData());

                if (diaAtual > fechamento && diaAtual < vencimento) { //se o dia da compra estiver dentro do melhor dia de compra
                    Calendar calMesAtual = Calendar.getInstance(); // cria uma instancia que pega o mes atual
                    calMesAtual.setTime(entidade.getData()); // pega o mes atual
                    calMesAtual.add(Calendar.MONTH, 1); // gera um data com o mes que vem
                    Date proximoMesMelhorDia = new Date(calMesAtual.getTime().getTime()); // adiciona o mes recem criado em uma variavel
                    for (j = 0; j < mes; j++) {
                        Calendar calMesQueVem = Calendar.getInstance(); //cria mais uma instancia do me que vem 
                        calMesQueVem.setTime(proximoMesMelhorDia); // seta a intancia com o mes que vem 
                        calMesQueVem.add(Calendar.MONTH, j); // acrescenta a quantidade de repetição a partir do proximo mes 

                        if (entidade.getId() == null) {
                            ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`) VALUES (?,?,?,?)");
                        } else {
                            ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=? where id=?");
                            ps.setInt(5, entidade.getId());
                        }
                        ps.setString(1, entidade.getCategoria());
                        ps.setString(2, entidade.getDescGasto());
                        ps.setDouble(3, entidade.getValor());
                        ps.setDate(4, new Date(calMesQueVem.getTime().getTime()));

                        ps.execute();
                    }
                } else { // se o cartão não esta no melhor dia de compra 
                    if (diaAtual < vencimento) { // se o dia atual for anterior ao vencimento ele coloca o gasto no mesmo mes de vencimento

                        for (h = 0; h < mes; h++) {
                            Calendar calMesVem = Calendar.getInstance();
                            calMesVem.setTime(entidade.getData());
                            calMesVem.add(Calendar.MONTH, h);
                            if (entidade.getId() == null) {
                                ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`) VALUES (?,?,?,?)");
                            } else {
                                ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=? where id=?");
                                ps.setInt(5, entidade.getId());
                            }
                            ps.setString(1, entidade.getCategoria());
                            ps.setString(2, entidade.getDescGasto());
                            ps.setDouble(3, entidade.getValor());
                            ps.setDate(4, new Date(calMesVem.getTime().getTime()));

                            ps.execute();
                        }
                    } else {// se o dia for posterior ao vencimento ele adiciona a conta no mes que vem 
                      
                        Calendar calMesAtual = Calendar.getInstance(); // cria uma instancia que pega o mes atual
                        calMesAtual.setTime(entidade.getData()); // pega o mes atual
                        calMesAtual.add(Calendar.MONTH, 1); // gera um data com o mes que vem
                        Date proximoMes = new Date(calMesAtual.getTime().getTime()); // adiciona o mes recem criado em uma variavel

                        for (f = 0; f < mes; f++) { // se o dia atual for depois do vencimento ele coloca o gasto no proximo mes 
                            Calendar calMesQueVem = Calendar.getInstance(); //cria mais uma instancia do me que vem 
                            calMesQueVem.setTime(proximoMes); // seta a intancia com o mes que vem 
                            calMesQueVem.add(Calendar.MONTH, f); // acrescenta a quantidade de repetição a partir do proximo mes 
                            if (entidade.getId() == null) {
                                ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`) VALUES (?,?,?,?)");
                            } else {
                                ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=? where id=?");
                                ps.setInt(5, entidade.getId());
                            }
                            ps.setString(1, entidade.getCategoria());
                            ps.setString(2, entidade.getDescGasto());
                            ps.setDouble(3, entidade.getValor());
                            ps.setDate(4, new Date(calMesQueVem.getTime().getTime()));

                            ps.execute();
                        }

                    } // fim if
                }
            } // fim else  catao de credito

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        } // fim cath
    }

    @Override
    public void gastosAdicionarGastosPorCategoria(Gastos entidade, String categoria) throws ErroSistema {
        entidade.setCategoria(categoria);
    }

    @Override
    public List<Gastos> gastosBuscarTodosGastos(Gastos entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMesAno;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getData());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        strMesAno = format.format(cal.getTime().getTime());
        strMes = strMesAno.substring(0, 2);
        strAno = strMesAno.substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);

        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM gastos WHERE YEAR(data) LIKE ? AND MONTH(data) LIKE ? ");
            ps.setInt(1, ano);
            ps.setInt(2, mes);
            ResultSet resultSet = ps.executeQuery();

            List<Gastos> entidades = new ArrayList<>();
            while (resultSet.next()) {
                entidade = new Gastos();
                entidade.setId(resultSet.getInt("id"));
                entidade.setCategoria(resultSet.getString("categoria"));
                entidade.setDescGasto(resultSet.getString("descGasto"));
                entidade.setValor(resultSet.getDouble("valor"));
                entidade.setData(resultSet.getDate("data"));
                entidades.add(entidade);
            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    @Override
    public List<Gastos> buscarTotalDeGastosPorCategoria(Gastos entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMesAno;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getData());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        strMesAno = format.format(cal.getTime().getTime());
        strMes = strMesAno.substring(0, 2);
        strAno = strMesAno.substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT categoria, SUM(valor) AS total FROM gastos WHERE YEAR(data) LIKE ? AND MONTH(data) LIKE ? GROUP BY categoria ORDER BY total DESC");
            ps.setInt(1, ano);
            ps.setInt(2, mes);
            ResultSet resultSet = ps.executeQuery();

            List<Gastos> entidades = new ArrayList<>();

            while (resultSet.next()) {
                entidade = new Gastos();

                entidade.setCategoria(resultSet.getString("categoria"));
                entidade.setSomaCategoria(Integer.parseInt(resultSet.getString("total")));

                entidades.add(entidade);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    //-----------------------cartão de credito------------------------
    @Override
    public void cartaoSalvarCartoes(CartaoCredito entidade) throws ErroSistema {

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            if (entidade.getIdCartao() == null) {
                ps = conexao.prepareCall("INSERT INTO `cartao` (`nome`,`bandeira`,`fechamento`,`vencimento`,`numero`) VALUES (?,?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update cartao set nome=?,bandeira=?,fechamento=?,vencimento=?,numero=? where idCartao=?");
                ps.setInt(6, entidade.getIdCartao());
            }
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getBandeira());
            ps.setInt(3, entidade.getFechamentoFatura());
            ps.setInt(4, entidade.getVencimentoFatura());
            ps.setString(5, entidade.getNumero());

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }

    }

    @Override
    public List<CartaoCredito> cartaoBuscarCartoes(CartaoCredito entidade) throws ErroSistema {
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM cartao ");

            ResultSet resultSet = ps.executeQuery();

            List<CartaoCredito> entidades = new ArrayList<>();
            while (resultSet.next()) {
                entidade = new CartaoCredito();

                entidade.setIdCartao(resultSet.getInt("idCartao"));
                entidade.setNome(resultSet.getString("nome"));
                entidade.setBandeira(resultSet.getString("bandeira"));
                entidade.setFechamentoFatura(resultSet.getInt("fechamento"));
                entidade.setVencimentoFatura(resultSet.getInt("vencimento"));
                entidade.setNumero(resultSet.getString("numero"));

                entidades.add(entidade);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    @Override
    public void cartaoDeletarCartao(CartaoCredito entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from cartao where idCartao = ?");
            ps.setInt(1, entidade.getIdCartao());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }
    }

    //===============receitas==============================
    @Override
    public void receitasSalvarReceitas(Receitas receitas) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            int i;
            int mes = receitas.getReceitasMesesRepetir();

            for (i = 0; i < mes; i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(receitas.getReceitasData());
                cal.add(Calendar.MONTH, i);
                if (receitas.getReceitasId() == null) {
                    ps = conexao.prepareCall("INSERT INTO `receitas` (`descReceita`,`valor`,`dataReceita`) VALUES (?,?,?)");
                } else {
                    ps = conexao.prepareStatement("update receitas set descReceita=?,valor=?,dataReceita=?where id=?");
                    ps.setInt(4, receitas.getReceitasId());
                }

                ps.setString(1, receitas.getReceitasDesc());
                ps.setDouble(2, receitas.getReceitasValor());
                ps.setDate(3, new Date(cal.getTime().getTime()));

                ps.execute();
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    @Override
    public List<Receitas> receitasBuscarReceitas(Receitas entidade) throws ErroSistema {
        int ano;
        int mes;
        String strMesAno;
        String strMes;
        String strAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(entidade.getReceitasData());
        cal.add(Calendar.MONTH, controleMesAnoParaBusca);
        Format format = new SimpleDateFormat("MM-yyyy");
        strMesAno = format.format(cal.getTime().getTime());
        strMes = strMesAno.substring(0, 2);
        strAno = strMesAno.substring(3, 7);
        mes = Integer.parseInt(strMes);
        ano = Integer.parseInt(strAno);
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM receitas WHERE YEAR(dataReceita) LIKE ? AND MONTH(dataReceita) LIKE ? ");
            ps.setInt(1, ano);
            ps.setInt(2, mes);
            ResultSet resultSet = ps.executeQuery();

            List<Receitas> entidades = new ArrayList<>();
            while (resultSet.next()) {
                entidade = new Receitas();
                entidade.setReceitasId(resultSet.getInt("id"));
                entidade.setReceitasDesc(resultSet.getString("descReceita"));
                entidade.setReceitasValor(resultSet.getDouble("valor"));
                entidade.setReceitasData(resultSet.getDate("dataReceita"));

                entidades.add(entidade);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    @Override
    public void deletarReceita(Receitas entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from receitas where id = ?");
            ps.setInt(1, entidade.getReceitasId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }
    }

}
