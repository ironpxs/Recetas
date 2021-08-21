/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author joel1
 */
public class Ingrediente implements Serializable,Comparable<Ingrediente>{
    private String nombre;
    private String cantidad;
    private float calorias;

    public Ingrediente(String nombre, String cantidad, float calorias) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingrediente other = (Ingrediente) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    public int compareTo(Ingrediente o){
        return this.getNombre().compareToIgnoreCase(o.getNombre());
    }
    
    @Override
    public String toString() {
        return nombre + "-" + cantidad + " (" + calorias + " cal)";
    }

    
    
    
}
