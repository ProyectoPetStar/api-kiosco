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
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.petstar.dao.UploadProtectorPantallaDAO;
import org.petstar.dto.imagenDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;
import org.petstar.service.UploadShape;

/**
 *
 * @author Ramiro
 */
public class ControllerUploadProtectorPantalla {
    public OutputJson insertUploadProtectorPantalla(HttpServletRequest request) throws Exception{
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        try{
            imagenDTO imagen = new imagenDTO();
            String nombre = IOUtils.toString(request.getPart("nombre").getInputStream(), "UTF-8");
            String descripcion = IOUtils.toString(request.getPart("descripcion").getInputStream(), "UTF-8");
            String idUsuario = IOUtils.toString(request.getPart("id_usuario").getInputStream(), "UTF-8");
            int id_usuario = Integer.parseInt(idUsuario);
            
            final Part filePart = request.getPart("file");
            UploadShape ups = new UploadShape();
            
            String nombreArchivo = ups.getFileName(filePart);
            String subField = nombreArchivo.substring(nombreArchivo.length()-3, nombreArchivo.length());
            
            File folder = new File("C:\\petstar\\protectorPantalla\\");
            if(!folder.exists()){
                folder.mkdir();
            }

            String sFichero = "";
            sFichero = "C:\\petstar\\protectorPantalla\\" +UUID.randomUUID() + "."+subField;

            OutputStream outFile = null;
            InputStream filecontent = null;
            
            outFile = new FileOutputStream(new File(sFichero));
            filecontent = filePart.getInputStream();
            int read = 0;
            
            final byte[] bytes = new byte[1024];
            while((read = filecontent.read(bytes)) != -1){
                outFile.write(bytes, 0, read);                
            }    
            
            imagen.setNombre(nombre);
            imagen.setDescripcion(descripcion);
            imagen.setImagen(nombreArchivo);
            imagen.setId_usuario_registro(id_usuario);
            
            UploadProtectorPantallaDAO pantallaDao = new UploadProtectorPantallaDAO();            
            pantallaDao.insertImagen(imagen);
            
            response.setMessage("Ok");
            response.setSucessfull(true);
        }catch(Exception ex){
            response.setMessage("Error" + ex.getMessage());
            response.setSucessfull(false);
        }        
        output.setResponse(response);
        return output;
    }    
    
}
