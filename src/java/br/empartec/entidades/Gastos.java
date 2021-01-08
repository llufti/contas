package br.empartec.entidades;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Luciano
 */
public class Gastos implements Serializable {

    
    private Integer id;
    private Integer idCliente = 1;
    private Integer somaCategoria;
    private String categoria;
    private String descGasto;
    private Integer gastosRepete = 1;
    private Double valor;
    private Date data = new Date();
    private String proximoMes = "nao";
    private String musicas = "Deus";

    public String getMusicas() {
        return musicas;
    }

    public void setMusicas(String musicas) {
        this.musicas = musicas;
    }
    
   
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescGasto() {
        return descGasto;
    }

    public void setDescGasto(String descGasto) {
        this.descGasto = descGasto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGastosRepete() {
        return gastosRepete;
    }

    public void setGastosRepete(Integer gastosRepete) {
        this.gastosRepete = gastosRepete;
    }

    public String getProximoMes() {
        return proximoMes;
    }

    public void setProximoMes(String proximoMes) {
        this.proximoMes = proximoMes;
    }

    public Integer getSomaCategoria() {
        return somaCategoria;
    }

    public void setSomaCategoria(Integer somaCategoria) {
        this.somaCategoria = somaCategoria;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

  
    
}
