/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.KioscoDTO;

/**
 *
 * @author Ramiro
 */
public class KioscoJson {
    private KioscoDTO kiosco;
    private List<KioscoDTO> listKiosco;

    public KioscoDTO getKiosco() {
        return kiosco;
    }

    public void setKiosco(KioscoDTO kiosco) {
        this.kiosco = kiosco;
    }

    public List<KioscoDTO> getListKiosco() {
        return listKiosco;
    }

    public void setListKiosco(List<KioscoDTO> listKiosco) {
        this.listKiosco = listKiosco;
    }   
}
