/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tpcs_backend;

import java.util.List;
import java.sql.Connection;
/**
 *
 * @author 2omar
 */
public abstract class SelectionDB<T> {
    protected T p;
    
    public SelectionDB(T p) {
        this.p = p;
    }
    public abstract List<T> select(Connection con);    
}
