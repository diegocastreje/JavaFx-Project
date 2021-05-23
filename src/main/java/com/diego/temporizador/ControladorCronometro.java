/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.time.LocalDate;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author diego
 */
public class ControladorCronometro implements Initializable{
    
    double segundos = 0;
    static boolean parar = false;
    static Timeline reloj;
    LocalDate date = LocalDate.now();
    String fecha = date + "";
    TiemposDao tiemposDao;
    
    @FXML
    Label tiempo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiemposDao = new TiemposDao();
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
    
    public void actionPerformed( ActionEvent evt ) {
        Object o = evt.getSource();
        if( o instanceof Button )
        {
            Button btn = (Button)o;
            if( btn.getText().equals("Iniciar") ) iniciar();
            if( btn.getText().equals("Resetear") ) resetear();
            if( btn.getText().equals("Parar")) parar();
            else {
            	
            }
        }
    }
    
    public void iniciar(){
    	segundos = 0;
        	reloj = new Timeline(new KeyFrame(Duration.ZERO, e -> { 
            
            tiempo.setText("" + Math.floor(segundos * 100)/100);
            segundos = segundos + (0.01);

        }),
                new KeyFrame(Duration.seconds(0.01))

        );
        
        	reloj.setCycleCount(Animation.INDEFINITE);
        	reloj.play();
    }
    
    public void parar() {
    	reloj.stop();
    	guardar();
    }
    
    public void resetear(){
        reloj.stop();
        segundos = 0;
        tiempo.setText("0.00");
    }
    
    private void guardar(){
       Tiempo tiempo = new Tiempo();
       tiempo.setCubo(ControladorTiempos.cubo);
       tiempo.setUsuario(ControladorLogin.user);
       tiempo.setFecha(fecha);
       tiempo.setTiempo(Math.floor(segundos * 100)/100);    
       boolean record = tiemposDao.record(tiempo);
       if(record)
           record();
       tiemposDao.guardar(tiempo);    
    }
    
   private void record(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RECORD!");
        alert.setHeaderText(null);
        alert.setContentText("¡Enhorabuena! Sigue esforzandote asi");

        alert.showAndWait();   
   }
    
}
