/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.petstar.configurations.Configuration;
import static org.petstar.configurations.Utils.encodeFileToBase64;
import org.petstar.dao.UploadProtectorPantallaDAO;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.ResultString;
import org.petstar.dto.UserDTO;
import org.petstar.dto.imagenDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ProtectorPantallaJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Ramiro
 */
public class ControllerUploadProtectorPantalla {

    private static final String MSG_SUCESS = "OK";
    private static final String MSG_ERROR = "Descripción de error: ";
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";
    
    public OutputJson insertUploadProtectorPantalla(HttpServletRequest request) {
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        try {
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UploadProtectorPantallaDAO pantallaDao = new UploadProtectorPantallaDAO();
                    imagenDTO imagen = new imagenDTO();
                    String nombre = IOUtils.toString(request.getPart("nombre").getInputStream(), "UTF-8");
                    String descripcion = IOUtils.toString(request.getPart("descripcion").getInputStream(), "UTF-8");
                    String idUsuario = IOUtils.toString(request.getPart("id_usuario").getInputStream(), "UTF-8");
                    int id_usuario = Integer.parseInt(idUsuario);
                    
                    ResultInteger result = pantallaDao.insertValidaNombre(nombre.trim());
                    
                    if(result.getResult().equals(0)){
                        final Part filePart = request.getPart("file");

                        String nombreArchivo = getFileName(filePart);
                        String subField = nombreArchivo.substring(nombreArchivo.length() - 3, nombreArchivo.length());
                        String archivo = "protector_" + UUID.randomUUID() + "." + subField;

                        File folder = new File(Configuration.PATH_PROTECTOR);
                        if (!folder.exists()) {
                            folder.mkdir();
                        }

                        String sFichero = "";

                        sFichero = Configuration.PATH_PROTECTOR + archivo;

                        OutputStream outFile = null;
                        InputStream filecontent = null;

                        outFile = new FileOutputStream(new File(sFichero));
                        filecontent = filePart.getInputStream();
                        int read = 0;

                        final byte[] bytes = new byte[1024];
                        while ((read = filecontent.read(bytes)) != -1) {
                            outFile.write(bytes, 0, read);
                        }

                        outFile.flush();
                        outFile.close();

                        imagen.setNombre(nombre);
                        imagen.setDescripcion(descripcion);
                        imagen.setImagen(archivo);
                        imagen.setId_usuario_registro(id_usuario);

                        pantallaDao.insertImagen(imagen);

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    }else{
                        response.setMessage("El nombre " +nombre+ " ya existe");
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
        } catch (Exception ex) {
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }

    public OutputJson getProtectorPantalla(HttpServletRequest request) {
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();       

        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();

        try {
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    int idImagen = Integer.parseInt(request.getParameter("id_imagen"));
                    UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();
                    ProtectorPantallaJson data = new ProtectorPantallaJson();

                    imagenDTO img = protectorDao.getProtectorPantallaById(idImagen);

                    /**
                     * Aqui inicia transformacion de imagen a base 64
                     */
                    File file = new File(Configuration.PATH_PROTECTOR + img.getImagen());
                    img.setImg_base64(encodeFileToBase64(file));

                    /**
                     * Aqui termina transformacion de file a base64
                     */
                    data.setImagen(img);

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
        } catch (Exception ex) {
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
    
    public OutputJson updateDatosProtectorPantalla(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();
                    imagenDTO imagen = new imagenDTO();
                    imagen.setId_imagen(Integer.parseInt(request.getParameter("id_imagen")));
                    imagen.setNombre(request.getParameter("nombre"));
                    imagen.setDescripcion(request.getParameter("descripcion"));
                    imagen.setId_usuario_modifica_registro(Integer.parseInt(request.getParameter("id_usuario")));
                    
                    ResultInteger result = protectorDao.updateValidaNombre(imagen.getId_imagen(), imagen.getNombre());
                    
                    if(result.getResult().equals(0)){
                        protectorDao.updateDatosProtectorPantalla(imagen);

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    }else{
                        response.setMessage("El nombre " +imagen.getNombre().trim()+ " ya existe");
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

    public OutputJson updateImagen(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    imagenDTO ima = new imagenDTO();
            
                    String idImagen = IOUtils.toString(request.getPart("id_imagen").getInputStream(), "UTF-8");
                    String imagen = IOUtils.toString(request.getPart("imagen").getInputStream(), "UTF-8");

                    int id_imagen = Integer.parseInt(idImagen);

                    final Part filePart = request.getPart("file");

                    String nombreArchivo = getFileName(filePart);
                    String subField = nombreArchivo.substring(nombreArchivo.length() - 3, nombreArchivo.length());
                    String archivo = "protector_" + UUID.randomUUID() + "." + subField;

                    File folder = new File(Configuration.PATH_PROTECTOR);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    String sFichero = "";
                    String sFicheroDos = "";

                    sFichero = Configuration.PATH_PROTECTOR + archivo;
                    sFicheroDos = Configuration.PATH_PROTECTOR + imagen;

                    File fichero = new File(sFicheroDos);
                    if(fichero.exists()){
                        fichero.delete();
                    }

                    OutputStream outFile = null;
                    InputStream filecontent = null;

                    outFile = new FileOutputStream(new File(sFichero));
                    filecontent = filePart.getInputStream();
                    int read = 0;

                    final byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        outFile.write(bytes, 0, read);
                    }
                    outFile.flush();
                    outFile.close();

                    ima.setId_imagen(id_imagen);
                    ima.setImagen(archivo);

                    UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();
                    protectorDao.updateImagen(ima);

                    response.setMessage(archivo);
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
    
    public OutputJson getAllImagen(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    ProtectorPantallaJson data = new ProtectorPantallaJson();
                    UploadProtectorPantallaDAO dao = new UploadProtectorPantallaDAO();

                    List<imagenDTO> lista = dao.getAllKioscos();

                    for(imagenDTO ima : lista){
                        File file = new File(Configuration.PATH_PROTECTOR + ima.getImagen());
                        ima.setImg_base64(encodeFileToBase64(file));
                    }

                    data.setListImagen(lista);
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
    
    public OutputJson seleccionImagen(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();
        imagenDTO imagen = new imagenDTO();
        
        imagen.setPosicion(Integer.parseInt(request.getParameter("posicion")));
        imagen.setId_imagen(Integer.parseInt(request.getParameter("id_imagen")));
        imagen.setId_usuario_modifica_registro(Integer.parseInt(request.getParameter("id_usuario")));
                
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    int idImagen = Integer.parseInt(request.getParameter("id_imagen"));
                    ResultInteger valida = protectorDao.validaWallpaper(idImagen);
                    
                    if(valida.getResult().equals(0)){
                        ResultString fecha = protectorDao.seleccionImagen(imagen);
                        if(fecha != null){
                            response.setMessage(fecha.getResult());
                            response.setSucessfull(true);
                        }
                        else{
                            response.setMessage("ERROR NO SE CONCRETÓ LA ACCIÓN");
                            response.setSucessfull(false);
                        }
                    }
                    else{
                        response.setMessage("Esta imagen ya esta siendo utilizada en el protector, elija otra");
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
    
    public OutputJson deleteImagen(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    imagenDTO imagen = new imagenDTO();
                    imagen.setId_imagen(Integer.parseInt(request.getParameter("id_imagen")));
                    imagen.setImagen(request.getParameter("imagen"));

                    UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();     

                    ResultInteger result = protectorDao.validaSeleccionImagen(imagen.getId_imagen());

                    if(result.getResult().equals(0)){
                        protectorDao.deleteImagen(imagen);
                        
                        File folder = new File(Configuration.PATH_PROTECTOR);
                        if (!folder.exists()) {
                            folder.mkdir();
                        }

                        String sFichero = "";

                        sFichero = Configuration.PATH_PROTECTOR + imagen.getImagen();

                        File fichero = new File(sFichero);
                        if(fichero.exists()){
                            fichero.delete();
                        }        

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    }
                    else{
                        response.setMessage("La imagen se encuentra en uso, no se puede eliminar");
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
        
    public String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }    
}
