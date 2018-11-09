/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.petstar.dto.UrlKioscosDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;
import static org.petstar.configurations.Utils.convertStringToSql;
import org.petstar.dao.UrlKioscoDao;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;

/**
 *
 * @author Ramiro
 */
public class ControllerUrlKioscos {
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR  = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Valor o Descripción ya existe";
    
    public OutputJson insertUrlKioscos(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UrlKioscosDTO url = new UrlKioscosDTO();
            url.setDescripcion(request.getParameter("descripcion"));
            url.setUrl(request.getParameter("url"));
            url.setId_usuario_registro(Integer.parseInt(request.getParameter("id_usuario_registro")));
            url.setFecha_registro(convertStringToSql(request.getParameter("fecha_registro")));
            
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                UrlKioscoDao urlDao = new UrlKioscoDao();
                
                ResultInteger result = urlDao.validaUrl(url.getUrl());
                
                if(result.getResult().equals(0)){
                    urlDao.insertUrlKiosco(url);
                    
                    response.setMessage(MSG_SUCESS);
                    response.setSucessfull(true);
                }else{
                    response.setMessage(MSG_INVALID);
                    response.setSucessfull(false);
                }
            }else{
                response.setMessage(MSG_LOGOUT);
                response.setSucessfull(false);
            }
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
}
