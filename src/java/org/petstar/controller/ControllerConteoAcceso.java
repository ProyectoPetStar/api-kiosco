/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import javax.servlet.http.HttpServletRequest;
import org.petstar.dao.ConteoAccesoDAO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Ramiro
 */
public class ControllerConteoAcceso {
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_ERROR  = "Descripción de error: ";
    
    public OutputJson insertConteoAcceso(HttpServletRequest request){
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            String ipPrivada = request.getParameter("ip_privada");
            String ipPublica = request.getParameter("ip_publica");
            ConteoAccesoDAO conteo = new ConteoAccesoDAO();
            
            conteo.insertConteoAcceso(ipPrivada, ipPublica);
            response.setMessage(MSG_SUCESS);
            response.setSucessfull(true);
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
}
