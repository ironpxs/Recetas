/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaneOrganizer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import modelo.Receta;
import recetaschoez.RecetasChoez;

/**
 *
 * @author joel1
 */
public class PaneBuscadorNombre {
    private HBox root;
    Label nombreLb;
    TextField nombreTxt;
    Button buscarBtn;
    private int selector;
    RecetasChoez rC;
    public PaneBuscadorNombre(int selector, RecetasChoez rC){
        this.selector=selector;
        this.rC=rC;
        createContent();
    }
    
    public HBox getRoot(){
        return root;
    }
    
    public void createContent(){
        root = new HBox(7);
        nombreLb= new Label("Nombre: ");
        nombreTxt = new TextField();
        buscarBtn = new Button("BUSCAR");
        buscarBtn.setOnAction(e->{
            if(selector==1){
                buscarNombre();
            }
            else if(selector==2){
                buscarIngrediente();
            }
        });
        root.getChildren().addAll(nombreLb,nombreTxt,buscarBtn);
        
    }
    
    public void buscarNombre(){
        List<Receta> resultadoRecetas = new ArrayList<>();
        for(Receta r:RecetasChoez.recetas){
            Receta estaEs = r.buscarXNombre(nombreTxt.getText());
            if(estaEs != null){
                resultadoRecetas.add(estaEs);
            }
        }
        
        if(resultadoRecetas.isEmpty()){
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Resultado de búsqueda");
            alerta.setHeaderText("UPS!!!");
            alerta.setContentText("No se encontraron resultados.");
            alerta.showAndWait();
        }
        else{
            RecetasChoez.lstVwRecetas.getItems().clear();
            for(Receta rcta:resultadoRecetas){
                RecetasChoez.lstVwRecetas.getItems().add(rcta);
            }
            rC.mostrarInfo(resultadoRecetas.get(0));
            nombreTxt.getScene().getWindow().hide();
        }
        
    }
    public void buscarIngrediente(){
        List<Receta> resultadoRecetas = new ArrayList<>();
        for(Receta r:RecetasChoez.recetas){
            Receta estaEs = r.buscarXIngrediente(nombreTxt.getText());
            if(estaEs != null){
                resultadoRecetas.add(estaEs);
            }
        }
        
        if(resultadoRecetas.isEmpty()){
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Resultado de búsqueda");
            alerta.setHeaderText("UPS!!!");
            alerta.setContentText("No se encontraron resultados.");
            alerta.showAndWait();
        }
        else{
            RecetasChoez.lstVwRecetas.getItems().clear();
            for(Receta rcta:resultadoRecetas){
                RecetasChoez.lstVwRecetas.getItems().add(rcta);
            }
            rC.mostrarInfo(resultadoRecetas.get(0));
            nombreTxt.getScene().getWindow().hide();
        }
    }
}

