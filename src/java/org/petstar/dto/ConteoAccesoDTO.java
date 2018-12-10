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
public class ConteoAccesoDTO {
    private int id_conteo_acceso;
    private Date dia;
    private int id_kiosko;
    private String hora_acceso;
    private int id_url_kiosko;

    public int getId_conteo_acceso() {
        return id_conteo_acceso;
    }

    public void setId_conteo_acceso(int id_conteo_acceso) {
        this.id_conteo_acceso = id_conteo_acceso;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public int getId_kiosko() {
        return id_kiosko;
    }

    public void setId_kiosko(int id_kiosko) {
        this.id_kiosko = id_kiosko;
    }

    public String getHora_acceso() {
        return hora_acceso;
    }

    public void setHora_acceso(String hora_acceso) {
        this.hora_acceso = hora_acceso;
    }   

    public int getId_url_kiosko() {
        return id_url_kiosko;
    }

    public void setId_url_kiosko(int id_url_kiosko) {
        this.id_url_kiosko = id_url_kiosko;
    }    
}
