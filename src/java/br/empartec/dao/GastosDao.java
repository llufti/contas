/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.dao;

import br.empartec.entidades.ControleDeMesSelecionado;
import br.empartec.entidades.Gastos;
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
public class GastosDao {

    public void salvarGastos(Gastos gastos) throws ErroSistema {
        int f;
        int j;
        int h;
        int fechamento;
        int diaAtual;
        int vencimento = 0;
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            int mesGastosRepetem = gastos.getGastosRepete();
            if (gastos.getProximoMes().equals("nao")) {  // se gasto for para este mes 
                int i;

                for (i = 0; i < mesGastosRepetem; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(ControleDeMesSelecionado.dateMesAnoSelecionado);
                    cal.add(Calendar.MONTH, i);
                    if (gastos.getId() == null) {
                        ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`,`idCliente`) VALUES (?,?,?,?,?)");
                    } else {
                        ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=?,idCliente=? where id=?");
                        ps.setInt(6, gastos.getId());
                    }
                    ps.setString(1, gastos.getCategoria());
                    ps.setString(2, gastos.getDescGasto());
                    ps.setDouble(3, gastos.getValor());
                    ps.setDate(4, new Date(cal.getTime().getTime()));
                    ps.setInt(5, gastos.getIdCliente());

                    ps.execute();

                }
            } // final if gasto deste mes mes
            else {     // se gasto for para o proximo mes
                

                for (j = 1; j < mesGastosRepetem+1; j++) {
                    Calendar calMesProximo = Calendar.getInstance();
                    calMesProximo.setTime(ControleDeMesSelecionado.dateMesAnoSelecionado);
                    calMesProximo.add(Calendar.MONTH, j);
                    if (gastos.getId() == null) {
                        ps = conexao.prepareCall("INSERT INTO `gastos` (`categoria`,`descGasto`,`valor`,`data`,`idCliente`) VALUES (?,?,?,?,?)");
                    } else {
                        ps = conexao.prepareStatement("update gastos set categoria=?,descGasto=?,valor=?,data=?,idCliente=? where id=?");
                        ps.setInt(6, gastos.getId());
                    }
                    ps.setString(1, gastos.getCategoria());
                    ps.setString(2, gastos.getDescGasto());
                    ps.setDouble(3, gastos.getValor());
                    ps.setDate(4, new Date(calMesProximo.getTime().getTime()));
                    ps.setInt(5, gastos.getIdCliente());

                    ps.execute();
                }
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        } // final cath

    } // final metodo

    public void gastosAdicionarGastosPorCategoria(Gastos entidade, String categoria) throws ErroSistema {
        entidade.setCategoria(categoria);
    }

    public List<Gastos> gastosBuscarTodosGastos(Gastos entidade, Usuarios usuarios) throws ErroSistema {
        int totalGastos;
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM gastos WHERE YEAR(data) LIKE ? AND MONTH(data) LIKE ? AND idCliente LIKE ?");
            ps.setInt(1, ControleDeMesSelecionado.intAno);
            ps.setInt(2, ControleDeMesSelecionado.intMes);
            ps.setInt(3, usuarios.getIdCliente());
            ResultSet resultSet = ps.executeQuery();

            List<Gastos> entidades = new ArrayList<>();
            while (resultSet.next()) {
                entidade = new Gastos();
                entidade.setId(resultSet.getInt("id"));
                entidade.setCategoria(resultSet.getString("categoria"));
                entidade.setDescGasto(resultSet.getString("descGasto"));
                entidade.setValor(resultSet.getDouble("valor"));
                entidade.setData(resultSet.getDate("data"));
                
                totalGastos = gastosBuscarTotalGastos();
                entidade.setTotalGastos(totalGastos);
                entidades.add(entidade);
            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

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
    public int gastosBuscarTotalGastos() throws ErroSistema {

        int total = 0;
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastos WHERE YEAR(gastos.data) LIKE ? AND MONTH(gastos.data)  LIKE ? AND gastos.idCliente LIKE ? ");
            ps.setInt(1, ControleDeMesSelecionado.intAno);
            ps.setInt(2, ControleDeMesSelecionado.intMes);
            ps.setInt(3, 1);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    total = 0;
                } else {
                    total = Integer.parseInt(resultSet.getString("total"));
                }
            }

            return total;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

}//Final da classe
