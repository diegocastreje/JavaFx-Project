/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class UsuariosDao {

    public static final String URL_CONEXION = "jdbc:mysql://localhost:3306/temporizador?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Madrid";
    public static final String USUARIO_BD = "root";
    public static final String PASSWORD_BD = "root";

    public UsuariosDao() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios" +
                    "(id INTEGER PRIMARY KEY auto_increment, " +
                    "usuario VARCHAR(255), " +
                    "contrasenha VARCHAR(255)) ";
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<String> encontrarUsuario(){
        ArrayList<String> usuarios = new ArrayList<>();
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT usuario FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                System.out.println(resultSet.getString("usuario"));
                usuarios.add(resultSet.getString("usuario"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return usuarios;
    }
    
    public static boolean comprobar(String user, String contrasenha) throws ClassNotFoundException, SQLException {
		boolean comprobacion = false;

		try (Connection connection = DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD );
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT usuario,contrasenha FROM usuarios WHERE usuario = ? AND contrasenha = ?")) {
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, contrasenha);
			ResultSet rs = preparedStatement.executeQuery();
			comprobacion = rs.next();

		} catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("SQL Error");}
		return comprobacion;
	}    
    
    public static String comprobarContrasenha(String user){
        String contrasenha = null;
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT contrasenha FROM usuarios WHERE usuario = '" + user + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                contrasenha = resultSet.getString("contrasenha");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }     
        return contrasenha;
    }
    
    public static void insert(String user, String password){
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "INSERT INTO usuarios(usuario, contrasenha) " +
            "VALUES ('" + user + "', '" + password + "')";
            statement.executeUpdate(sql);
            System.out.println("INSERT INTO usuarios(usuario, contrasenha) " +
            "VALUES ('" + user + "', '" + password + "')");
        } catch (Exception ex) {
            throw new RuntimeException("Ocurrio un error al guardar la informacion: " + ex.getMessage());
        }        
    }
}
