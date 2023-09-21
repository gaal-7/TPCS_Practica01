/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tpcs_backend;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 2omar
 */
public class LEmpleadoDAO {
    private Empleado empleado = null;
    private EmpleadoDAO dao = null;
    
    public LEmpleadoDAO() {
        empleado = new Empleado();
        dao = new EmpleadoDAO();
    }
    
    public LEmpleadoDAO(String nombre, String direccion, String telefono) {
        empleado = new Empleado(nombre, direccion, telefono);
        dao = new EmpleadoDAO();
    }
    
    public LEmpleadoDAO(int clave, String nombre, String direccion, String telefono) {
        empleado = new Empleado(clave, nombre, direccion, telefono);
        dao = new EmpleadoDAO();
    }
    
    @SuppressWarnings("empty-statement")
    public DefaultTableModel constructTable(List<Empleado> empleados) {
        String[] columnas = {"Clave", "Nombre", "Direccion", "Telefono"}; 
        
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (Empleado emp : empleados) {
            String clv = emp.getClave() + "";
            String nbr = emp.getNombre();
            String dir = emp.getDireccion();
            String tel = emp.getTelefono();
            String[] fila = {clv, nbr, dir, tel};
            model.addRow(fila);
        }
        return model;
    }
    
    public DefaultTableModel constructTable(Empleado empleado) {
        String[] columnas = {"Clave", "Nombre", "Direccion", "Telefono"}; 
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        Object[] fila = {empleado.getClave(), empleado.getNombre(), empleado.getDireccion(), empleado.getTelefono()};
        model.addRow(fila);
        return model;
    }
    
    public String[] getInfoEmpleado(int clave) {
        Empleado emp = fingById(clave);
        String clv = emp.getClave() + "";
        String nbr = emp.getNombre();
        String dir = emp.getDireccion();
        String tel = emp.getTelefono();
        String[] info = {clv, nbr, dir, tel};
        return info;
    }
    
    public Empleado save() {
        return dao.save(empleado);
    }
    
    public Empleado update(int clave) {
        return dao.update(empleado, clave);
    }
    
    public boolean delete(int clave) {
        return dao.delete(clave);
    }
    
    public Empleado fingById(int clave) {
        return dao.findById(clave);
    }
    
    public List<Empleado> findAll() {
        return dao.findAll();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EmpleadoDAO getDao() {
        return dao;
    }

    public void setDao(EmpleadoDAO dao) {
        this.dao = dao;
    }
}
