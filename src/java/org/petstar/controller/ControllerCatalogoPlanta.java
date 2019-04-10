/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import com.google.gson.Gson;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.petstar.configurations.Configuration;
import static org.petstar.configurations.Utils.encodeFileToBase64;
import org.petstar.dao.CatalogoPlantaDAO;
import org.petstar.dto.CatalogoPlantaDTO;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;
import org.petstar.model.CatalogoPlantaJson;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;

/**
 * 
 * @author TECH-PRO
 */
public class ControllerCatalogoPlanta {

    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Valor o Descripción ya existe";
    private static final String MSG_NOEXISTE = "La planta no existe";
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";

    public OutputJson insertCatalogoPlanta(HttpServletRequest request) {
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        Gson gson = new Gson();

        try {
            UserDTO sesion = autenticacion.isValidToken(request);
            if (sesion != null) {
                if (sesion.getId_perfil() == 1) {
                    CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    CatalogoPlantaDTO planta = gson.fromJson(jsonResponse.getJSONObject("planta").toString(), CatalogoPlantaDTO.class);

                    ResultInteger result = plantaDao.validaNombrePlanta(planta.getNombre_planta());

                    if (result.getResult().equals(0)) {
                        plantaDao.insertCatalogoPlanta(planta);

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    } else {
                        response.setMessage(MSG_INVALID);
                        response.setSucessfull(false);
                    }
                } else {
                    response.setMessage(MSG_PERFIL);
                    response.setSucessfull(false);
                }
            } else {
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

    public OutputJson getAllPlantas(HttpServletRequest request) {
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();

        try {
            UserDTO sesion = autenticacion.isValidToken(request);
            if (sesion != null) {
                if (sesion.getId_perfil() == 1) {
                    CatalogoPlantaJson data = new CatalogoPlantaJson();
                    CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();

                    List<CatalogoPlantaDTO> lista = plantaDao.getAllPlantas();
                    for (CatalogoPlantaDTO item : lista) {
                        File file = new File(Configuration.PATH_PLANTAS + item.getImagen());
                        item.setImagen(encodeFileToBase64(file));
                    }

                    data.setListPlanta(lista);
                    output.setData(data);
                    response.setMessage(MSG_SUCESS);
                    response.setSucessfull(true);
                } else {
                    response.setMessage(MSG_PERFIL);
                    response.setSucessfull(false);
                }
            } else {
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

    public OutputJson getAllPlantaById(HttpServletRequest request) {
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();

        try {
            int id_planta = Integer.parseInt(request.getParameter("id_planta"));

            UserDTO sesion = autenticacion.isValidToken(request);
            if (sesion != null) {
                if (sesion.getId_perfil() == 1) {
                    CatalogoPlantaJson data = new CatalogoPlantaJson();
                    CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();

                    ResultInteger result = plantaDao.validaId(id_planta);
                    if (result.getResult().equals(1)) {
                        data.setPlanta(plantaDao.getAllPlantasById(id_planta));
                        output.setData(data);
                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    } else {
                        response.setMessage(MSG_NOEXISTE);
                        response.setSucessfull(false);
                    }
                } else {
                    response.setMessage(MSG_PERFIL);
                    response.setSucessfull(false);
                }
            } else {
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

    public OutputJson updateCatalogoPlanta(HttpServletRequest request) {
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();

        Gson gson = new Gson();

        try {
            UserDTO sesion = autenticacion.isValidToken(request);

            if (sesion != null) {
                if (sesion.getId_perfil() == 1) {
                    CatalogoPlantaDAO plantaDao = new CatalogoPlantaDAO();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    CatalogoPlantaDTO planta = gson.fromJson(jsonResponse.getJSONObject("planta").toString(), CatalogoPlantaDTO.class);

                    ResultInteger result = plantaDao.updateValidaCatalogoPlanta(planta.getId_planta(), planta.getNombre_planta());

                    if (result.getResult().equals(0)) {
                        plantaDao.updateCatalogoPlanta(planta);

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    } else {
                        response.setMessage(MSG_INVALID);
                        response.setSucessfull(false);
                    }
                } else {
                    response.setMessage(MSG_PERFIL);
                    response.setSucessfull(false);
                }
            } else {
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
                File file = new File(Configuration.PATH_PLANTAS + nombre_image);
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
}
