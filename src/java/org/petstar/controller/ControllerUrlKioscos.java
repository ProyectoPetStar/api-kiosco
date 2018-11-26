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
import org.petstar.dto.UrlKioscosDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;
import static org.petstar.configurations.Utils.convertStringToSql;
import org.petstar.dao.UrlKioscoDao;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;
import org.petstar.model.UrlKioscoJson;

/**
 *
 * @author Ramiro
 */
public class ControllerUrlKioscos {
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR  = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Ya esta en uso el nombre: ";
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";
    private static final String MSG_URL = "Ya esta en uso la URL ingresada";
    
    public OutputJson insertUrlKioscos(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        Gson gson = new Gson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UrlKioscoDao urlDao = new UrlKioscoDao();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    UrlKioscosDTO url = gson.fromJson(jsonResponse.getJSONObject("app").toString(), UrlKioscosDTO.class);
                    
                    ResultInteger result = urlDao.validaUrl(url.getUrl());

                    if(result.getResult().equals(0)){
                        ResultInteger des = urlDao.insertValidaNombre(url.getNombre().trim());
                        if(des.getResult().equals(0)){
                            urlDao.insertUrlKiosco(url);

                            response.setMessage(MSG_SUCESS);
                            response.setSucessfull(true);
                        }else{
                            response.setMessage(MSG_INVALID + url.getNombre());
                            response.setSucessfull(false);
                        }                        
                    }else{
                        response.setMessage(MSG_URL);
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
    
    public OutputJson getAllUrlKiosco(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UrlKioscoJson data = new UrlKioscoJson();
                    UrlKioscoDao urlDao = new UrlKioscoDao();
                    
                    data.setListUrlKiosco(urlDao.getUrlKiosco());
                    
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
    
    public OutputJson getUrlKioscoById(HttpServletRequest request){
        int idUrlKiosco = Integer.parseInt(request.getParameter("id_url_kiosco"));
        
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UrlKioscoJson data = new UrlKioscoJson();
                    UrlKioscoDao urlDao = new UrlKioscoDao();
                    
                    data.setUrlKiosco(urlDao.getUrlKioscoById(idUrlKiosco));
                    
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
    
    public OutputJson updateUrlKiosco(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        Gson gson = new Gson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UrlKioscoDao urlDao = new UrlKioscoDao();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    UrlKioscosDTO url = gson.fromJson(jsonResponse.getJSONObject("app").toString(), UrlKioscosDTO.class);
                    
                    ResultInteger des = urlDao.updateValidaNombreUrl(url.getId_url_kiosko(), url.getNombre().trim());
                    if(des.getResult().equals(0)){
                        ResultInteger ur = urlDao.updateValidaUrl(url.getId_url_kiosko(), url.getUrl().trim());
                        
                        if(ur.getResult().equals(0)){
                            urlDao.updateUrlKiosco(url);
                            response.setMessage(MSG_SUCESS);
                            response.setSucessfull(true);
                        }else{
                            response.setMessage(MSG_URL);
                            response.setSucessfull(false);
                        }
                    }else{
                        response.setMessage(MSG_INVALID + url.getNombre());
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
    
    public OutputJson deleteUrlKiosco(HttpServletRequest request){
        int idUrlKiosco = Integer.parseInt(request.getParameter("id_url_kiosco"));
        
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil()== 1){
                    UrlKioscoDao urlDao = new UrlKioscoDao();
                    urlDao.deleteUrlKioscoById(idUrlKiosco);
                    
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
