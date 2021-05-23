/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author diego
 */
public class ControladorRegistro implements Initializable{
    
    UsuariosDao usuariosDao;

    @FXML
    TextField usuario;
    
    @FXML
    PasswordField contrasenha;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void registrar(ActionEvent e) throws IOException{
        UsuariosDao.insert(usuario.getText().trim(), contrasenha.getText().trim());
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene escena = new Scene(root);
        Image icono = new Image("img/icono.png");
        stage.getIcons().add(icono);
        stage.setScene(escena);
        stage.show(); 
        ( (Node) (e.getSource() ) ).getScene().getWindow().hide();
    }
    
}
