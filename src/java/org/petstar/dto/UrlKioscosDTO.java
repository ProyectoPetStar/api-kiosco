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
public class UrlKioscosDTO {
    private int id_url_kiosko;
    private String descripcion;
    private String imagen;
    private String url;
    private int activo;
    private int id_usuario_registro;
    private Date fecha_registro;
    private int id_usuario_modifica_registro;
    private Date fecha_modifica_registro;

    public int getId_url_kiosko() {
        return id_url_kiosko;
    }

    public void setId_url_kiosko(int id_url_kiosko) {
        this.id_url_kiosko = id_url_kiosko;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
