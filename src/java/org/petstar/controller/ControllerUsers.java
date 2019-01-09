/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import com.google.gson.Gson;
import static com.oracle.jrockit.jfr.Transition.To;
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
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.auth.Subject;
import org.petstar.configurations.Configuration;
import static org.petstar.configurations.Utils.encodeFileToBase64;
import org.petstar.dto.CorreoDTO;
import org.petstar.model.ResponseJson;

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
    private static final String MSG_PASS = "La contraseña actual no coincide, favor de corroborar";
    public static String Username = "";
    public static String PassWord = "";

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
        String contraseniaActual = request.getParameter("password_actual");

        UserResponseJson response = new UserResponseJson();
        OutputJson output = new OutputJson();
        ControllerAutenticacion auth = new ControllerAutenticacion();

        try {
            UserDTO usuario = auth.isValidToken(request);
            if (usuario != null) {
                UsersDAO userDAO = new UsersDAO();

                ResultInteger result = userDAO.changeValidaPassword(contraseniaActual, idAcceso);
                if (result.getResult().equals(1)) {
                    userDAO.changePassword(newPassword, idAcceso);

                    response.setMessage("OK");
                    response.setSucessfull(true);
                } else {
                    response.setMessage(MSG_PASS);
                    response.setSucessfull(false);
                }
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

    public OutputJson getImage(HttpServletRequest request) {
        String nombre_image = request.getParameter("nombre_image");

        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();

        ControllerAutenticacion auth = new ControllerAutenticacion();
        try {
            UserDTO sesion = auth.isValidToken(request);
            if (sesion != null) {
                /**
                 * Aqui inicia transformacion de imagen a base 64
                 */
                File file = new File(Configuration.PATH_USUARIOS + nombre_image);
                response.setMessage(encodeFileToBase64(file));
                response.setSucessfull(true);
               
                /**
                 * Aqui termina transformacion de file a base64
                 */

            } else {
                response.setSucessfull(false);
                response.setMessage(MSG_LOGOUT);
            }

        } catch (Exception ex) {
            response.setSucessfull(false);
            response.setMessage("" + ex.getMessage());
        }
        output.setResponse(response);
        return output;
    }
    
    public OutputJson recuperarContrasenia(HttpServletRequest request){
        String destino = request.getParameter("correo").trim();
        
        UserResponseJson response = new UserResponseJson();
        OutputJson output = new OutputJson();
                
        try{              
            UsersDAO user = new UsersDAO();
            ResultInteger id = user.recuperarId(destino);
            
            if(!id.getResult().equals(0)){
                String contrasenia = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
                
                CorreoDTO correo = new CorreoDTO();
                correo.setContrasenia("uoztuipxmghtghnb");
                correo.setUsuarioCorreo("rlunaaragon@gmail.com");
                correo.setDestino(destino);
                correo.setAsunto("Contraseña Nueva");
                correo.setMensaje("Su nueva contraseña es " +contrasenia);
                
                if(this.sendMessage(correo)){
                    user.changePassword(contrasenia, id.getResult());
                    response.setSucessfull(true);
                    response.setMessage("Se envio un correo a su cuenta con la nueva contraseña");
                }else{
                    response.setMessage("Error, no se envió el correo");
                    response.setSucessfull(false);
                }              
            }else{
                response.setMessage("El correo no está dado de alta");
                response.setSucessfull(false);
            }            
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }   
        output.setResponse(response);
        return output;
    }
    
    public boolean sendMessage(CorreoDTO c){
        ResponseJson response = new ResponseJson();
        try{            
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", c.getUsuarioCorreo());
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p, null);
            
            BodyPart texto = new MimeBodyPart();
            texto.setText(c.getMensaje());
            
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(c.getUsuarioCorreo()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getDestino()));
            mensaje.setSubject(c.getAsunto());
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            t.connect(c.getUsuarioCorreo(), c.getContrasenia());
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();       
            
            return true;
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            return false;
        }      
    }
}
