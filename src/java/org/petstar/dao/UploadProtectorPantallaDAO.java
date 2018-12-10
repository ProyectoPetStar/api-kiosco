/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.petstar.configurations.PoolDataSource;
import org.petstar.dto.imagenDTO;

/**
 *
 * @author Ramiro
 */
public class UploadProtectorPantallaDAO {
    public void insertImagen(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertImagen ?, ?, ?, ?");
        Object[] params = {imagen.getNombre(), imagen.getDescripcion(), imagen.getImagen(), imagen.getId_usuario_registro()};
        
        qr.update(sql.toString(), params);
    }
}
