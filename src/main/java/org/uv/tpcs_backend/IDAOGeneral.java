/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tpcs_backend;

import java.util.List;

/**
 *
 * @author 2omar
 */
public interface IDAOGeneral <T, I>{
    public T save(T pojo);
    public T update(T pojo, I clave);
    public boolean delete(I clave);
    public T findById(I id);
    public List<T> findAll();
}
