/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import org.petstar.configurations.PoolDataSource;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.ResultString;

/**
 * Clase DAO de Autenticación
 * @author Tech-Pro
 */

public class AutenticacionDAO {

    /**
     * Consulta de token
     * Metodo que devuelve el token de un usuario en especifico
     * @param idUsuario
     * @return
     * @throws Exception 
     */
    public String getToken_Key(int idUsuario) throws Exception {
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT token AS result FROM pet_usuario_kiosko WHERE id_usuario_kiosko = ?");
        Object[] params = { idUsuario };
        
        ResultSetHandler rsh = new BeanHandler(ResultString.class);
        ResultString result = (ResultString) qr.query(sql.toString(), rsh, params);
        return result.getResult();
    }
    
    /**
     * Actualización de Key
     * Metodo que registra la key del token generado en la DB
     * @param idUsuario
     * @param token_key
     * @throws Exception 
     */
    public void updateToken_Key(int idUsuario, String token_key) throws Exception {
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateToken ?, ?");
        Object[] params = { idUsuario, token_key };
        
        qr.update(sql.toString(), params);
    }
    
    public ResultInteger getIdPerfil(int idUsuario) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("");
        Object[] params = {idUsuario};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger result = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return result;
    }
}
