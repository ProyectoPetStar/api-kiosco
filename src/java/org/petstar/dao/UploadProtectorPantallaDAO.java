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
import static org.petstar.configurations.Utils.convertSqlToDay;
import static org.petstar.configurations.Utils.sumarFechasDias;
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
    
    public imagenDTO getProtectorPantallaById(int idImagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectImagenById ?");
        Object[] params = {idImagen};
        
        ResultSetHandler rsh = new BeanHandler(imagenDTO.class);
        imagenDTO imagen = (imagenDTO) qr.query(sql.toString(), rsh, params);
        
        if(imagen != null){
            imagen.setFecha_registro(sumarFechasDias(imagen.getFecha_registro(), 2));
            imagen.setFecha_registro_string(convertSqlToDay(imagen.getFecha_registro()));
            
            if(imagen.getFecha_modifica_registro() != null){
                imagen.setFecha_modifica_registro(sumarFechasDias(imagen.getFecha_modifica_registro(), 2));
                imagen.setFecha_modifica_registro_string(convertSqlToDay(imagen.getFecha_modifica_registro()));
            }
        }       
        return imagen;
    }
    
    public void updateDatosProtectorPantalla(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateDatosImagen ?, ?, ?, ?");
        Object[] params = {imagen.getId_imagen(), imagen.getNombre(), imagen.getDescripcion(), imagen.getId_usuario_modifica_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public void updateImagen(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateImagen ?, ?");
        Object[] params = {imagen.getId_imagen(), imagen.getImagen()};
        
        qr.update(sql.toString(), params);
    }
}
