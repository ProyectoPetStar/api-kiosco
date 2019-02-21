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
public class imagenDTO {
    private int id_imagen;
    private String nombre;
    private String descripcion;
    private String imagen;
    private int seleccion_imagen;
    private int id_usuario_registro;
    private Date fecha_registro;
    private int id_usuario_modifica_registro;
    private Date fecha_modifica_registro;
    private String fecha_registro_string;
    private String fecha_modifica_registro_string;
    private String wallpaper;
    private int posicion;
    
    public int getId_imagen() {
        return id_imagen;
    }

    public void setId_imagen(int id_imagen) {
        this.id_imagen = id_imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getSeleccion_imagen() {
        return seleccion_imagen;
    }

    public void setSeleccion_imagen(int seleccion_imagen) {
        this.seleccion_imagen = seleccion_imagen;
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

    public String getFecha_registro_string() {
        return fecha_registro_string;
    }

    public void setFecha_registro_string(String fecha_registro_string) {
        this.fecha_registro_string = fecha_registro_string;
    }

    public String getFecha_modifica_registro_string() {
        return fecha_modifica_registro_string;
    }

    public void setFecha_modifica_registro_string(String fecha_modifica_registro_string) {
        this.fecha_modifica_registro_string = fecha_modifica_registro_string;
    }  

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }   
}
