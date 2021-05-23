/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author diego
 */
public class ControladorGraficos implements Initializable{
    
    TiemposDao tiemposDao;
    
    @FXML
    private PieChart chart;
    
    @FXML
    Label media_3x3;
    @FXML
    Label media_4x4;
    @FXML
    Label media_megaminx;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiemposDao = new TiemposDao();
        cargarDatosEnElChart();
        try {
            cargarMedias();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGraficos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarMedias() throws SQLException{
        media_3x3.setText("Media 3x3: " + Math.floor(tiemposDao.calcularMedia3x3() * 100)/100);
        media_4x4.setText("Media 4x4: " + Math.floor(tiemposDao.calcularMedia4x4() * 100)/100);
        media_megaminx.setText("Media Megaminx: " + Math.floor(tiemposDao.calcularMediaMegaminx() * 100)/100);
    }
    
    public void cargarDatosEnElChart(){
        Map<String, Integer> tiemposPorCubo = tiemposDao.contarTiemposPorCubo(ControladorLogin.user);
        ObservableList<PieChart.Data> datosParaElChart = FXCollections.observableArrayList();
        tiemposPorCubo.forEach((nombreCubo, cantidad)->
        {
            PieChart.Data data = new PieChart.Data(nombreCubo, cantidad);
            datosParaElChart.add(data);
        });
        
        chart.setData(datosParaElChart);
    }
    
    public void cargarTiempos(ActionEvent e) throws IOException{
            
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Tiempos.fxml"));

        Scene escena = new Scene(root);
        Image icono = new Image("img/icono.png");
        stage.getIcons().add(icono);
        stage.setScene(escena);
        stage.show(); 
        ( (Node) (e.getSource() ) ).getScene().getWindow().hide();
    }
    
}
