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
import org.petstar.dto.KioscoDTO;
import org.petstar.dto.ResultInteger;

/**
 *
 * @author Ramiro
 */
public class KioscoDAO {
    public ResultInteger insertValidaNombreKiosco (String nombre_kiosco) throws Exception{
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
    
    public List<KioscoDTO> getAllKioscos() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectPetKiosco");
        
        ResultSetHandler rsh = new BeanListHandler(KioscoDTO.class);
        List<KioscoDTO> lista = (List<KioscoDTO>) qr.query(sql.toString(), rsh);
        return lista;
    }
    
    public List<KioscoDTO> getKioscoById(int idKiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectKioscoById ?");
        
        Object[] params = {idKiosco};
        ResultSetHandler rsh = new BeanListHandler(KioscoDTO.class);
        List<KioscoDTO> lista = (List<KioscoDTO>) qr.query(sql.toString(), rsh, params);
        return lista;
    }
    
    public void updateKiosco(KioscoDTO kiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateKiosco ?, ?, ?, ?, ?, ?");
        Object[] params = {kiosco.getId_kiosko(), kiosco.getNombre_kiosko(), kiosco.getId_planta(), kiosco.getIp_privada()
            , kiosco.getId_usuario_modifica_registro(), kiosco.getFecha_modifica_registro_string()};
        
        qr.update(sql.toString(), params);
    }
    
    public ResultInteger updateValidaKiosco(int idKiosco, String nombreKiosco) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateValidaNombreKiosco ?, ?");
        Object[] params = {idKiosco, nombreKiosco};
        
        ResultSetHandler rsh = new BeanListHandler(KioscoDTO.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public ResultInteger updateValidaIpPrivada(int idKiosco, int idPlanta, String ipPrivada) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateValidaIpPrivada ?, ?, ?");
        Object[] params = {idKiosco, idPlanta, ipPrivada};
        
        ResultSetHandler rsh = new BeanListHandler(KioscoDTO.class);
        ResultInteger count = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return count;
    }
}
