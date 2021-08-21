/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaneOrganizer;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Ingrediente;

/**
 *
 * @author joel1
 */
public class PaneNuevoIngrediente {
    private VBox root;
    TextField nomTxt, cantTxt, calTxt;
    PaneNuevaReceta receta;
    public PaneNuevoIngrediente(PaneNuevaReceta receta){
        this.receta = receta;
        createContent();
    }
    
    public VBox getRoot(){
        return root;
    }
    
    public void createContent(){
        root = new VBox();
        HBox hTitulo = new HBox(5);
        Label titulo = new Label("NUEVO INGREDIENTE");
        hTitulo.getChildren().add(titulo);
        hTitulo.setAlignment(Pos.CENTER);
        
        HBox hNom = new HBox(5);
        Label nomLb = new Label("Nombre: ");
        nomTxt = new TextField();
        hNom.getChildren().addAll(nomLb,nomTxt);
        hNom.setAlignment(Pos.CENTER);
        
        HBox hCant = new HBox(5);
        Label cantLb = new Label("Cantidad: ");
        cantTxt = new TextField();
        hCant.getChildren().addAll(cantLb,cantTxt);
        hCant.setAlignment(Pos.CENTER);
        
        HBox hCal = new HBox(5);
        Label calLb = new Label("Calorías: ");
        calTxt = new TextField();
        hCal.getChildren().addAll(calLb,calTxt);
        hCal.setAlignment(Pos.CENTER);
        
        HBox hBtn = new HBox(5);
        Button guardarBtn = new Button("GUARDAR");
        hBtn.getChildren().add(guardarBtn);
        hBtn.setAlignment(Pos.CENTER);
        
        guardarBtn.setOnAction(e->{
            guardarIngrediente();
        });
        root.getChildren().addAll(hTitulo,hNom,hCant,hCal,hBtn);
    }
    
    public void guardarIngrediente(){
        String nombre = nomTxt.getText();
            String cantidad = cantTxt.getText();
        if(nombre.isEmpty() || cantidad.isEmpty() || calTxt.getText().isEmpty()){
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Guardar ingrediente");
            alerta.setHeaderText("Fallo al guardar el ingrediente.");
            alerta.setContentText("Debe llenar todos los campos.");
            alerta.showAndWait();
        }
        else{
            
            float calorias = Float.valueOf(calTxt.getText());
            Ingrediente ingrediente = new Ingrediente(nombre,cantidad,calorias);
            receta.ingredientes.add(ingrediente);
            receta.actualizarAreaIngredientes(ingrediente);
            nomTxt.getScene().getWindow().hide();
        }
    }
    
    
   
    
}
