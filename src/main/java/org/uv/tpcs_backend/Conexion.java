/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tpcs_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2omar
 */
public class Conexion {
    private Connection con = null;
    private static Conexion cx = null;
    
    private final String bd = "empleado";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String password = System.getProperty("database.password");
    
    private Conexion() {
        try {
            con = DriverManager.getConnection(url + bd, user, password);
            Logger.getLogger(Conexion.class.getName()).log(Level.INFO, "Se ha conectado la base de datos {0}.", bd);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se ha conectado a la base de datos {0}.", bd);
        }
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public static synchronized Conexion getInstance() {
        if (cx == null) {
            cx = new Conexion();
        }
        return cx;
    }
    
    public boolean execute(String sql) throws SQLException {
        Statement st = null;
        try {
            st = con.createStatement();
            Logger.getLogger(Conexion.class.getName()).log(Level.INFO, sql);
            st.execute(sql);
            return true;
        } catch(SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se pudo realizar la accion", e);
            return false;
        } 
        finally {
            if(st != null) {
                try {
                    st.close();
                } catch(SQLException e) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se pudo cerrar la conexion", e);
                }
            }
        }
    }
    
    public boolean execute(TransactionDB t){
        return t.execute(con);
    }
    
    public List select(SelectionDB sel){
        return sel.select(con);
    }
    
    public ResultSet select(String sql){
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            return rs;
        } catch(SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se pudo realizar la accion", e);
            return null;
        }
        finally {
            /*
            if(st != null) {
                try {
                    st.close();
                } catch(SQLException e) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se pudo cerrar la conexion", e);
                }
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se pudo cerrar la conexion", e);
                }
            }
            */
        }
    }
    
    public boolean isConnected() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
