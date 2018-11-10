/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import javax.servlet.http.HttpServletRequest;
import org.petstar.dao.CatalogoPlantaDAO;
import org.petstar.dto.CatalogoPlantaDTO;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;
import org.petstar.model.CatalogoPlantaJson;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Ramiro
 */
public class ControllerCatalogoPlanta {
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR  = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Valor o Descripción ya existe";
    private static final String MSG_NOEXISTE = "La planta no existe";
    
    public OutputJson insertCatalogoPlanta(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            CatalogoPlantaDTO planta = new CatalogoPlantaDTO();
            planta.setNombre_planta(request.getParameter("nombre_planta"));
            planta.setEstado_planta(request.getParameter("estado_planta"));
            planta.setDireccion_planta(request.getParameter("direccion_planta"));
            planta.setIp_publica(request.getParameter("ip_publica"));
            
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();
                
                ResultInteger result = plantaDao.validaNombrePlanta(planta.getNombre_planta());
                
                if(result.getResult().equals(0)){
                    plantaDao.insertCatalogoPlanta(planta);
                    
                    response.setMessage(MSG_SUCESS);
                    response.setSucessfull(true);
                }
                else{
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
    
    public OutputJson getAllPlantas(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                CatalogoPlantaJson data = new CatalogoPlantaJson();
                CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();
                
                data.setListPlanta(plantaDao.getAllPlantas());
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
    
    public OutputJson getAllPlantaById(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            int id_planta = Integer.parseInt(request.getParameter("id_planta"));
            
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                CatalogoPlantaJson data = new CatalogoPlantaJson();
                CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();                
                   
                ResultInteger result = plantaDao.validaId(id_planta);
                if(result.getResult().equals(1)){
                    data.setListPlanta(plantaDao.getAllPlantasById(id_planta));
                    output.setData(data);
                    response.setMessage(MSG_SUCESS);
                    response.setSucessfull(true);
                }else{
                    response.setMessage(MSG_NOEXISTE);
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
