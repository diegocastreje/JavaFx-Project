package com.diego.temporizador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Login extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent contenedor = new FXMLLoader().load(getClass().getResource("Login.fxml"));
        Scene escena = new Scene(contenedor, 365, 496);
        escena.getStylesheets().addAll(getClass().getResource("estilos.css").toExternalForm());
        Image icono = new Image("img/icono.png");
        primaryStage.getIcons().add(icono);
        primaryStage.setScene(escena);
        primaryStage.show();    
    }   
        public static void main(String[] args){
        launch(args);
    }
    
}
