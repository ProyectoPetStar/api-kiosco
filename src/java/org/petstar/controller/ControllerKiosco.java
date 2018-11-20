/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import static org.petstar.configurations.Utils.convertSqlToDay;
import org.petstar.dto.KioscoDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;
import static org.petstar.configurations.Utils.convertStringToSql;
import static org.petstar.configurations.Utils.sumarFechasDias;
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
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";
    private static final String MSG_IP = "Ip ya registrada";
    
     public OutputJson insertKiosco(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        Gson gson = new Gson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    KioscoDAO kioscoDao = new KioscoDAO();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    KioscoDTO kiosco = gson.fromJson(jsonResponse.getJSONObject("kiosco").toString(), KioscoDTO.class);
                    
                    ResultInteger result = kioscoDao.insertValidaNombreKiosco(kiosco.getNombre_kiosko());

                    if(result.getResult().equals(0)){
                        kiosco.setFecha_registro(convertStringToSql(kiosco.getFecha_registro_string()));
                        kioscoDao.insertKiosco(kiosco);

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    }else{
                        response.setMessage(MSG_INVALID);
                        response.setSucessfull(false);
                    }
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
    
    public OutputJson getAllKioscos(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    KioscoJson data = new KioscoJson();
                    KioscoDAO kioscoDao = new KioscoDAO();

                    data.setListKiosco(kioscoDao.getAllKioscos());
                    
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
    
    public OutputJson getKioscoById(HttpServletRequest request){
        int idKiosco = Integer.parseInt(request.getParameter("id_kiosco"));
        
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            
            
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    KioscoJson data = new KioscoJson();
                    KioscoDAO kioscoDao = new KioscoDAO();  
                    
                    data.setKiosco(kioscoDao.getKioscoById(idKiosco));                      
                    
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
    
    public OutputJson updateKiosco(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson responseJson = new ResponseJson();
        OutputJson outputJson = new OutputJson();
        
        Gson gson = new Gson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    KioscoDAO kioscoDao = new KioscoDAO();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    KioscoDTO kiosco = gson.fromJson(jsonResponse.getJSONObject("kiosco").toString(), KioscoDTO.class);
                    
                    ResultInteger validaNombre = kioscoDao.updateValidaKiosco(kiosco.getId_kiosko(), kiosco.getNombre_kiosko());
                    
                    if(validaNombre.getResult().equals(0)){
                        ResultInteger validaIp = kioscoDao.updateValidaIpPrivada(kiosco.getId_kiosko(), kiosco.getId_planta(), kiosco.getIp_privada());
                        if(validaIp.getResult().equals(0)){
                            kiosco.setFecha_registro(convertStringToSql(kiosco.getFecha_registro_string()));
                            kiosco.setFecha_modifica_registro(convertStringToSql(kiosco.getFecha_modifica_registro_string()));
                            
                            kioscoDao.updateKiosco(kiosco);
                            responseJson.setMessage(MSG_SUCESS);
                            responseJson.setSucessfull(true);
                        }else{
                            responseJson.setMessage(MSG_IP);
                            responseJson.setSucessfull(false);
                        }
                    }else{
                        responseJson.setMessage(MSG_INVALID);
                        responseJson.setSucessfull(false);
                    }                    
                }else{
                    responseJson.setMessage(MSG_PERFIL);
                    responseJson.setSucessfull(false);
                }
            }else{
                responseJson.setMessage(MSG_LOGOUT);
                responseJson.setSucessfull(false);
            }
        }catch(Exception ex){
            responseJson.setMessage(MSG_ERROR + ex.getMessage());
            responseJson.setSucessfull(false);
        }
        outputJson.setResponse(responseJson);
        return outputJson;
    }
}
