/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.dao;

import br.empartec.bean.ControleDeContasBean;
import br.empartec.entidades.CartaoCredito;
import br.empartec.entidades.Supermercado;
import br.empartec.entidades.Usuarios;
import br.empartec.util.ErroSistema;
import br.empartec.util.FabricaConexao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Luciano
 */
@Named
@RequestScoped
public class SupermercadoDao implements Serializable {

    @Inject
    private ControleDeContasBean cdcb;

    public void salvarListaSupermercado(Supermercado supermercado, Usuarios usuarios) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            if (supermercado.getIdListaCompra() == null) {
                ps = conexao.prepareCall("INSERT INTO listadecompra (descricaoLista,dataLista,idUsuario) VALUES (?,?,?)");
            } else {
                ps = conexao.prepareStatement("update listadecompra set descricaoLista=?,dataLista=? where idListaCompra =?");
                ps.setInt(3, supermercado.getIdListaCompra());
            }
            ps.setString(1, supermercado.getDescricaoLista());
            ps.setDate(2, new Date(supermercado.getDataLista().getTime()));
            ps.setInt(3, 1);

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public void supermercadoRenomearNomeDaLista(Supermercado supermercado, Usuarios usuarios) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update listadecompra set descricaoLista=? where idListaCompra =?");
            ps.setInt(2, supermercado.getIdListaCompra());

            ps.setString(1, supermercado.getDescricaoLista());

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public void supermercadoSalvarValorItem(Supermercado supermercado, Usuarios usuarios) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("update supermercado set preco=? where idSupermercado=?");
            ps.setDouble(1, supermercado.getPreco());
            ps.setInt(2, supermercado.getIdSupermercado());

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public void supermercadoSalvarTotalItem(Supermercado supermercado, Usuarios usuarios) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("update supermercado set total=? where idSupermercado=?");
            ps.setDouble(1, cdcb.getTotal());
            ps.setInt(2, supermercado.getIdSupermercado());

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public void supermercadoSalvarQuantidadeItem(Supermercado supermercado, Usuarios usuarios) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("update supermercado set quantidade=? where idSupermercado=?");
            ps.setInt(1, supermercado.getQuantidade());
            ps.setInt(2, supermercado.getIdSupermercado());

