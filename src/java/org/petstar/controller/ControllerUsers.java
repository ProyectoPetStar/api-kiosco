/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import com.google.gson.Gson;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.petstar.dao.UsersDAO;
import org.petstar.dto.ResultInteger;
import org.petstar.model.OutputJson;
import org.petstar.model.UserResponseJson;
import org.petstar.dto.UserDTO;
import static org.petstar.configurations.Utils.getCurrentDate;

/**
 *
 * @author Tech-Pro
 */
public class ControllerUsers {

    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "No existe el usuario";
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";

    /**
     * Perfil Metodo para obtener datos del usuario
     *
     * @param request
     * @return
     */
    public OutputJson getUsuarioById(HttpServletRequest request) {
        int id_usuario_kiosko = Integer.parseInt(request.getParameter("id_usuario_kiosko"));
        UserResponseJson response = new UserResponseJson();
        OutputJson output = new OutputJson();
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();

        try {
            UserDTO sesion = autenticacion.isValidToken(request);
            if (sesion != null) {
                if (sesion.getId_perfil() == 1) {
                    UsersDAO userDAO = new UsersDAO();
                    ResultInteger result = userDAO.validaExistUsers(id_usuario_kiosko);
                    if (result.getResult().equals(1)) {
                        UserResponseJson userData = new UserResponseJson();
                        userData.setUsuario(userDAO.getUsuarioById(sesion.getId_usuario_kiosko()));
                        output.setData(userData);
                        response.setSucessfull(true);
                        response.setMessage(MSG_SUCESS);
                    } else {
                        response.setSucessfull(false);
                        response.setMessage(MSG_INVALID);
                    }
                } else {
                    response.setSucessfull(false);
                    response.setMessage(MSG_PERFIL);
                }

            } else {
                response.setSucessfull(false);
                response.setMessage(MSG_LOGOUT);
            }
        } catch (Exception ex) {
            response.setSucessfull(false);
            response.setMessage(MSG_ERROR + ex.getMessage());
        }
        output.setResponse(response);
        return output;
    }

    /**
     * Cambio de contraseña Metodo que permite el cambio del password del
     * usuario, validando su contraseña anterior
     *
     * @param request
     * @return
     */
    public OutputJson changePasswordUser(HttpServletRequest request) {
        int idAcceso = Integer.parseInt(request.getParameter("id_acceso"));
        String newPassword = request.getParameter("new_password");

        UserResponseJson response = new UserResponseJson();
        OutputJson output = new OutputJson();
        ControllerAutenticacion auth = new ControllerAutenticacion();

        try {
            UserDTO usuario = auth.isValidToken(request);
            if (usuario != null) {
                UsersDAO userDAO = new UsersDAO();

                userDAO.changePassword(newPassword, idAcceso);

                response.setMessage("OK");
                response.setSucessfull(true);
            } else {
                response.setSucessfull(false);
                response.setMessage("Inicie sesión nuevamente");
            }
        } catch (Exception ex) {
            response.setSucessfull(false);
            response.setMessage("Descripcion de error: " + ex.getMessage());
        }
        output.setResponse(response);
        return output;
    }

    /**
     * Modificación de Usuario Metodo que permite la actualización de datos de
     * un usuario.
     *
     * @param request
     * @return
     */
    public OutputJson updateUser(HttpServletRequest request) {

        UserResponseJson response = new UserResponseJson();
        OutputJson output = new OutputJson();
        ControllerAutenticacion auth = new ControllerAutenticacion();

        try {
            UserDTO sesion = auth.isValidToken(request);
            if (sesion != null) {
                Gson gson = new Gson();
                UsersDAO userDAO = new UsersDAO();
                //Date fecha = getCurrentDate(); no se requiere en este momento

                String jsonString = request.getParameter("data");
                JSONObject jsonResponse = new JSONObject(jsonString);
                UserDTO usuario = gson.fromJson(jsonResponse.getJSONObject("usuario").toString(), UserDTO.class);
                
                userDAO.updateUser(usuario);
                
                response.setMessage(MSG_SUCESS);
                response.setSucessfull(true);

            } else {
                response.setSucessfull(false);
                response.setMessage(MSG_LOGOUT);
            }
        } catch (Exception ex) {
            response.setSucessfull(false);
            response.setMessage(MSG_ERROR + ex.getMessage());
        }
        output.setResponse(response);
        return output;
    }

}
