 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaneOrganizer;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modelo.Categorias;
import modelo.Ingrediente;
import modelo.Receta;
import recetaschoez.RecetasChoez;

/**
 *
 * @author joel1
 */
public class PaneNuevaReceta {
    private VBox root;
    TextField nomTxt, tiempoPreparacionTxt,imagenTxt;
    ComboBox categoriaCb, dificultadCb;
    Button examinarImagenBtn, nuevoIngredienteBtn, agregarRecetaBtn, cancelarRecetaBtn;
    TreeSet<Ingrediente> ingredientes = new TreeSet<>();
    TextArea areaIngredientes, areaInstrucciones;
    FileChooser fileChooser;
    File file;
    public PaneNuevaReceta(){
        createContent();
    }
    
    public VBox getRoot(){
        return root;
    }
    
    public void createContent(){
        root = new VBox();
        
        HBox hNom = new HBox(5);
        Label nomLb = new Label("Nombre: ");
        nomTxt = new TextField();
        hNom.getChildren().addAll(nomLb,nomTxt);
        hNom.setAlignment(Pos.CENTER_LEFT);
        
        HBox hCategorias = new HBox(5);
        Label categoriaLb = new Label("Categoria: ");
        categoriaCb = new ComboBox();
        //AGREGAR ELEMENTOS ENUM A UN COMBO BOX.
        Categorias[] categoriasList = Categorias.values();
        for(Categorias cat:categoriasList){
            categoriaCb.getItems().add(cat.name().replace("_", " "));
        }
        hCategorias.getChildren().addAll(categoriaLb,categoriaCb);
        hCategorias.setAlignment(Pos.CENTER_LEFT);
        
        HBox hDificultad = new HBox(5);
        Label dificultadLb = new Label("Dificultad: ");
        dificultadCb = new ComboBox();
        //AGREGAR ITEMS DEL 1 AL 10 A UN COMBO BOX.
        for(int i=0;i<10;i++){
            dificultadCb.getItems().add(i+1);
        }
        hDificultad.getChildren().addAll(dificultadLb,dificultadCb);
        hDificultad.setAlignment(Pos.CENTER_LEFT);
        
        HBox hTiempo = new HBox(5);
        Label tiempoLb = new Label("Tiempo de preparación (min): ");
        tiempoPreparacionTxt = new TextField();
        hTiempo.getChildren().addAll(tiempoLb,tiempoPreparacionTxt);
        hTiempo.setAlignment(Pos.CENTER_LEFT);
        
        HBox hImagen = new HBox(5);
        Label imagenLb = new Label("Imagen: ");
        imagenTxt = new TextField();
        imagenTxt.setEditable(false);
        examinarImagenBtn = new Button("...");
        //SE CREA EL FILECHOOSER Y SE FILTRA LAS EXTENSIONES QUE SE ELEGIRáN
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Imágenes", "*.jpg","*.png")
        );
        examinarImagenBtn.setOnAction(e->{
             Stage fcStage = new Stage();
             fcStage.setTitle("Agregar imagen");
             file = fileChooser.showOpenDialog(fcStage);
             if(file != null){
                 imagenTxt.setText(file.getPath());
             }
             else{
                 imagenTxt.setText("C:\\Users\\Administrador\\Desktop\\Proyecto\\choezmejoramiento\\RecetasChoez\\src\\recursos\\sinfoto.jpg");
             }
        });
        hImagen.getChildren().addAll(imagenLb,imagenTxt,examinarImagenBtn);
        hImagen.setAlignment(Pos.CENTER_LEFT);
        
        Label ingredientesLb = new Label("Ingredientes: ");
        areaIngredientes = new TextArea();
        HBox hNuevoIngrediente = new HBox(5);
        Button nuevoIngredienteBtn = new Button("NUEVO INGREDIENTE");
        nuevoIngredienteBtn.setOnAction(e->{
            Stage nuevoIngredienteStage = new Stage();
            PaneNuevoIngrediente nI = new PaneNuevoIngrediente(this);
            Scene nuevoIngredienteScene = new Scene(nI.getRoot(),300,150);            
            nuevoIngredienteStage.setScene(nuevoIngredienteScene);
            nuevoIngredienteStage.setTitle("Nuevo ingrediente");
            nuevoIngredienteStage.show();
        });
        hNuevoIngrediente.getChildren().add(nuevoIngredienteBtn);
        hNuevoIngrediente.setAlignment(Pos.CENTER);
        
        
        Label instruccionesLb = new Label("Instrucciones: ");
        areaInstrucciones = new TextArea();
        
        HBox hBtn = new HBox(5);
        Button guardarBtn = new Button("GUARDAR");
        Button cancelarBtn = new Button("CANCELAR");
        hBtn.getChildren().addAll(guardarBtn,cancelarBtn);
        hBtn.setAlignment(Pos.CENTER);
        
        guardarBtn.setOnAction(e->{
            guardarReceta();
        });
        cancelarBtn.setOnAction(e->{
            nomTxt.getScene().getWindow().hide();
        });
        
        root.getChildren().addAll(hNom,hCategorias,hDificultad,hTiempo,hImagen,ingredientesLb,areaIngredientes,hNuevoIngrediente, instruccionesLb, areaInstrucciones, hBtn);
    }
    
    public void guardarReceta(){
        String nombre = nomTxt.getText();
        String urlImg = imagenTxt.getText();
        String areaInst = areaInstrucciones.getText();
        
        if(nombre.isEmpty() || tiempoPreparacionTxt.getText().isEmpty()){
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Guardar receta");
            alerta.setHeaderText("Fallo al guardar la receta.");
            alerta.setContentText("Necesario un nombre y un tiempo de preparación.");
            alerta.showAndWait();
        }
        else{
            Categorias categoriaReceta =  Categorias.valueOf(categoriaCb.getSelectionModel().getSelectedItem().toString().replace(" ", "_"));
            int numDificultad = Integer.parseInt(dificultadCb.getSelectionModel().getSelectedItem().toString());
            float tiempo = Float.parseFloat(tiempoPreparacionTxt.getText().replace(",", "."));
            Receta receta = new Receta(nombre,categoriaReceta,numDificultad,tiempo,urlImg,ingredientes,areaInst);
            RecetasChoez.recetas.add(receta);
            RecetasChoez.actualizarArchivoRecetas();
            RecetasChoez.actualizarListView();
            
        }
        nomTxt.getScene().getWindow().hide();
    }
    public void actualizarAreaIngredientes(Ingrediente ingrediente){
        areaIngredientes.appendText(ingrediente.toString()+"\n");            
    }
}
