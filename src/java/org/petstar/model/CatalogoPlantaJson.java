/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.CatalogoPlantaDTO;

/**
 *
 * @author TECH-PRO
 */
public class CatalogoPlantaJson {
    private CatalogoPlantaDTO planta;
    private List<CatalogoPlantaDTO> listPlanta;

    public CatalogoPlantaDTO getPlanta() {
        return planta;
    }

    public void setPlanta(CatalogoPlantaDTO planta) {
        this.planta = planta;
    }

    public List<CatalogoPlantaDTO> getListPlanta() {
        return listPlanta;
    }

    public void setListPlanta(List<CatalogoPlantaDTO> listPlanta) {
        this.listPlanta = listPlanta;
    }
    
    
}
