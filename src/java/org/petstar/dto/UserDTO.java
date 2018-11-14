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
    private String imagen;
    private String correo_electronico;
    private String apellidos;
    private int id_perfil;   
    private int activo;
    private CatalogoPerfilDTO perfil;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen64() {
        return imagen64;
    }

    public void setImagen64(String imagen64) {
        this.imagen64 = imagen64;
    }
    private String token;
    /**
    * Se agrega atributo donde se colocara la imagen en base64
    */
    private String imagen64;


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

    public int getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
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

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    

    public CatalogoPerfilDTO getPerfil() {
        return perfil;
    }

    public void setPerfil(CatalogoPerfilDTO perfil) {
        this.perfil = perfil;
    }
    
    
}
