/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.ResultString;

/**
 *
 * @author TECH-PRO
 */
public class ResultStringJson {
    private ResultString result;
    private List<ResultString> listResultString;

    public ResultString getResultString() {
        return result;
    }

    public void setResultString(ResultString resultString) {
        this.result = resultString;
    }

    public List<ResultString> getListResultString() {
        return listResultString;
    }

    public void setListResultString(List<ResultString> listResultString) {
        this.listResultString = listResultString;
    }   
}
