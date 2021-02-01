/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.grafico;

import br.empartec.util.ErroSistema;
import br.empartec.util.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luciano
 */
public class GraficoGastosDao {
    
     public static int somarGastosParaGrafico(int mes, int ano) throws ErroSistema{
         int totalGastos ;
         int gastos = somarGastosDosMeses(mes, ano);
         int gastosCartao = somarGastosCartaoCredito(mes, ano);
         totalGastos = gastos + gastosCartao;
         return totalGastos;
         
    }
     public static int somarGastosDosMeses(int mes, int ano) throws ErroSistema{
            int totalDeReceitas= 0;
         try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastos WHERE MONTH(data) LIKE ? AND YEAR(data) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                
              if(resultSet.getString("total") == null){
              totalDeReceitas = 0;
              }else{
              totalDeReceitas = Integer.parseInt(resultSet.getString("total"));                  
              }  
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
         return totalDeReceitas;
    }
     public static int somarGastosCartaoCredito(int mes, int ano) throws ErroSistema{
            int totalDeReceitas= 0;
         try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(valor) AS total FROM gastoscartao WHERE MONTH(gastoscartao.dataGastoCartao) LIKE ? AND YEAR(gastoscartao.dataGastoCartao) LIKE ?");
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                
              if(resultSet.getString("total") == null){
              totalDeReceitas = 0;
              }else{
              totalDeReceitas = Integer.parseInt(resultSet.getString("total"));                  
              }  
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
         return totalDeReceitas;
    }
   
    
}
