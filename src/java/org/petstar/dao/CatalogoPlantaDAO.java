/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.petstar.configurations.PoolDataSource;
import org.petstar.dto.CatalogoPlantaDTO;
import org.petstar.dto.ResultInteger;

/**
 *
 * @author Ramiro
 */
public class CatalogoPlantaDAO {
    public ResultInteger validaNombrePlanta(String nombre_planta) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertValidaNombrePlanta ?");
        Object[] params = {nombre_planta};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public void insertCatalogoPlanta(CatalogoPlantaDTO planta) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertCatalogoPlanta ?, ?, ?, ?");
        Object[] params = {planta.getNombre_planta(), planta.getEstado_planta(), planta.getDireccion_planta(), planta.getIp_publica()};
        
        qr.update(sql.toString(), params);
    }
}
