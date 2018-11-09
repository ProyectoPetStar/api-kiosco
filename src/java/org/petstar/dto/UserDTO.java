/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dto;

/**
 * DTO de Usuarios
 * @author Tech-Pro
 */
public class UserDTO {
    
    private int id_usuario_kiosko;
    private String nombre_usuario;
    private String usuario;
    private String imagen_administrador;
    private int id_perfil;
    private String descripcion;
    private int activo;
    private String token;

    public int getId_usuario_kiosko() {
        return id_usuario_kiosko;
    }

    public void setId_usuario_kiosko(int id_usuario_kiosko) {
        this.id_usuario_kiosko = id_usuario_kiosko;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImagen_administrador() {
        return imagen_administrador;
    }

    public void setImagen_administrador(String imagen_administrador) {
        this.imagen_administrador = imagen_administrador;
    }

    public int getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
