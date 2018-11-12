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
import org.petstar.dto.KioscoDTO;
import org.petstar.dto.ResultInteger;

/**
 *
 * @author Ramiro
 */
public class KioscoDAO {
    public ResultInteger insertValidaKiosco (String nombre_kiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertValidaKiosco ?");
        
        Object[] params = {nombre_kiosco};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public void insertKiosco(KioscoDTO kiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertKiosco ?, ?, ?, ?, ?");
        Object[] params = {kiosco.getNombre_kiosko(), kiosco.getId_planta(), kiosco.getIp_privada(), kiosco.getId_usuario_registro()
                , kiosco.getFecha_registro()};
        
        qr.update(sql.toString(), params);
    }
}
