/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.ResultString;
import org.petstar.dto.UrlKioscosDTO;

/**
 *
 * @author TECH-PRO
 */
public class UrlKioscoJson {
    private UrlKioscosDTO urlKiosco;
    private List<UrlKioscosDTO> listUrlKiosco;
    private List<ResultString> wallpapers;
    

    public UrlKioscosDTO getUrlKiosco() {
        return urlKiosco;
    }

    public void setUrlKiosco(UrlKioscosDTO urlKiosco) {
        this.urlKiosco = urlKiosco;
    }

    public List<UrlKioscosDTO> getListUrlKiosco() {
        return listUrlKiosco;
    }

    public void setListUrlKiosco(List<UrlKioscosDTO> listUrlKiosco) {
        this.listUrlKiosco = listUrlKiosco;
    }   

    public List<ResultString> getWallpapers() {
        return wallpapers;
    }

    public void setWallpapers(List<ResultString> wallpapers) {
        this.wallpapers = wallpapers;
    }  
   
}
