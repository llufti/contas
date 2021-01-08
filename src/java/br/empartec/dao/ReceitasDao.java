/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.dao;

import br.empartec.entidades.ControleDeMesSelecionado;
import br.empartec.entidades.Receitas;
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
public class ReceitasDao {
    
    int controleMesAnoParaBusca = 0;
    
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
                    ps = conexao.prepareCall("INSERT INTO `receitas` (`descReceita`,`valor`,`dataReceita`, `idCliente`) VALUES (?,?,?,?)");
                } else {
                    ps = conexao.prepareStatement("update receitas set descReceita=?,valor=?,dataReceita=? ,idCliente=?  where id=?");
                    ps.setInt(5, receitas.getReceitasId());
                }

                ps.setString(1, receitas.getReceitasDesc());
                ps.setDouble(2, receitas.getReceitasValor());
                ps.setDate(3, new Date(cal.getTime().getTime()));
                ps.setInt(4, receitas.getReceitasIdCliente());

                ps.execute();
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public List<Receitas> receitasBuscarReceitas(Receitas receitas) throws ErroSistema {
       
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM receitas WHERE YEAR(dataReceita) LIKE ? AND MONTH(dataReceita) LIKE ? ");
            ps.setInt(1, ControleDeMesSelecionado.intAno);
            ps.setInt(2, ControleDeMesSelecionado.intMes);
            ResultSet resultSet = ps.executeQuery();

            List<br.empartec.entidades.Receitas> entidades = new ArrayList<>();
            while (resultSet.next()) {
                receitas = new br.empartec.entidades.Receitas();
                receitas.setReceitasId(resultSet.getInt("id"));
                receitas.setReceitasDesc(resultSet.getString("descReceita"));
                receitas.setReceitasValor(resultSet.getDouble("valor"));
                receitas.setReceitasData(resultSet.getDate("dataReceita"));

                entidades.add(receitas);

            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    public void deletarReceita(Receitas receitas) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from receitas where id = ?");
            ps.setInt(1, receitas.getReceitasId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }
    }
    
}