            ps.execute();

            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir", ex);
        }
    }

    public int supermercadoBuscarQuantidadePeloIdItem(Supermercado supermercado) throws ErroSistema {
        int quantidade = 0;
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT supermercado.quantidade FROM supermercado WHERE supermercado.idSupermercado  LIKE ? ");
            ps.setInt(1, supermercado.getIdSupermercado());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                quantidade = resultSet.getInt("quantidade");
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }

        return quantidade;
    }

    public Double supermercadoBuscarPrecoPeloIdItem(Supermercado supermercado) throws ErroSistema {
        Double preco = null;
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT supermercado.preco FROM supermercado WHERE supermercado.idSupermercado  LIKE ? ");
            ps.setInt(1, supermercado.getIdSupermercado());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                preco = resultSet.getDouble("preco");
            }
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }

        return preco;
    }

    public Double supermercadoBuscarTotalTodosItensListaPeloId(int idLista) throws ErroSistema {

        Double total = null;
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT SUM(total) AS total FROM supermercado WHERE supermercado.idListaCompra LIKE ?  ");
            ps.setInt(1, idLista);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("total") == null) {
                    total = 0.0;
                } else {
                    total = Double.parseDouble(resultSet.getString("total"));
                }
            }

            return total;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    public void salvarItensSupermercado(Supermercado supermercado) throws ErroSistema {
        for (String item : cdcb.getItensSelecionados()) {
            supermercado = new Supermercado();
            supermercado.setItem(item);
            try {
                Connection conexao = FabricaConexao.getConexao();
                PreparedStatement ps;
                ps = conexao.prepareCall("INSERT INTO supermercado (item,idListaCompra) VALUES (?,?)");

                ps.setString(1, supermercado.getItem());
                ps.setInt(2, supermercadoBuscarIdLista());

                ps.execute();

            } catch (SQLException ex) {
                throw new ErroSistema("Existem itens Repitidos" + ex, ex);
            }
            FabricaConexao.fecharConexao();
        }
    }
     public void salvarItensSupermercadoDuranteCompra(Supermercado supermercado) throws ErroSistema {
            try {
                Connection conexao = FabricaConexao.getConexao();
                PreparedStatement ps;
                ps = conexao.prepareCall("INSERT INTO supermercado (item,idListaCompra) VALUES (?,?)");

                ps.setString(1, supermercado.getItem());
                ps.setInt(2, supermercado.getIdListaCompra());

                ps.execute();

            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao inserir", ex);
            }
            FabricaConexao.fecharConexao();
    }

    public List<Supermercado> supermercadoBuscarListas(Supermercado supermercado) throws ErroSistema {
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM listadecompra");

            ResultSet resultSet = ps.executeQuery();

            List<Supermercado> entidades = new ArrayList<>();
            while (resultSet.next()) {
                supermercado = new Supermercado();

                supermercado.setIdListaCompra(resultSet.getInt("idListaCompra"));
                supermercado.setDescricaoLista(resultSet.getString("descricaoLista"));
                supermercado.setDataLista(resultSet.getDate("dataLista"));
                entidades.add(supermercado);
            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    public List<Supermercado> supermercadoBuscarItensPeloIdLista(Supermercado supermercado) throws ErroSistema {
        try {

            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT supermercado.idSupermercado,supermercado.idListaCompra,supermercado.item,supermercado.preco, supermercado.quantidade, listadecompra.descricaoLista, listadecompra.dataLista FROM supermercado JOIN listadecompra ON supermercado.idListaCompra = listadecompra.idListaCompra WHERE supermercado.idListaCompra LIKE ? ORDER BY supermercado.item ASC");
            ps.setInt(1, supermercado.getIdListaCompra());
            ResultSet resultSet = ps.executeQuery();

            List<Supermercado> entidades = new ArrayList<>();
            while (resultSet.next()) {
                supermercado = new Supermercado();

                supermercado.setIdSupermercado(resultSet.getInt("idSupermercado"));
                supermercado.setIdListaCompra(resultSet.getInt("idListaCompra"));
                supermercado.setItem(resultSet.getString("item"));
                supermercado.setQuantidade(resultSet.getInt("quantidade"));
                supermercado.setPreco(resultSet.getDouble("preco"));
                supermercado.setDescricaoLista(resultSet.getString("descricaoLista"));
                supermercado.setDataLista(resultSet.getDate("dataLista"));
                entidades.add(supermercado);
            }
            FabricaConexao.fecharConexao();
            return entidades;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
    }

    public int supermercadoBuscarIdLista() throws ErroSistema {
        int idLista = 0;
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT listadecompra.idListaCompra FROM listadecompra ORDER BY listadecompra.idListaCompra DESC limit 1");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                idLista = resultSet.getInt("idListaCompra");
            }
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao inserir na lista", ex);

        }
        return idLista;
    }

    public void supermercadoDeletarLista(Supermercado supermercado) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from supermercado where idListaCompra   = ?");
            ps.setInt(1, supermercado.getIdListaCompra());

            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }
    }

    public void supermercadoDeletarItensLista(Supermercado supermercado) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from listadecompra where idListaCompra  = ?");
            ps.setInt(1, supermercado.getIdListaCompra());

            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }
    }
    public void supermercadoDeletarItensPeloId(Supermercado supermercado) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from supermercado where idSupermercado = ?");
            ps.setInt(1, supermercado.getIdSupermercado());

            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar", ex);
        }
    }

    public ControleDeContasBean getCdcb() {
        return cdcb;
    }

    public void setCdcb(ControleDeContasBean cdcb) {
        this.cdcb = cdcb;
    }

}
