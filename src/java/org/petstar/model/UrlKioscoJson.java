/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.UrlKioscosDTO;

/**
 *
 * @author Ramiro
 */
public class UrlKioscoJson {
    private UrlKioscosDTO urlKiosco;
    private List<UrlKioscosDTO> listUrlKiosco;
    
    private String wallpaper;

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

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }
    
    
    

}
