/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import java.sql.Date;
import java.sql.SQLException;
import org.petstar.configurations.PoolDataSource;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;


/**
 *
 * @author Tech-Pro
 */

public class UsersDAO {


    /**
     * Perfil 
     * Metodo que devuelve la información a detalle del usuario
     * @param id_usuario_kiosko
     * @return
     * @throws Exception 
     */
    public UserDTO getUsuarioById(int id_usuario_kiosko) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_selectPerfilById ?");
        Object[] params = {
            id_usuario_kiosko
        };
        
        ResultSetHandler rsh = new BeanHandler(UserDTO.class);
        UserDTO datosUsuario = (UserDTO) qr.query(sql.toString(), rsh, params);
        CatalogoPerfilDAO catalogosDAO = new CatalogoPerfilDAO();
        
        if(datosUsuario != null){
            datosUsuario.setPerfil(catalogosDAO.getPerfilById(datosUsuario.getId_perfil()));
        }
        return datosUsuario;
    }
    
    /**
     * Actualización de Password
     * Metodo que actualiza el password del usuario
     * @param contraseniaNueva
     * @param idAcceso
     * @throws Exception 
     */
    public void changePassword(String contraseniaNueva, int idAcceso) throws Exception{
        
       DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_updateContrasenia ?, ?");
        Object[] params = {
            idAcceso, contraseniaNueva 
        };
        
        qr.update(sql.toString(), params);
       
    }

    /**
     * Actualización de Usuario
     * Metodo que actiualiza los datos de un usuario.
     * @param user
     * @param fecha_actualizacion
     * @throws Exception 
     */
    public void updateUser(UserDTO usuario) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateUsuarioKiosco ?, ?, ?, ?, ?, ?");
        Object[] params = {
            usuario.getId_usuario_kiosko(),
            usuario.getUsuario(),
            usuario.getCorreo_electronico(),
            usuario.getNombre_usuario(),
            usuario.getApellidos(),
            usuario.getId_perfil()
        };
        qr.update(sql.toString(), params);
    }
    
  
    /**
     * Validación usuario valido
     * Metodo que valida que el usuario Sonarh no este registrado en ETAD
     * @param id_usuario_kiosko
     * @return
     * @throws Exception 
     */
    public ResultInteger validaExistUsers(int id_usuario_kiosko) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();        
        sql.append("EXEC sp_validaUsuario ?");
        Object[] params = { id_usuario_kiosko };        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger result = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return result;
    }
    
}
