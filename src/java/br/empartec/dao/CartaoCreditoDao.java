/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.dao;

import br.empartec.entidades.CartaoCredito;
import br.empartec.entidades.ControleDeMesSelecionado;
import br.empartec.entidades.Gastos;
import br.empartec.entidades.SuperClasse;
import br.empartec.entidades.Usuarios;
import br.empartec.util.ErroSistema;
import br.empartec.util.FabricaConexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luciano
 */
public class CartaoCreditoDao {

    public void cartaoSalvarCartoes(CartaoCredito entidade, Usuarios usuarios) throws ErroSistema {

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            if (entidade.getIdCartao() == null) {
                ps = conexao.prepareCall("INSERT INTO `cartao` (`nome`,`bandeira`,`fechamento`,`vencimento`,`numero`,`idCliente`) VALUES (?,?,?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update cartao set nome=?,bandeira=?,fechamento=?,vencimento=?,numero=?,idCliente=? where idCartao=?");
                ps.setInt(7, entidade.getIdCartao());
            }
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getBandeira());
            ps.setInt(3, entidade.getFechamentoFatura());
            ps.setInt(4, entidade.getVencimentoFatura());
            ps.setString(5, entidade.getNumero());
            ps.setInt(6, usuarios.getIdCliente());

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public void cartaoSalvarGastosComCartao(CartaoCredito cartao, Usuarios usuarios, Gastos gastos) throws ErroSistema {

        int fechamento;
        int vencimento;
        int diaAtual;
        fechamento = cartao.getFechamentoFatura();
        vencimento = cartao.getVencimentoFatura();

        int j;
        int h;
        int mesesQueRepetem = gastos.getGastosRepete();

        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            Calendar calCartao = Calendar.getInstance();
            diaAtual = calCartao.get(Calendar.DAY_OF_MONTH);
            calCartao.setTime(cartao.getData());

            if (diaAtual > fechamento && diaAtual < vencimento) { //se o dia da compra estiver dentro do melhor dia de compra
                System.out.println("executou o melhor dia de compra ");
                Calendar calMesAtual = Calendar.getInstance(); // cria uma instancia que pega o mes atual
                calMesAtual.setTime(cartao.getData()); // pega o mes atual
                calMesAtual.add(Calendar.MONTH, 1); // gera um data com o mes que vem
                Date proximoMesMelhorDia = new Date(calMesAtual.getTime().getTime()); // adiciona o mes recem criado em uma variavel
                for (j = 0; j < mesesQueRepetem; j++) {
                    Calendar calMesQueVem = Calendar.getInstance(); //cria mais uma instancia do me que vem 
                    calMesQueVem.setTime(proximoMesMelhorDia); // seta a intancia com o mes que vem 
                    calMesQueVem.add(Calendar.MONTH, j); // acrescenta a quantidade de repetição a partir do proximo mes 

                    if (gastos.getId() == null) {
                        ps = conexao.prepareCall("INSERT INTO `gastosCartao` (`gasto`,`idUsuario`,`idCartao`,`dataGastoCartao`,`valor`,`categoria`) VALUES (?,?,?,?,?,?)");
                    } else {
                        ps = conexao.prepareStatement("update gastos set gasto=?,idUsuario=?,idCartao=?,dataGastoCartao=?,valor=? ,categoria=? where idGastosCartao=?");
                        ps.setInt(6, gastos.getId());
                    }
                    ps.setString(1, gastos.getDescGasto());
                    ps.setInt(2, usuarios.getIdCliente());
                    ps.setInt(3, cartao.getIdCartao());
                    ps.setDate(4, new Date(calMesQueVem.getTime().getTime()));
                    ps.setDouble(5, gastos.getValor());
                    ps.setString(6, "teste");

                    ps.execute();

                }
            } else { // se o cartão não esta no melhor dia de compra 

                for (h = 0; h < mesesQueRepetem; h++) {
                    Calendar calMesVem = Calendar.getInstance();
                    calMesVem.setTime(cartao.getData());
                    calMesVem.add(Calendar.MONTH, h);
                    if (gastos.getId() == null) {
                        ps = conexao.prepareCall("INSERT INTO `gastosCartao` (`gasto`,`idUsuario`,`idCartao`,`dataGastoCartao`,`valor`,`categoria`) VALUES (?,?,?,?,?,?)");
                    } else {
                        ps = conexao.prepareStatement("update gastos set gasto=?, idUsuario=?, idCartao=?, dataGastoCartao=?, valor=? ,categoria=? where idGastosCartao=?");
                        ps.setInt(6, gastos.getId());
                    }
                    ps.setString(1, gastos.getDescGasto());
                    ps.setInt(2, usuarios.getIdCliente());
                    ps.setInt(3, cartao.getIdCartao());
                    ps.setDate(4, new Date(calMesVem.getTime().getTime()));
                    ps.setDouble(5, gastos.getValor());
                    ps.setString(6, "teste");

                    ps.execute();
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        } // fim cath

    } // fim metodo 

    public List<CartaoCredito> cartaoBuscarCartoes(CartaoCredito cartao) throws ErroSistema {
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM cartao ");

            ResultSet resultSet = ps.executeQuery();

            List<CartaoCredito> entidades = new ArrayList<>();
            while (resultSet.next()) {
                cartao = new CartaoCredito();

                cartao.setIdCartao(resultSet.getInt("idCartao"));
                cartao.setNome(resultSet.getString("nome"));
                cartao.setBandeira(resultSet.getString("bandeira"));
                cartao.setFechamentoFatura(resultSet.getInt("fechamento"));
                cartao.setVencimentoFatura(resultSet.getInt("vencimento"));
                cartao.setNumero(resultSet.getString("numero"));

                entidades.add(cartao);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }
    public List<SuperClasse> cartaoBuscarGastosCartoes(CartaoCredito cartao, Gastos gastos, SuperClasse superClasse, Usuarios usuarios) throws ErroSistema {
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT cartao.nome, cartao.bandeira, cartao.numero, gastoscartao.idGastosCartao, gastoscartao.gasto, gastoscartao.idCartao, gastoscartao.dataGastoCartao, gastoscartao.valor, gastoscartao.categoria FROM gastoscartao JOIN cartao ON gastoscartao.idCartao = cartao.idCartao WHERE gastoscartao.idCartao LIKE ? AND YEAR(gastoscartao.dataGastoCartao) LIKE ? AND MONTH(gastoscartao.dataGastoCartao) LIKE ?  AND gastoscartao.idUsuario = 1");
            ps.setInt(1, cartao.getIdCartao());
            ps.setInt(2,ControleDeMesSelecionado.intAno);
            ps.setInt(3, ControleDeMesSelecionado.intMes);
            ResultSet resultSet = ps.executeQuery();

            List<SuperClasse> entidades = new ArrayList<>();
            while (resultSet.next()) {
                superClasse = new SuperClasse();

                superClasse.setNomeUsuarioDoCartao(resultSet.getString("nome"));
                superClasse.setBandeira(resultSet.getString("bandeira"));
                superClasse.setNumero(resultSet.getString("numero"));
                superClasse.setIdGastoaCartao(resultSet.getInt("idGastosCartao"));
                superClasse.setDescricaoGasto(resultSet.getString("gasto"));
                superClasse.setIdCartao(resultSet.getInt("idCartao"));
                superClasse.setDataDoGasto(resultSet.getDate("dataGastoCartao"));
                superClasse.setValor(resultSet.getInt("valor"));
                superClasse.setCategoria(resultSet.getString("categoria"));

                entidades.add(superClasse);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    public List<CartaoCredito> cartaoBuscarCatoesCadastrados(CartaoCredito cartao, Usuarios usuarios) throws ErroSistema {
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT idCartao FROM `gastoscartao` WHERE idUsuario GROUP BY idCartao");

            ResultSet resultSet = ps.executeQuery();

            List<CartaoCredito> entidades = new ArrayList<>();
            while (resultSet.next()) {
                cartao = new CartaoCredito();

                cartao.setIdCartao(resultSet.getInt("idCartao"));
                entidades.add(cartao);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

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

}
