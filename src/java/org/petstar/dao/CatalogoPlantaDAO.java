/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.petstar.configurations.PoolDataSource;
import org.petstar.dto.CatalogoPlantaDTO;
import org.petstar.dto.ResultInteger;

/**
 *
 * @author Ramiro
 */
public class CatalogoPlantaDAO {
    
    //Metodo para consultar si hay un nombre agregado
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
    
    //Inserta datos en el catálogo de plantas
    public void insertCatalogoPlanta(CatalogoPlantaDTO planta) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertCatalogoPlanta ?, ?, ?, ?");
        Object[] params = {planta.getNombre_planta(), planta.getEstado_planta(), planta.getDireccion_planta(), planta.getIp_publica()};
        
        qr.update(sql.toString(), params);
    }
    
    //Consulta toda la tabla del catalogo de planta
    public List<CatalogoPlantaDTO> getAllPlantas() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectPetCatPlanta");
        
        ResultSetHandler rsh = new BeanListHandler(CatalogoPlantaDTO.class);
        List<CatalogoPlantaDTO> lista = (List<CatalogoPlantaDTO>) qr.query(sql.toString(), rsh);
        return lista;
    }
    
    public List<CatalogoPlantaDTO> getAllPlantas(int idCatalogoPlanta) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectPetCatPlantaById ?");
        
        Object[] params = {idCatalogoPlanta};
        
        ResultSetHandler rsh = new BeanListHandler(CatalogoPlantaDTO.class);
        List<CatalogoPlantaDTO> lista = (List<CatalogoPlantaDTO>) qr.query(sql.toString(), rsh, params);
        return lista;
    }
}
