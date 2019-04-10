/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dto;

/**
 *
 * @author TECH-PRO
 */
public class CatalogoPlantaDTO {
    private int id_planta;
    private String nombre_planta;
    private String estado_planta;
    private String direccion_planta;
    private String ip_publica;
    private String imagen;
    private int activo;

    public int getId_planta() {
        return id_planta;
    }

    public void setId_planta(int id_planta) {
        this.id_planta = id_planta;
    }

    public String getNombre_planta() {
        return nombre_planta;
    }

    public void setNombre_planta(String nombre_planta) {
        this.nombre_planta = nombre_planta;
    }

    public String getEstado_planta() {
        return estado_planta;
    }

    public void setEstado_planta(String estado_planta) {
        this.estado_planta = estado_planta;
    }

    public String getDireccion_planta() {
        return direccion_planta;
    }

    public void setDireccion_planta(String direccion_planta) {
        this.direccion_planta = direccion_planta;
    }

    public String getIp_publica() {
        return ip_publica;
    }

    public void setIp_publica(String ip_publica) {
        this.ip_publica = ip_publica;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }   
}