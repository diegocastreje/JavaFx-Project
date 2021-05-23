/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.io.IOException;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author diego
 */
public class ControladorLogin implements Initializable{
    
    public static String user;
    static UsuariosDao usuariosDao;
    
    @FXML
    TextField usuario;
    
    @FXML
    PasswordField contrasenha;
        
    public void entrar(ActionEvent e) throws IOException, ClassNotFoundException, SQLException{
        user = usuario.getText().trim();
        boolean comprobacion = UsuariosDao.comprobar(user, contrasenha.getText().trim());
        if(comprobacion)
            cargarTiempos(e);
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acceso denegado");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña erróneos");

            alert.showAndWait();             
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuariosDao = new UsuariosDao();
    }
    
    public void nuevo(ActionEvent e) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Registro.fxml"));

        Scene escena = new Scene(root);
        Image icono = new Image("img/icono.png");
        stage.getIcons().add(icono);
        stage.setScene(escena);
        stage.show(); 
        ( (Node) (e.getSource() ) ).getScene().getWindow().hide();
    }
    
    private void cargarTiempos(ActionEvent e) throws IOException{
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
