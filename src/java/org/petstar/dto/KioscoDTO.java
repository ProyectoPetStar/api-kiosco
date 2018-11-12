/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dto;

import java.sql.Date;

/**
 *
 * @author Ramiro
 */
public class KioscoDTO {
    private int id_kiosko;
    private String nombre_kiosko;
    private int id_planta;
    private String ip_privada;
    private String imagen;
    private int activo;
    private int id_usuario_registro;
    private Date fecha_registro;
    private int id_usuario_modifica_registro;
    private Date fecha_modifica_registro;

    public int getId_kiosko() {
        return id_kiosko;
    }

    public void setId_kiosko(int id_kiosko) {
        this.id_kiosko = id_kiosko;
    }

    public String getNombre_kiosko() {
        return nombre_kiosko;
    }

    public void setNombre_kiosko(String nombre_kiosko) {
        this.nombre_kiosko = nombre_kiosko;
    }

    public int getId_planta() {
        return id_planta;
    }

    public void setId_planta(int id_planta) {
        this.id_planta = id_planta;
    }

    public String getIp_privada() {
        return ip_privada;
    }

    public void setIp_privada(String ip_privada) {
        this.ip_privada = ip_privada;
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

    public int getId_usuario_registro() {
        return id_usuario_registro;
    }

    public void setId_usuario_registro(int id_usuario_registro) {
        this.id_usuario_registro = id_usuario_registro;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getId_usuario_modifica_registro() {
        return id_usuario_modifica_registro;
    }

    public void setId_usuario_modifica_registro(int id_usuario_modifica_registro) {
        this.id_usuario_modifica_registro = id_usuario_modifica_registro;
    }

    public Date getFecha_modifica_registro() {
        return fecha_modifica_registro;
    }

    public void setFecha_modifica_registro(Date fecha_modifica_registro) {
        this.fecha_modifica_registro = fecha_modifica_registro;
    }   
}
