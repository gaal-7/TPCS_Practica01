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
//    public Empleado save(Empleado pojo) {
//        Conexion cx = Conexion.getInstance();
//        try {
//            String sql = "INSERT INTO empleados (nombre, direccion, telefono) VALUES('" + pojo.getNombre() + "', '" + pojo.getDireccion() + "', '" + pojo.getTelefono() + "')";
//            boolean res = cx.execute(sql);
//            if(res) {
//                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.INFO, "Empleado guardado");
//                return pojo;
//            } else {
//                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No fue posible insertar el empleado");
//                return null;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }

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
    
  /*  @Override
    public boolean delete(Integer clave) {  
        TransactionDB t = new TransactionDB<Empleado>(pojo){
        @Override
        public boolean execute (Connection con){
        try {
            String sql = "delete from empleado where clave=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, p.getClave());
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
        return pojo;
    }*/
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
    public List <Empleado> findAll(){
        SelectionDB sel=new SelectionDB<Empleado>(p:null){
        @Override
        public List select(Connection con){
            try[
                Statement st= null
                ResultSet reg = null;
                st = con.createStatement();
                reg = st.executeQuery(sql);
                while (reg.next()){
                    Empleado emp= new Empleado();
                    emp.setClave(clave:reg.getInt(columnIndex:1));
                    
                }
                return lstEmpleado;
                
    }
        
    }
    }
   /* @Override
    public Empleado update(Empleado pojo, Integer clave) {
        Conexion con = Conexion.getInstance();
        Empleado empleado = null;
        try {
            String sql = "UPDATE empleados SET " + "nombre = '" + pojo.getNombre() + "', " + "direccion = '" + pojo.getDireccion()+ "', " + "telefono = '" + pojo.getTelefono()+ "' " + "WHERE clave = " + clave;
            boolean res = con.execute(sql);
            if(res) {
                empleado = new Empleado(clave, pojo.getNombre(), pojo.getDireccion(), pojo.getTelefono());
                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.INFO, "Empleado actualizado");
            } else {
                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No se pudo actualizar el empleado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No se pudo actualizar el empleado", ex);
        }
        return empleado; 
    TransactionDB t = new TransactionDB<Empleado>(pojo){
        @Override
        public boolean execute (Connection con){
            try {
            String sql = "update empleado set nombre=?, direccion=?, telefono=? where clave=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            psmt.setString(1, p.getNombre());
            psmt.setString(2, p.getDireccion());
            psmt.setString(3, p.getTelefono());
            psmt.setInt(4, p.getClave());
            psmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        }
    };
    }
*/
   /* @Override
    public boolean delete(Integer clave) {  
        Conexion cx = Conexion.getInstance();
        try {
            String sql = "DELETE FROM empleados WHERE clave = '" + clave + "'";
            boolean res = cx.execute(sql);
            if(res) {
                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.INFO, "Empleado eliminado");
                return true;
            } else {
                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No se pudo eliminar el empleado");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No se pudo eliminar el empleado", ex);
            return false;
        }
        TransactionDB t = new TransactionDB<Empleado>(pojo){
        @Override
        public boolean execute (Connection con){
        try {
            String sql = "delete from empleado where clave=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            psmt.setInt(1, p.getClave());
            psmt.executeUpdate();
            return true
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
                }
        };   
    }
*/
    @Override
    public Empleado findById(Integer id) {
        Conexion cx = Conexion.getInstance();
        ResultSet rs = null;
        Empleado empleado = null;
        try {
            String sql = "SELECT * FROM empleados WHERE clave = '" + id + "'";
            rs = cx.select(sql);
            if (rs.next()) {
                empleado = new Empleado(
                    rs.getInt("clave"), 
                    rs.getString("nombre"), 
                    rs.getString("direccion"), 
                    rs.getString("telefono")
                );
                Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.INFO, "Se ha encontrado el empleado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LEmpleadoDAO.class.getName()).log(Level.SEVERE, "No se pudo encontrar el empleado", ex);
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
        
        return empleado;
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