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
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UrlKioscosDTO;

/**
 *
 * @author Ramiro
 */
public class UrlKioscoDao {
    public void insertUrlKiosco(UrlKioscosDTO url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertPetUrlKioskos ?, ?, ?, ?");
        Object[] params = {url.getDescripcion(), url.getUrl(), url.getId_usuario_registro(), url.getFecha_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public ResultInteger validaUrl(String url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertValidaUrl ?");
        Object[] params = {url};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger)  qr.query(sql.toString(), rsh, params);
        return count;
    }
}
