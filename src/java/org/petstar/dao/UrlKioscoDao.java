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
import static org.petstar.configurations.Utils.convertSqlToDay;
import static org.petstar.configurations.Utils.sumarFechasDias;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UrlKioscosDTO;
import org.petstar.dto.ResultString;

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
        Object[] params = {url.getDescripcion(), url.getNombre().trim(), url.getUrl().trim(), url.getId_usuario_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public ResultInteger validaUrl(String url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertValidaUrl ?");
        Object[] params = {url};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public ResultInteger insertValidaNombre(String nombre) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertValidaDescripcionUrl ?");
        
        Object[] params = {nombre};
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public List<UrlKioscosDTO> getUrlKiosco() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectUrlKiosco");
        
        ResultSetHandler rsh = new BeanListHandler(UrlKioscosDTO.class);
        List<UrlKioscosDTO> lista = (List<UrlKioscosDTO>) qr.query(sql.toString(), rsh);
        
        for(UrlKioscosDTO url : lista){
            url.setFecha_registro(sumarFechasDias(url.getFecha_registro(), 2));
            url.setFecha_registro_string(convertSqlToDay(url.getFecha_registro()));
            
            if(url.getFecha_modifica_registro() != null){
                url.setFecha_modifica_registro(sumarFechasDias(url.getFecha_modifica_registro(), 2));
                url.setFecha_modifica_registro_string(convertSqlToDay(url.getFecha_modifica_registro()));
            }            
        }
        return lista;
    }
    
    public UrlKioscosDTO getUrlKioscoById(int idUrlKiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectUrlKioscoById ?");
        
        Object[] params = {idUrlKiosco};
        ResultSetHandler rsh = new BeanHandler(UrlKioscosDTO.class);
        UrlKioscosDTO datosUrl = (UrlKioscosDTO) qr.query(sql.toString(), rsh, params);
        
        if(datosUrl != null){
            datosUrl.setFecha_registro(sumarFechasDias(datosUrl.getFecha_registro(), 2));
            datosUrl.setFecha_registro_string(convertSqlToDay(datosUrl.getFecha_registro()));
            
            if(datosUrl.getFecha_modifica_registro() != null){
                datosUrl.setFecha_modifica_registro(sumarFechasDias(datosUrl.getFecha_modifica_registro(), 2));
                datosUrl.setFecha_modifica_registro_string(convertSqlToDay(datosUrl.getFecha_modifica_registro()));
            }
        }
        return datosUrl;
    }
    
    public ResultInteger updateValidaNombreUrl(int idUrlKiosco, String nombre) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateValidaDescripcionUrl ?, ?");
        Object[] params = {idUrlKiosco, nombre};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        
        return count;
    }
    
    public ResultInteger updateValidaUrl(int idUrlKiosco, String url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateValidaUrl ?, ?");
        Object[] params = {idUrlKiosco, url};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public void updateUrlKiosco(UrlKioscosDTO url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateUrlKiosco ?, ?, ?, ?, ?, ?");
        Object[] params = {url.getId_url_kiosko(), url.getDescripcion(), url.getNombre().trim(), url.getUrl().trim(), url.getActivo(), url.getId_usuario_modifica_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public void deleteUrlKioscoById(int idUrlKiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_deleteUrlKiosco ?");
        Object[] params = {idUrlKiosco};
        
        qr.update(sql.toString(), params);
    }
    
    public ResultString getNameWallpaper() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_selectNombreImagenByActivo ");
        ResultSetHandler rsh = new BeanHandler(ResultString.class);
        ResultString wallpaper = (ResultString) qr.query(sql.toString(), rsh );
        return wallpaper;
    }
    
    public List<ResultString> getListNameWallpaper() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectNombreImagenByActivo");
        ResultSetHandler rsh = new BeanListHandler(ResultString.class);
        List<ResultString> wallpaper = (List<ResultString>) qr.query(sql.toString(), rsh);
        return wallpaper;
    }
}
