/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import java.sql.Date;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.petstar.configurations.PoolDataSource;
import org.petstar.dto.AplicacionDTO;
import org.petstar.dto.PlantaDTO;
import org.petstar.dto.ReportesDTO;

/**
 *
 * @author Ramiro
 */
public class ReportesDAO {
    public List<ReportesDTO> conteoKiosco() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectConteoKioscos");
        ResultSetHandler rsh = new BeanListHandler(ReportesDTO.class);
        List<ReportesDTO> lista = (List<ReportesDTO>) qr.query(sql.toString(), rsh);
        return lista;
    }
    
    public AplicacionDTO aplicacionMasUtilizada() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectAplicacionMasUtilizada");
        
        ResultSetHandler rsh = new BeanHandler(AplicacionDTO.class);
        AplicacionDTO aplicacion = (AplicacionDTO) qr.query(sql.toString(), rsh);
        return aplicacion;
    }
    
    public PlantaDTO plantaMasUtilizada() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectPlantaMasUtilizada");
        
        ResultSetHandler rsh = new BeanHandler(PlantaDTO.class);
        PlantaDTO aplicacion = (PlantaDTO) qr.query(sql.toString(), rsh);
        return aplicacion;
    }
    
    public List<ReportesDTO> reporteByDia(int idKiosco, int idPlanta, Date fecha) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectReporteByDia ?, ?, ?");
        Object[] params = {idKiosco, idPlanta, fecha};
        
        ResultSetHandler rsh = new BeanListHandler(ReportesDTO.class);
        List<ReportesDTO> lista = (List<ReportesDTO>) qr.query(sql.toString(), rsh, params);
        return lista;
    }
}
