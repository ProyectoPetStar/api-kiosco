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
    private String nombre_planta;
    private String ip_privada;
    private String imagen;
    private int activo;
    private int id_usuario_registro;
    private Date fecha_registro;
    private int id_usuario_modifica_registro;
    private Date fecha_modifica_registro;
    private String fecha_registro_string;
    private String fecha_modifica_registro_string;
    private CatalogoPlantaDTO planta;
    private String marca_kiosco;
    private String modelo_kiosco;

    public String getMarca_kiosco() {
        return marca_kiosco;
    }

    public void setMarca_kiosco(String marca_kiosco) {
        this.marca_kiosco = marca_kiosco;
    }

    public String getModelo_kiosco() {
        return modelo_kiosco;
    }

    public void setModelo_kiosco(String modelo_kiosco) {
        this.modelo_kiosco = modelo_kiosco;
    }

    public CatalogoPlantaDTO getPlanta() {
        return planta;
    }

    public void setPlanta(CatalogoPlantaDTO planta) {
        this.planta = planta;
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

    public String getNombre_planta() {
        return nombre_planta;
    }

    public void setNombre_planta(String nombre_planta) {
        this.nombre_planta = nombre_planta;
    }
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
