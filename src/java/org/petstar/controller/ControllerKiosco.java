/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import javax.servlet.http.HttpServletRequest;
import org.petstar.dto.KioscoDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;
import static org.petstar.configurations.Utils.convertStringToSql;
import org.petstar.dao.KioscoDAO;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;
import org.petstar.model.KioscoJson;

/**
 *
 * @author Ramiro
 */
public class ControllerKiosco {
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR  = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Valor o Descripción ya existe";    
    
    public OutputJson insertCatalogoPlanta(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            KioscoDTO kiosco = new KioscoDTO();
            kiosco.setNombre_kiosko(request.getParameter("nombre_kiosco"));
            kiosco.setId_planta(Integer.parseInt(request.getParameter("id_planta")));
            kiosco.setIp_privada(request.getParameter("ip_privada"));
            kiosco.setId_usuario_registro(Integer.parseInt(request.getParameter("id_usuario_registro")));
            kiosco.setFecha_registro(convertStringToSql(request.getParameter("fecha_registro")));
            
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                KioscoDAO kioscoDao = new KioscoDAO();
                ResultInteger result = kioscoDao.insertValidaKiosco(kiosco.getNombre_kiosko());
                
                if(result.getResult().equals(0)){
                    kioscoDao.insertKiosco(kiosco);
                    
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
    
    public OutputJson getAllKioscos(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                KioscoJson data = new KioscoJson();
                KioscoDAO kioscoDao = new KioscoDAO();
                
                data.setListKiosco(kioscoDao.getAllKioscos());
                output.setData(data);
                response.setMessage(MSG_SUCESS);
                response.setSucessfull(true);
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
