/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.petstar.configurations.Configuration;

/**
 *
 * @author Ramiro
 */
@WebServlet(name = "UploadProtectorPantalla", urlPatterns = {"/UploadProtectorPantalla"})
public class UploadProtectorPantalla extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Configuration.setHeadersFile(response);
        try{
            String action = "";
            try{
                action = IOUtils.toString(request.getPart("action").getInputStream(), "UTF-8");
            }catch(Exception error){
                action = request.getParameter("action");
            }
            switch(action){
                case "insertUploadProtectorPantalla":
                    String nombre = IOUtils.toString(request.getPart("nombre").getInputStream(), "UTF-8");
                    String descripcion = IOUtils.toString(request.getPart("descripcion").getInputStream(), "UTF-8");
                    String seleccion = IOUtils.toString(request.getPart("seleccion").getInputStream(), "UTF-8");
                    String idUsuario = IOUtils.toString(request.getPart("id_usuario").getInputStream(), "UTF-8");
                    
                    int seleccion_imagen = Integer.parseInt(seleccion);
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
                    sFichero = "C:\\petstar\\protectorPantalla\\" +UUID.randomUUID() + subField;
                    
                    OutputStream outFile = null;
                    InputStream filecontent = null;
                    
                    try{
                        outFile = new FileOutputStream(new File(sFichero));
                        filecontent = filePart.getInputStream();
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while((read = filecontent.read(bytes)) != -1){
                            outFile.write(bytes, 0, read);
                        }
                    }catch(Exception ex){
                        System.out.println(ex);
                    }finally{
                        if(outFile != null){
                            outFile.close();
                        }
                        if(filecontent != null){
                            filecontent.close();
                        }
                    }
                    break;
            }
        }catch(Exception ex){
            response.getWriter().print(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
