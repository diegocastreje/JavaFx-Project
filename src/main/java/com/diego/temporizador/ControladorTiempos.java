/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author diego
 */
public class ControladorTiempos implements Initializable{
    
    @FXML
    TableView<Tiempo> tablaTiempos;
    
    @FXML 
    RadioButton cubo_3x3;
    
    @FXML 
    RadioButton cubo_4x4;
    
    @FXML
    RadioButton megaminx;
    
    TiemposDao tiemposDao;
    public static String cubo = "3x3";
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiemposDao = new TiemposDao();
        cubo_3x3.setSelected(true);
        cargarTiemposDeLaBase();
        configurarTamanhoColumnas();
    }
    
    public void eliminar(){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
           alerta.setTitle("Eliminar Tiempo");
           alerta.setHeaderText("Se requiere confirmación");
           alerta.setContentText("¿Seguro que desea eliminar este tiempo?");
           Optional<ButtonType> resultado = alerta.showAndWait();
        if(resultado.get() == ButtonType.OK){     
            Tiempo tiempo = tablaTiempos.getSelectionModel().getSelectedItem();
            TiemposDao.eliminar(tiempo);
            cargarTiemposDeLaBase();
           }

    }   
    
    public void cargarGraficos(ActionEvent e) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Graficos.fxml"));

        Scene escena = new Scene(root);
        Image icono = new Image("img/icono.png");
        stage.getIcons().add(icono);
        stage.setScene(escena);
        stage.show();
        ( (Node) (e.getSource() ) ).getScene().getWindow().hide();   
    }
    public void cargarLogin(ActionEvent e) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene escena = new Scene(root);
        Image icono = new Image("img/icono.png");
        stage.getIcons().add(icono);
        stage.setScene(escena);
        stage.show();
        ( (Node) (e.getSource() ) ).getScene().getWindow().hide();   
    }

    public void cargarCronometro(ActionEvent e) throws IOException {
        
        if(cubo_3x3.isSelected())
            cubo = "3x3";
        else if(cubo_4x4.isSelected())
            cubo = "4x4";
        else if(megaminx.isSelected())
            cubo = "Megaminx";
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Cronometro.fxml"));

        Scene escena = new Scene(root);
        Image icono = new Image("img/icono.png");
        stage.getIcons().add(icono);
        stage.setScene(escena);
        stage.show();
        ( (Node) (e.getSource() ) ).getScene().getWindow().hide();   
    }
    
    private void cargarTiemposDeLaBase() {
        ObservableList<Tiempo> tiempos = FXCollections.observableArrayList();
        List<Tiempo> tiemposEncontrados = TiemposDao.buscarTodos(ControladorLogin.user);
        tiempos.addAll(tiemposEncontrados);
        tablaTiempos.setItems(tiempos);
    }
    
    private void configurarTamanhoColumnas(){
        tablaTiempos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Tiempo, ?>> columnas = tablaTiempos.getColumns();
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 20); 
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 20);
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 25);
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 35); 
    }    
}
