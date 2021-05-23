/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.temporizador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author diego
 */
public class TiemposDao {
    
    public static final String URL_CONEXION = "jdbc:mysql://localhost:3306/temporizador?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Madrid";
    public static final String USUARIO_BD = "root";
    public static final String PASSWORD_BD = "root";
    
    public TiemposDao() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS tiempos" +
                    "(id INTEGER PRIMARY KEY auto_increment, " +
                    "fecha VARCHAR(255), " +
                    "usuario VARCHAR(255), " +
                    "cubo VARCHAR(255), " +
                    "tiempo DOUBLE) "; 
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }       
    
    public double calcularMedia3x3() throws SQLException{
        double media = 0;
        try(Connection conexionDataBase =
                DriverManager.getConnection(URL_CONEXION,USUARIO_BD,PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT AVG(tiempo) AS media FROM tiempos WHERE cubo='3x3'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                media = resultSet.getDouble("media");
                
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al consultar la información: "
            + e.getMessage());
        }
        return media;
    }
    public double calcularMedia4x4() throws SQLException{
        double media = 0;
        try(Connection conexionDataBase =
                DriverManager.getConnection(URL_CONEXION,USUARIO_BD,PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT AVG(tiempo) AS media FROM tiempos WHERE cubo='4x4'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                media = resultSet.getDouble("media");
                
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al consultar la información: "
            + e.getMessage());
        }
        return media;
    }
    public double calcularMediaMegaminx() throws SQLException{
        double media = 0;
        try(Connection conexionDataBase =
                DriverManager.getConnection(URL_CONEXION,USUARIO_BD,PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT AVG(tiempo) AS media FROM tiempos WHERE cubo='Megaminx'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                media = resultSet.getDouble("media");
                
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al consultar la información: "
            + e.getMessage());
        }
        return media;
    }

    public boolean record(Tiempo t){
        boolean record = false;
        try(Connection conexionDataBase =
                DriverManager.getConnection(URL_CONEXION,USUARIO_BD,PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT MIN(tiempo) AS record FROM tiempos WHERE cubo='" + t.getCubo() + "' AND usuario='" + t.getUsuario() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                if(resultSet.getDouble("record") == 0){
                    record = true;
                }else{
                    if(t.getTiempo() < resultSet.getDouble("record"))
                        record = true;
                }                    
            }        
                   
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al consultar la información: "
            + e.getMessage());
        }        
        return record;
    }
    
    public Map<String, Integer> contarTiemposPorCubo(String usuario){
        List<Tiempo> tiempos = buscarTodos(usuario);
        Map<String, Integer> tiemposPorCubo = new HashMap<>();
        
        try(Connection conexionDataBase =
                DriverManager.getConnection(URL_CONEXION,USUARIO_BD,PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT cubo, COUNT(*) AS cantidad FROM tiempos GROUP BY cubo";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String cubo = resultSet.getString("cubo");
                int cantidadTiempos = resultSet.getInt("cantidad");
                
                for(Tiempo tiempo: tiempos){
                    if(tiempo.getCubo().equals(cubo)){
                        tiemposPorCubo.put(tiempo.getCubo(), cantidadTiempos);
                        break;
                    }
                }
                
            }
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al consultar la información: "
            + e.getMessage());
        }
        return tiemposPorCubo;
    }
    
    public void guardar(Tiempo tiempo) {
        
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "INSERT INTO tiempos(fecha, usuario, cubo, tiempo) " +
            "VALUES ('" + tiempo.getFecha() + "', '" + tiempo.getUsuario() + 
                    "','" + tiempo.getCubo() + "', " + tiempo.getTiempo() + ")";
            statement.executeUpdate(sql);
            System.out.println("Hizo el insert");
            System.out.println("INSERT INTO tiempos(fecha, usuario, cubo, tiempo) " +
            "VALUES ('" + tiempo.getFecha() + "', '" + tiempo.getUsuario() + 
                    "','" + tiempo.getCubo() + "', " + tiempo.getTiempo() + ")");
        } catch (Exception ex) {
            throw new RuntimeException("Ocurrio un error al guardar la informacion: " + ex.getMessage());
        }
        
    }
    
    public static List<Tiempo> buscarTodos(String usuario){
        List<Tiempo> tiempos = new ArrayList<>();
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT * FROM tiempos WHERE usuario = '" + usuario + "' ORDER BY id";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Tiempo tiempo = new Tiempo();
                tiempo.setId(resultSet.getInt("id"));
                tiempo.setFecha(resultSet.getString("fecha"));
                tiempo.setCubo(resultSet.getString("cubo"));                
                tiempo.setUsuario(resultSet.getString("usuario"));
                tiempo.setTiempo(resultSet.getDouble("tiempo"));
                tiempos.add(tiempo);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Ocurrio un error al consultar la informacion: " + ex.getMessage());
        }
        return tiempos;
    }

    public static void eliminar(Tiempo tiempo){
        
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BD, PASSWORD_BD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "DELETE FROM tiempos WHERE id =" + tiempo.getId();
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            throw new RuntimeException("Ocurrio un error al eliminar la informacion: " + ex.getMessage());
        }
        
    }    
        
}
