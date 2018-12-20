/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import javax.servlet.http.HttpServletRequest;
import org.petstar.dao.CatalogoPlantaDAO;
import org.petstar.dao.KioscoDAO;
import org.petstar.dao.ReportesDAO;
import org.petstar.dto.AplicacionDTO;
import org.petstar.dto.PlantaDTO;
import org.petstar.dto.UserDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ReportesJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Ramiro
 */
public class ControllerReportes {
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR  = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Valor o Descripción ya existe";
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";
    private static final String MSG_IP = "Ya esta en uso la IP ";
    
    public OutputJson getConteoKiosco(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        OutputJson output = new OutputJson();
        ResponseJson response = new ResponseJson();
        
        try{
           UserDTO sesion = autenticacion.isValidToken(request);
           if(sesion != null){
               if(sesion.getId_perfil() == 1){
                   ReportesDAO reportes = new ReportesDAO();
                   CatalogoPlantaDAO planta = new CatalogoPlantaDAO();
                   KioscoDAO kiosco = new KioscoDAO();
                   ReportesJson data = new ReportesJson();
                   AplicacionDTO aplicacion = reportes.aplicacionMasUtilizada();
                   PlantaDTO plantaUtilzada = reportes.plantaMasUtilizada();
                   
                   data.setListReportes(reportes.conteoKiosco());
                   data.setListPlanta(planta.getAllPlantas());
                   data.setListKiosco(kiosco.getAllKioscos());
                   data.setAplicacion(aplicacion);
                   data.setPlanta(plantaUtilzada);
                   
                   output.setData(data);
                   response.setMessage(MSG_SUCESS);
                   response.setSucessfull(true);
               }else{
                   response.setMessage(MSG_PERFIL);
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
