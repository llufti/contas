/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.dao;

import br.empartec.entidades.ResumoDoMes;
import br.empartec.util.ErroSistema;
import br.empartec.util.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Luciano
 */
public class ResumoDoMesDao {

    public void resumoBuscarTotalDeGastos(ResumoDoMes resumoDoMes, int mes, int ano) throws ErroSistema {

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastos WHERE MONTH(data) LIKE ?  AND YEAR(data) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    resumoDoMes.setDespesas(0);
                } else {
                    resumoDoMes.setDespesas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }
    }

    public void resumoBuscarTotalDeReceitasDoProximoMes(ResumoDoMes resumoDoMes, int mes, int ano) throws ErroSistema {

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM receitas WHERE MONTH(dataReceita) LIKE ?  AND YEAR(dataReceita) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    resumoDoMes.setReceitas(0);
                } else {
                    resumoDoMes.setReceitas(Integer.parseInt(resultSet.getString("total")));
                }
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);
        }

    }

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
}
