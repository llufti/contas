/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.empartec.grafico;

import br.empartec.entidades.ControleDeMesSelecionado;
import br.empartec.util.ErroSistema;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

@Named
@ViewScoped
public class ChartView implements Serializable {

    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    
   

   

    String mesAnoUm = "";
    String mesAnoDois = "";
    String mesAnoTres = "";
    String mesAnoQuatro = "";
    String mesAnoCinco = "";

    @PostConstruct
    public void init() {
        try {
            createBarModels();
        } catch (ErroSistema ex) {
            Logger.getLogger(ChartView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    private BarChartModel initBarModel() throws ErroSistema {

        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();

        boys.setLabel("Receitas");

        boys.set(definirMesAnoBarrasDoGrafico(-2), GraficoReceitasDao.somarReceitasDosMeses(definirMesCalculoBarraGrafico(-2), definirAnoCalculoBarraGrafico(-2)));
        boys.set(definirMesAnoBarrasDoGrafico(-1), GraficoReceitasDao.somarReceitasDosMeses(definirMesCalculoBarraGrafico(-1), definirAnoCalculoBarraGrafico(-1)));
        boys.set("Mes Atual", GraficoReceitasDao.somarReceitasDosMeses(definirMesCalculoBarraGrafico(0), definirAnoCalculoBarraGrafico(0)));
        boys.set(definirMesAnoBarrasDoGrafico(1), GraficoReceitasDao.somarReceitasDosMeses(definirMesCalculoBarraGrafico(1), definirAnoCalculoBarraGrafico(1)));
        boys.set(definirMesAnoBarrasDoGrafico(2), GraficoReceitasDao.somarReceitasDosMeses(definirMesCalculoBarraGrafico(2), definirAnoCalculoBarraGrafico(2)));

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Gastos");
        girls.set(definirMesAnoBarrasDoGrafico(-2), GraficoGastosDao.somarGastosParaGrafico(definirMesCalculoBarraGrafico(-2), definirAnoCalculoBarraGrafico(-2)));
        girls.set(definirMesAnoBarrasDoGrafico(-1), GraficoGastosDao.somarGastosParaGrafico(definirMesCalculoBarraGrafico(-1), definirAnoCalculoBarraGrafico(-1)));
        girls.set("Mes Atual", GraficoGastosDao.somarGastosParaGrafico(definirMesCalculoBarraGrafico(0), definirAnoCalculoBarraGrafico(0)));
        girls.set(definirMesAnoBarrasDoGrafico(1), GraficoGastosDao.somarGastosParaGrafico(definirMesCalculoBarraGrafico(1), definirAnoCalculoBarraGrafico(1)));
        girls.set(definirMesAnoBarrasDoGrafico(2), GraficoGastosDao.somarGastosParaGrafico(definirMesCalculoBarraGrafico(2), definirAnoCalculoBarraGrafico(2)));
        model.addSeries(boys);
        model.addSeries(girls);

        return model;
    }

    public void createBarModels() throws ErroSistema {
        createBarModel();

    }

    void createBarModel() throws ErroSistema {
        barModel = initBarModel();

        barModel.setTitle("Resumo de Gastos");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Mês");

        Axis yAxis = barModel.getAxis(AxisType.Y);

        yAxis.setMin(250);
        yAxis.setMax(3500.00);
        yAxis.setTickCount(5);
    }

    public String definirMesAnoBarrasDoGrafico(int controle) {
        Date mesAnoTual = new Date();
        String mesAno;

        Calendar cal = Calendar.getInstance();
        cal.setTime(mesAnoTual);
        cal.add(Calendar.MONTH, controle);
        Format format = new SimpleDateFormat("MMM/yy");
        mesAno = format.format(cal.getTime().getTime());

        return mesAno;

    }

    public int definirMesCalculoBarraGrafico(int controle) {
        int mesParaBarraDoGrafico;
        Date mesAnoTual = new Date();
        String strMes;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(mesAnoTual);
        cal.add(Calendar.MONTH, controle);
        Format format = new SimpleDateFormat("MM-yyyy");
        strMes = format.format(cal.getTime().getTime());
        strMes = strMes.substring(0, 2);
        mesParaBarraDoGrafico = Integer.parseInt(strMes);
        
        return  mesParaBarraDoGrafico;
    }
    public int definirAnoCalculoBarraGrafico(int controle) {
         int anoParaBarraDoGrafico;
        Date mesAnoTual = new Date();
        String strAno;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(mesAnoTual);
        cal.add(Calendar.MONTH, controle);
        Format format = new SimpleDateFormat("MM-yyyy");
        strAno = format.format(cal.getTime().getTime());
        strAno = strAno.substring(3, 7);
        anoParaBarraDoGrafico = Integer.parseInt(strAno);
        
        return  anoParaBarraDoGrafico;
    }

}
