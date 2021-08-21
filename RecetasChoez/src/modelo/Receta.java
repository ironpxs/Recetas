/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author joel1
 */
public class Receta implements Serializable, Comparable<Receta>{
    private String nombre;
    private Categorias categoria;
    private int dificultad;
    private float tiempo_preparacion;
    private String urlFoto;
    private TreeSet<Ingrediente> ingredientes;
    private String instrucciones;

    public Receta(String nombre, Categorias categoria, int dificultad, float tiempo_preparacion, String urlFoto, TreeSet<Ingrediente> ingredientes, String instrucciones) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.tiempo_preparacion = tiempo_preparacion;
        this.urlFoto = urlFoto;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
    }
    

    

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public float getTiempo_preparacion() {
        return tiempo_preparacion;
    }

    public void setTiempo_preparacion(float tiempo_preparacion) {
        this.tiempo_preparacion = tiempo_preparacion;
    }
    
    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }
    
    
    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }
    
    public TreeSet<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(TreeSet<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.nombre);
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
        final Receta other = (Receta) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre.toUpperCase();
    }
    
    //ORDEN NATURAL ES POR NOMBRE
    public int compareTo(Receta r){
        return nombre.compareToIgnoreCase(r.nombre);
    }
    
    public Receta buscarXNombre(String nom){
        if(nombre.equalsIgnoreCase(nom) || nombre.toLowerCase().contains(nom.toLowerCase())){
            return this;
        }
        else{
            return null;
        }
    }
    
    public Receta buscarXIngrediente(String nomIngrediente){
        Receta receta = null;
        for(Ingrediente i:ingredientes){
            if(i.getNombre().equalsIgnoreCase(nomIngrediente) || i.getNombre().toLowerCase().contains(nomIngrediente.toLowerCase())){
                receta = this;
            }
        }
        return receta;
    }
    
    public float calcularTotalCalorias(){
        float total = 0;
        for(Ingrediente i:ingredientes){
            total += i.getCalorias();
        }
        return total;
    }
    
}
