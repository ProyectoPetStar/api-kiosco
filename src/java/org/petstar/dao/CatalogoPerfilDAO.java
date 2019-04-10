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
import org.petstar.dto.CatalogoPerfilDTO;

/**
 *
 * @author TECH-PRO
 */
public class CatalogoPerfilDAO {
    
        public CatalogoPerfilDTO getPerfilById(int idPerfil) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectPerfilById ?");
        
        Object[] params = {idPerfil};
        
        ResultSetHandler rsh = new BeanHandler(CatalogoPerfilDTO.class);
        CatalogoPerfilDTO catalogosDTO = (CatalogoPerfilDTO)  qr.query(sql.toString(), rsh, params);        
        return catalogosDTO;
    }
    
}
