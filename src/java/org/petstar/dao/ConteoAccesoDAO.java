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
 * @author Ramiro
 */
public class ConteoAccesoDAO {
    public void insertConteoAcceso(String ipPrivada, String ipPublica, int idUrlKiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertConteoAcceso ?, ?, ?");
        Object[] params = {ipPrivada, ipPublica, idUrlKiosco};
        
        qr.update(sql.toString(), params);
    }
}
