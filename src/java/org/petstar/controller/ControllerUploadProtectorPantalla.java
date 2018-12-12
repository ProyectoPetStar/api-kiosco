/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import java.io.File;
import java.io.FileOutputStream;
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
import org.petstar.dto.ResultString;
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

    public OutputJson insertUploadProtectorPantalla(HttpServletRequest request) {
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        try {
            imagenDTO imagen = new imagenDTO();
            String nombre = IOUtils.toString(request.getPart("nombre").getInputStream(), "UTF-8");
            String descripcion = IOUtils.toString(request.getPart("descripcion").getInputStream(), "UTF-8");
            String idUsuario = IOUtils.toString(request.getPart("id_usuario").getInputStream(), "UTF-8");
            int id_usuario = Integer.parseInt(idUsuario);

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

            UploadProtectorPantallaDAO pantallaDao = new UploadProtectorPantallaDAO();
            pantallaDao.insertImagen(imagen);

            response.setMessage(MSG_SUCESS);
            response.setSucessfull(true);
        } catch (Exception ex) {
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }

    public OutputJson getProtectorPantalla(HttpServletRequest request) {
        int idImagen = Integer.parseInt(request.getParameter("id_imagen"));

        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();

        try {
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
        } catch (Exception ex) {
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
    
    public OutputJson updateDatosProtectorPantalla(HttpServletRequest request){
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            imagenDTO imagen = new imagenDTO();
            imagen.setId_imagen(Integer.parseInt(request.getParameter("id_imagen")));
            imagen.setNombre(request.getParameter("nombre"));
            imagen.setDescripcion(request.getParameter("descripcion"));
            imagen.setId_usuario_modifica_registro(Integer.parseInt(request.getParameter("id_usuario_modifica_registro")));
            
            UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();
            protectorDao.updateDatosProtectorPantalla(imagen);
            
            response.setMessage(MSG_SUCESS);
            response.setSucessfull(true);
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }

    public OutputJson updateImagen(HttpServletRequest request){
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
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
            
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
    
    public OutputJson getAllImagen(HttpServletRequest request){
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
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
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
    
    public OutputJson seleccionImagen(HttpServletRequest request){
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        try{
            imagenDTO imagen = new imagenDTO();
            imagen.setId_imagen(Integer.parseInt(request.getParameter("id_imagen")));
            imagen.setId_usuario_modifica_registro(Integer.parseInt(request.getParameter("id_usuario_modifica_registro")));
            
            UploadProtectorPantallaDAO protectorDao = new UploadProtectorPantallaDAO();
            ResultString fecha = protectorDao.seleccionImagen(imagen);
            
            response.setMessage(fecha.getResult());
            response.setSucessfull(true);
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
