/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.entidades;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Luciano
 */
public class ControleDeMesSelecionado {
    Date mesAnoTual = new Date();
    public static Date dateMesAnoSelecionado ;
    String strMesAno;
    String strMes;
    String strAno;
    private String mesAnoSelecionado;
    int controle = 0;
    public static int intMes;
    public static int intAno;

    public void definirDataEscolhida() { // define a data escolhida pelo usuario inicia na data atual 

        Calendar cal = Calendar.getInstance();
        cal.setTime(mesAnoTual);
        cal.add(Calendar.MONTH, controle);
        Format format = new SimpleDateFormat("MM-yyyy");
        dateMesAnoSelecionado = new Date(cal.getTime().getTime());
        strMesAno = format.format(cal.getTime().getTime());
        setMesAnoSelecionado(strMesAno);
        strMes = strMesAno.substring(0, 2);
        strAno = strMesAno.substring(3, 7);
        intMes = Integer.parseInt(strMes);
        intAno = Integer.parseInt(strAno);        

    }

    public void selecionarProximoMêsAno() {
        controle++;
        definirDataEscolhida();

    }

    public void selecionarMesAnterior() {
        controle--;
        definirDataEscolhida();
    }

   
    public String getMesAnoSelecionado() {
        return mesAnoSelecionado;
    }

    public void setMesAnoSelecionado(String mesAnoSelecionado) {
        this.mesAnoSelecionado = mesAnoSelecionado;
    }

  

}
