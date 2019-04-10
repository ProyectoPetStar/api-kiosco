/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.petstar.configurations.PoolDataSource;

/**
 *
 * @author TECH-PRO
 */
public class UploadImageDAO {
    
    public void changeImage(String table, String pk, String image, int id) throws Exception {
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("UPDATE ").append(table).append(" SET imagen = ? WHERE ").append(pk).append(" = ?");
        Object[] params = { image, id };
        
        qr.update(sql.toString(), params);
    }
}
