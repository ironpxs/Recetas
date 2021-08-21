/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ordenamiento;

import java.util.Comparator;
import modelo.Receta;

/**
 *
 * @author joel1
 */
public class OrdenarXDificultad implements Comparator<Receta>{

    @Override
    public int compare(Receta o1, Receta o2) {
        return o1.getDificultad()-o2.getDificultad();
    }
    
}
