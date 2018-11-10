/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.petstar.configurations.Configuration;
import org.petstar.controller.ControllerCatalogoPlanta;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Ramiro
 */
@WebServlet(name = "CatalogoPlanta", urlPatterns = {"/CatalogoPlanta"})
public class CatalogoPlanta extends HttpServlet {

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
       Configuration.setHeadersJson(response);
       
       PrintWriter printWriter = response.getWriter();
       OutputJson output = new OutputJson();
       ControllerCatalogoPlanta controllerPlanta = new ControllerCatalogoPlanta();
       Gson gson = new Gson();
       
       try{
           String action = request.getParameter("action");
           switch(action){
               case "insertCatalogoPlanta":
                   output = controllerPlanta.insertCatalogoPlanta(request);
                   break;
               case "getAllPlanta":
                   output = controllerPlanta.getAllPlantas(request);
                   break;
               case "getAllPlantaById":
                   output = controllerPlanta.getAllPlantaById(request);
                   break;
           }
       }catch(Exception ex){
           ResponseJson responseJson = new ResponseJson();
           responseJson.setMessage(ex.getMessage());
           responseJson.setSucessfull(false);
           output.setResponse(responseJson);
       }finally{
           printWriter.print(gson.toJson(output));
           printWriter.close();
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
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Configuration.setHeadersJson(response);
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
