/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;


import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.petstar.configurations.Configuration;
import static org.petstar.configurations.Utils.encodeFileToBase64;
import org.petstar.dao.LoginDAO;
import org.petstar.dto.UserDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.UserResponseJson;

/**
 *
 * @author TECH-PRO
 */
public class ControllerLogin {

    /**
     * Logueo
     * Metodo que se encarga de validar los datos de usuario y brindar accesos 
     * al sistema.
     * @param request
     * @return 
     */
    public OutputJson Login(HttpServletRequest request) {
        String usuario_acceso = request.getParameter("usuario_acceso");
        String clave_acceso = request.getParameter("clave_acceso");
        
        UserResponseJson response = new UserResponseJson();
        OutputJson output = new OutputJson();

        LoginDAO dao = new LoginDAO();
        ControllerAutenticacion auth = new ControllerAutenticacion();
        try {
            UserDTO datos_usuario = dao.Login(usuario_acceso, clave_acceso);
            
            if (datos_usuario != null) {
                String token = auth.createJWT(datos_usuario);
                datos_usuario.setToken(token);
                 /**
                 * Aqui inicia transformacion de imagen a base 64
                 */
                File file =  new File(Configuration.PATH_USUARIOS + datos_usuario.getImagen());
                datos_usuario.setImagen64(encodeFileToBase64(file));                
                /**
                 * Aqui termina transformacion de file a base64
                 */
                response.setSucessfull(true);
                response.setUsuario(datos_usuario);
            } else {
                response.setSucessfull(false);
                response.setMessage("Usuario o contraseña incorrectos");
            }
        } catch (Exception ex) {
            response.setSucessfull(false);
            response.setMessage("" + ex.getMessage());
        }
        output.setResponse(response);
        return output;

    }

}
