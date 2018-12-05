/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.axis.encoding.Base64;
import org.petstar.configurations.Configuration;
import org.petstar.dao.UploadImageDAO;
import org.petstar.dto.UserDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Alfredo Neri
 */
public class ControllerUploadImage {
    
    public OutputJson uploadImage(HttpServletRequest request) throws Exception{
        OutputJson outputJson = new OutputJson();
        ResponseJson responseJson = new ResponseJson();
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
    
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    String[][] data = {
                        {"urls","pet_url_kioskos",Configuration.PATH_URLS,"id_url_kiosko"},
                        {"kiosco","pet_kiosko",Configuration.PATH_KIOSCOS,"id_kiosko"},
                        {"usuario","pet_usuario_kiosko",Configuration.PATH_USUARIOS,"id_usuario_kiosko"}
                    };

                    String stringFile = new String();
                   

                    String object = request.getParameter("object");
                    stringFile = request.getParameter("file");
                    stringFile = URLDecoder.decode(stringFile, "UTF-8");
                    int id = Integer.parseInt(request.getParameter("id"));
                    
                    int index = -1;
                    for (int y = 0; y<data.length; y++) {
                        if(data[y][0].equals(object)){
                            index = y;
                        }
                    }

                    String fileName = object+"_"+UUID.randomUUID()+".jpg";
                    boolean save = saveFIle(stringFile, fileName, data[index][2]);
                    if(save){
                        UploadImageDAO imageDAO = new UploadImageDAO();
                        imageDAO.changeImage(data[index][1], data[index][3], fileName, id);
                        responseJson.setSucessfull(true);
                        responseJson.setMessage("Success");
                    }else{
                        responseJson.setSucessfull(false);
                        responseJson.setMessage("Ocurrio un Error al Guardar la Imagen");
                    }
                }else{                    
                    responseJson.setSucessfull(false);
                    responseJson.setMessage("Este perfil no cuenta con los permisos para realizar la acción");
                }
            }else{
                responseJson.setSucessfull(false);
                responseJson.setMessage("Iniciar Sesión Nuevamente");
            }
        }catch(NumberFormatException ex){
            responseJson.setSucessfull(false);
            responseJson.setMessage("Error: " + ex.getMessage());
        }
        outputJson.setResponse(responseJson);
        return outputJson;
    }
    
    private boolean saveFIle(String file64, String nameFile, String pathFile){
        boolean estatus;
        try{
            StringBuilder stringFile = new StringBuilder();
            stringFile.append(file64);
            
            File paths = new File(pathFile);
            
            if(!paths.exists()){
                paths.mkdirs();
            }
                
            byte[] imageBytes = Base64.decode(stringFile.toString());
            
            try (FileOutputStream file = new FileOutputStream(pathFile + nameFile)) {
                file.write(imageBytes);
                file.close();
                String files = pathFile + nameFile;
                File fichero = new File(files);
                
                if(fichero.exists()){
                    estatus = true;
                }else{
                    estatus = false;
                }
            }
           
        }catch(IOException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            estatus=false;
        }
        
        return estatus;
    }
}
