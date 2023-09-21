/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tpcs_backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2omar
 */
public class EmpleadoDAO implements IDAOGeneral<Empleado, Integer>{
    @Override
    public Empleado save(Empleado pojo) {
        TransactionDB t = new TransactionDB<Empleado>(pojo){
            @Override
            public boolean execute(Connection con){
                try {
                    String sql = "insert into empleado (clave, nombre, direccion, telefono) values" + "(?,?,?,?)";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setInt(1, p.getClave());
                    pstm.setString(2, p.getNombre());
                    pstm.setString(3, p.getDireccion());
                    pstm.setString(4, p.getTelefono());
                    pstm.execute();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        Conexion con = Conexion.getInstance();
        boolean res = con.execute(t);
        return pojo;
    }
    
    @Override
    public Empleado update(Empleado pojo, Integer clave) {
    TransactionDB t = new TransactionDB<Empleado>(pojo){
        @Override
        public boolean execute (Connection con){
            try {
            String sql = "update empleado set nombre=?, direccion=?, telefono=? where clave=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, p.getNombre());
            pstm.setString(2, p.getDireccion());
            pstm.setString(3, p.getTelefono());
            pstm.setInt(4, p.getClave());
            pstm.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        }
    };
    Conexion con = Conexion.getInstance();
        boolean res = con.execute(t);
        return pojo;
    }
    

    @Override
public boolean delete(Integer clave) {  
    TransactionDB t = new TransactionDB<Empleado>(null) { // Pasamos null porque no necesitamos el pojo aquí
        @Override
        public boolean execute(Connection con) {
            try {
                String sql = "delete from empleado where clave=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setInt(1, clave); // Usamos el parámetro clave en lugar del pojo
                pstm.executeUpdate();
                return true;
            } catch (Exception ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    };
    
    Conexion con = Conexion.getInstance();
    boolean res = con.execute(t);
    return res; // Devolvemos el resultado de la transacción en lugar del pojo
}     
        @Override
    public List<Empleado> findAll() {
        SelectionDB sel = new SelectionDB(null) {
            @Override
            public List select(Connection conexion) {
                
                List<Empleado> listaEmpleados = new ArrayList<>();
                
                String consulta = "SELECT * FROM empleados";
                try (PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {

                        Empleado empleado = new Empleado();
                        empleado.setClave(Integer.parseInt(resultSet.getString("clave")));
                        empleado.setNombre(resultSet.getString("nombre"));
                        empleado.setDireccion(resultSet.getString("direccion"));
                        empleado.setTelefono(resultSet.getString("telefono"));

                        listaEmpleados.add(empleado);
                    }
                    
                    return listaEmpleados;
                    
                } catch (SQLException e) {
                     Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, "Error al buscar empleados", e);
                } finally {
                    try {
                        conexion.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
                    }
                }
                
                return listaEmpleados;
            }
            
        };
        
        Conexion con = Conexion.getInstance();
        List<Empleado> listaEmpleados = con.select(sel);
        return listaEmpleados;
    }
     
     @Override
    public Empleado findById(Integer clave) {

        SelectionDB sel = new SelectionDB(clave) {
            @Override
            public List select(Connection conexion) {
                
                List<Empleado> listaEmpleados = new ArrayList<>();
               
                String consulta = "SELECT * FROM empleados WHERE clave = '" + clave + "'";

                try (PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
                    preparedStatement.setInt(1, clave);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        Empleado empleado = new Empleado();
                        empleado.setClave(clave);
                        empleado.setNombre(resultSet.getString("nombre"));
                        empleado.setDireccion(resultSet.getString("direccion"));
                        empleado.setTelefono(resultSet.getString("telefono"));
                        
                        listaEmpleados.add(empleado);
                    }
                } catch (SQLException e) {
                     Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "Error al buscar", e);
                } finally {
                    try {
                        conexion.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                return listaEmpleados;
            }
            
        };     
        Conexion con = Conexion.getInstance();
        List<Empleado> listaEmpleados = con.select(sel);
        return listaEmpleados.get(0);
    }
    

  /*  @Override
    public List<Empleado> findAll() {
        Conexion cx = Conexion.getInstance();
        ResultSet rs = null;
        List<Empleado> empleados = new ArrayList<>();
        try {
            String sql = "SELECT * FROM empleados";
            rs = cx.select(sql);
            while(rs.next()) {
                Empleado empleado = new Empleado(
                    rs.getInt("clave"), 
                    rs.getString("nombre"), 
                    rs.getString("direccion"), 
                    rs.getString("telefono")
                );
                empleados.add(empleado);
            }
            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.INFO, "Se ha obtenido toda la lista de empleados registrados");
        } catch (SQLException ex) {
            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No se pudo obtener toda la lista de empleados registrados", ex);
        }
        finally {
            try {
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return empleados;
    }
    */
}