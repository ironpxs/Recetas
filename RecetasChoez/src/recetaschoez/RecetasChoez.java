/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recetaschoez;

import Ordenamiento.OrdenarXDificultad;
import Ordenamiento.OrdenarXTiempoAsc;
import Ordenamiento.OrdenarXTiempoDesc;
import PaneOrganizer.PaneBuscadorNombre;
import PaneOrganizer.PaneNuevaReceta;
import java.io.File;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Ingrediente;
import modelo.Receta;

/**
 *
 * @author joel1
 */
public class RecetasChoez extends Application {
    public static TreeSet<Receta> recetas;
    private static final String fileRecetas = "C:\\Users\\Administrador\\Desktop\\Proyecto\\choezmejoramiento\\RecetasChoez\\src\\recursos\\recetas.ser";
    VBox root;
    ImageView imageViewEncabezado = new ImageView();
    VBox izqBox;
    public static ListView lstVwRecetas;
    VBox derBox;
    ImageView imgMain;
    Receta obj;
    Label comidaLb;
    TextField catTxt;
    TextField difTxt;
    TextField tempTxt;
    TextField calTxt;
    Label ingredientesLb;
    TextArea areaIngredientes;
    TextArea areaInstrucciones;
    @Override 
    public void start(Stage primaryStage) {
        //Carga de datos
        cargarRecetas();
        //INSTANCIAR CONTENEDOR PRINCIPAL
        root = new VBox();
        //DISEÑO DE VENTANA
        //IMAGEN DE ENCABEZADO
        HBox hImagen = new HBox();
        try (FileInputStream fileImagen = new FileInputStream("C:\\Users\\Administrador\\Desktop\\Proyecto\\choezmejoramiento\\RecetasChoez\\src\\recursos\\encabezadoRecetasEspinoza.jpg")){
            imageViewEncabezado.setImage(new Image(fileImagen));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        }
        hImagen.getChildren().add(imageViewEncabezado);
        hImagen.setAlignment(Pos.CENTER);
        //CONTENEDOR IZQUIERDO
        izqBox = new VBox();
        izqBox.setPrefSize(208, 250);
        Label recetasLb = new Label("Recetas");
        recetasLb.setAlignment(Pos.CENTER_LEFT);
        lstVwRecetas = new ListView();
        lstVwRecetas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for(Receta r: recetas){
            lstVwRecetas.getItems().add(r);
        }
        lstVwRecetas.setOnMouseClicked(e->{
            obj = (Receta) lstVwRecetas.getSelectionModel().getSelectedItem();
            mostrarInfo(obj);
        });
        
        ScrollPane scrollListView = new ScrollPane();
        scrollListView.setContent(lstVwRecetas);
        HBox hagregarReceta = new HBox(); 
        Button agregarRecetaBtn = new Button("Agregar");
        hagregarReceta.getChildren().add(agregarRecetaBtn);
        hagregarReceta.setAlignment(Pos.CENTER);
        agregarRecetaBtn.setOnAction(e->{
            Stage nuevaRecetaStage = new Stage();
            PaneNuevaReceta nI = new PaneNuevaReceta();
            Scene nuevaRecetaScene = new Scene(nI.getRoot(),300,500);            
            nuevaRecetaStage.setScene(nuevaRecetaScene);
            nuevaRecetaStage.setTitle("Nueva receta");
            nuevaRecetaStage.show();
        });
        izqBox.getChildren().addAll(recetasLb, scrollListView,hagregarReceta);
        izqBox.setAlignment(Pos.CENTER_LEFT);
        //CONTENEDOR DERECHO
        derBox = new VBox();
        derBox.setPrefSize(610, 250);
        HBox hComida = new HBox();
        comidaLb = new Label("Nombre");
        hComida.getChildren().add(comidaLb);
        hComida.setAlignment(Pos.CENTER_LEFT);
        
        HBox hAtributos = new HBox();
        Label catLb = new Label("Categoria: ");
        catTxt = new TextField();
        catTxt.setPrefWidth(140);
        catTxt.setEditable(false);
        Label difLb = new Label("Dificultad: ");
        difTxt = new TextField();
        difTxt.setPrefWidth(30);
        difTxt.setEditable(false);
        Label tempLb = new Label("Tiempo: ");
        tempTxt = new TextField();
        tempTxt.setPrefWidth(50);
        tempTxt.setEditable(false);
        Label calLb = new Label("Calorías: ");
        calTxt = new TextField();
        calTxt.setPrefWidth(70);
        calTxt.setEditable(false);
        hAtributos.getChildren().addAll(catLb,catTxt,difLb,difTxt,tempLb,tempTxt,calLb,calTxt);
        hAtributos.setAlignment(Pos.CENTER_LEFT);
        hAtributos.setPrefSize(500, 100);
        HBox hDerechaCentral = new HBox();
        StackPane imgPane = new StackPane();
        try(FileInputStream imageFile = new FileInputStream("C:\\Users\\Administrador\\Desktop\\Proyecto\\choezmejoramiento\\RecetasChoez\\src\\recursos\\sinfoto.jpg")) {
            imgMain = new ImageView(new Image(imageFile));
            imgMain.setFitHeight(250);
            imgMain.setFitWidth(250);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        }
        imgPane.getChildren().add(imgMain);
        VBox besidesImg = new VBox();
        ingredientesLb = new Label("Ingredientes: ");
        ScrollPane scrollIngredientes  = new ScrollPane();
        areaIngredientes = new TextArea();
        scrollIngredientes.setContent(areaIngredientes);
        Label instruccionesLb = new Label("Instrucciones: ");
        ScrollPane scrollInstrucciones  = new ScrollPane();
        areaInstrucciones = new TextArea();
        scrollInstrucciones.setContent(areaInstrucciones);
        besidesImg.getChildren().addAll(ingredientesLb,scrollIngredientes,instruccionesLb,scrollInstrucciones);
        besidesImg.setAlignment(Pos.CENTER_RIGHT);
        hDerechaCentral.getChildren().addAll(imgPane,besidesImg);
        derBox.getChildren().addAll(hComida,hAtributos,hDerechaCentral);
        
        HBox hCombinado = new HBox();
        hCombinado.setPrefSize(418, 250);
        hCombinado.getChildren().addAll(izqBox,derBox);
        
        Label penultimaLb = new Label("Ordenamientos y búsqueda");
        HBox hPenultima = new HBox(10);
        hPenultima.setPrefSize(626, 80);
        ComboBox ordenCb = new ComboBox();
        String[] arrayOrden = {"Nombre","Dificultad","Tiempo de preparación"};
        for(String o:arrayOrden){
            ordenCb.getItems().add(o);
        }
        
        
        Button ordenBtn = new Button("ORDENAR");
        ordenBtn.setOnMouseClicked(e->{
            int selector = ordenCb.getSelectionModel().getSelectedIndex()+1;
            switch (selector) {
                case 1:
                    List<Receta> recetasList = new ArrayList<>();
                    for(Receta rec: recetas){
                        recetasList.add(rec);
                    }
                    Collections.sort(recetasList);
                    lstVwRecetas.getItems().clear();
                    for(Receta r:recetasList){
                        lstVwRecetas.getItems().add(r);
                    }
                    break;
                case 2:
                    List<Receta> recetasList2 = new ArrayList<>();
                    for(Receta rec: recetas){
                        recetasList2.add(rec);
                    }
                    Collections.sort(recetasList2, new OrdenarXDificultad());
                    lstVwRecetas.getItems().clear();
                    for(Receta r:recetasList2){
                        lstVwRecetas.getItems().add(r);
                    }
                    break;
                case 3:
                    List<Receta> recetasList3 = new ArrayList<>();
                    for(Receta rec: recetas){
                        recetasList3.add(rec);
                    }
                    Collections.sort(recetasList3, new OrdenarXTiempoDesc());
                    lstVwRecetas.getItems().clear();
                    for(Receta r:recetasList3){
                        lstVwRecetas.getItems().add(r);
                    }
                    break;
                default:
                    break;
            }
        });
        ComboBox buscarCb = new ComboBox();
        String[] arrayBuscar = {"Nombre","Ingrediente","Más fácil","Más difícil"};
        for(String b:arrayBuscar){
            buscarCb.getItems().add(b);
        }
        Button buscarBtn = new Button("BUSCAR");
        buscarBtn.setOnMouseClicked(e->{
            int selector = buscarCb.getSelectionModel().getSelectedIndex()+1;
            switch (selector) {
                case 1:
                    {
                        Stage buscarStage = new Stage();
                        PaneBuscadorNombre nI = new PaneBuscadorNombre(selector,this);
                        Scene buscarScene = new Scene(nI.getRoot(),350,30);
                        buscarStage.setScene(buscarScene);
                        buscarStage.setTitle("Buscar por nombre de receta");
                        buscarStage.show();
                        break;
                    }
                case 2:
                    {
                        Stage buscarStage = new Stage();
                        PaneBuscadorNombre nI = new PaneBuscadorNombre(selector,this);
                        Scene buscarScene = new Scene(nI.getRoot(),350,30);
                        buscarStage.setScene(buscarScene);
                        buscarStage.setTitle("Buscar por nombre de ingrediente");
                        buscarStage.show();
                        break;
                    }
                case 3:
                    {   
                        List<Receta> recetasListB3 = new ArrayList<>();
                        for(Receta rec: recetas){
                            recetasListB3.add(rec);
                        }
                        //MAS FACIL ES MENOR DIFICULTAD Y MENOR TIEMPO DE PREPARACION.
                        //LISTA PARA GUARDAR LAS RECETAS CON LA MENOR DIFICULTAD.
                        List<Receta> recetasF = recetasListB3;
                        //SE ORDENA POR DIFICULTAD DE MANERA ASCENDENTE.
                        Collections.sort(recetasF, new OrdenarXDificultad());
                        //SE OBTIENE LA MENOR DIFICULTAD
                        int dMin = recetasF.get(0).getDificultad();
                        //SE ELIMINA AQUELLAS RECETAS CON DIFICULTADES DIFERENTES A LA MINIMA.
                        Iterator<Receta> iterador = recetasF.iterator();
                        while(iterador.hasNext()){
                            Receta rD = iterador.next(); 
                            if(dMin!= rD.getDificultad()){
                                iterador.remove();
                            }
                        }
                        //SE ORDENA POR TIEMPO DE MANERA ASCENDENTE.
                        Collections.sort(recetasF, new OrdenarXTiempoAsc());
                        //SE OBTIENE EL MENOR TIEMPO.
                        float tMin = recetasF.get(0).getTiempo_preparacion();
                        //SE ELIMINA AQUELLAS RECETAS CON TIEMPOS DIFERENTES A LA MINIMA.
                        Iterator<Receta> iterador2 = recetasF.iterator();
                        while(iterador2.hasNext()){
                            Receta rT = iterador2.next(); 
                            if(tMin != rT.getTiempo_preparacion()){
                                iterador2.remove();
                            }
                        }
                        //SE ACTUALIZA EL LISTVIEW. 
                        //MOSTRANDO UNICAMENTE LAS MAS FACILES.
                        lstVwRecetas.getItems().clear();
                        for(Receta r:recetasF){
                            lstVwRecetas.getItems().add(r);
                        }
                        break;
                      } 
                case 4:
                    {   
                        List<Receta> recetasListB4 = new ArrayList<>();
                        for(Receta rec: recetas){
                            recetasListB4.add(rec);
                        }
                        //MAS DIFICIL ES MAYOR DIFICULTAD Y MAYOR TIEMPO DE PREPARACION.
                        //LISTA PARA GUARDAR LAS RECETAS CON LA MENOR DIFICULTAD.
                        List<Receta> recetasF2 = recetasListB4;
                        //SE ORDENA POR DIFICULTAD DE MANERA ASCENDENTE.
                        Collections.sort(recetasF2, new OrdenarXDificultad());
                        //SE OBTIENE LA MAYOR DIFICULTAD
                        int dMax = recetasF2.get(recetasF2.size()-1).getDificultad();
                        //SE ELIMINA AQUELLAS RECETAS CON DIFICULTADES DIFERENTES A LA MAXIMA.
                        Iterator<Receta> iterador3 = recetasF2.iterator();
                        while(iterador3.hasNext()){
                            Receta rD = iterador3.next(); 
                            if(dMax!= rD.getDificultad()){
                                iterador3.remove();
                            }
                        }
                        //SE ORDENA POR TIEMPO DE MANERA DESCENDENTE
                        Collections.sort(recetasF2, new OrdenarXTiempoDesc());
                        //SE OBTIENE EL MAYOR TIEMPO.
                        float tMax = recetasF2.get(0).getTiempo_preparacion();
                        //SE ELIMINA AQUELLAS RECETAS CON TIEMPOS DIFERENTES A LA MAXIMA.
                        Iterator<Receta> iterador4 = recetasF2.iterator();
                        while(iterador4.hasNext()){
                            Receta rT = iterador4.next(); 
                            if(tMax != rT.getTiempo_preparacion()){
                                iterador4.remove();
                            }
                        }
                        //SE ACTUALIZA EL LISTVIEW. 
                        //MOSTRANDO UNICAMENTE LAS MAS FACILES.
                        lstVwRecetas.getItems().clear();
                        for(Receta r:recetasF2){
                            lstVwRecetas.getItems().add(r);
                        }
                        break;
                    }
                default:
                    break;
            }
        });
        
        hPenultima.getChildren().addAll(ordenCb,ordenBtn,buscarCb,buscarBtn);
        hPenultima.setAlignment(Pos.CENTER);
        HBox hUltima = new HBox(7);
        hUltima.setPrefSize(626, 80);
        Button todaLaListaBtn = new Button("MOSTRAR LISTA COMPLETA");
        todaLaListaBtn.setOnAction(e->{
            actualizarListView();
        });
        Button reporteBtn = new Button("GENERAR REPORTE");
        reporteBtn.setOnAction(e->{
            generarReporte();
        });
        
        hUltima.getChildren().addAll(todaLaListaBtn,reporteBtn);
        hUltima.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(hImagen,hCombinado,penultimaLb,hPenultima,hUltima);
        
        Scene scene = new Scene(root, 708, 648);
        
        primaryStage.setTitle("Recetas Espinoza");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.show();
        
    }
    
    public void cargarRecetas(){
        recetas = new TreeSet();
        Path path = Paths.get(fileRecetas);
        if(Files.exists(path)){
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileRecetas))){
                recetas = (TreeSet<Receta>) in.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public static void actualizarArchivoRecetas(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileRecetas))){ 
           out.writeObject(recetas);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void actualizarListView(){
        
        lstVwRecetas.getItems().clear();
        for(Receta r:recetas){
            lstVwRecetas.getItems().add(r);
        }
    }
    
    public void mostrarInfo(Receta obj) {
        comidaLb.setText(obj.getNombre());
        difTxt.setText(Integer.toString(obj.getDificultad()));
        catTxt.setText(obj.getCategoria().name().replace("_", " "));
        tempTxt.setText(Float.toString(obj.getTiempo_preparacion()));
        int cont=0;
        areaIngredientes.clear();
        for(Ingrediente i:obj.getIngredientes()){
            cont+=1;
            areaIngredientes.appendText(i.toString()+System.getProperty("line.separator"));
        }
        DecimalFormat formato = new DecimalFormat("#.00");
        calTxt.setText(formato.format(obj.calcularTotalCalorias()));
        try {
            imgMain.setImage(new Image(new FileInputStream(obj.getUrlFoto())));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        }
        ingredientesLb.setText("Ingredientes("+Integer.toString(cont)+"):");
        areaInstrucciones.setText(obj.getInstrucciones());
    }
    
    
    public void generarReporte(){
        FileChooser fc = new FileChooser();
        Stage saveStage = new Stage();
        File f = fc.showSaveDialog(saveStage);
        
        
        try(FileWriter fw = new FileWriter(f.getPath())){
            fw.write("Listado detallado de recetas".toUpperCase()+"\n");
            for(Receta r: recetas){
                fw.append("Nombre: "+r.getNombre()+"\n");
                fw.append("Categoria: "+r.getCategoria().toString()+"\n");
                fw.append("Dificultad: "+r.getDificultad()+"\n");
                fw.append("Tiempo de preparación [min]: "+r.getTiempo_preparacion()+"\n");
                fw.append("Ingredientes: \n");
                for(Ingrediente i:r.getIngredientes()){
                    fw.append(">"+i.toString()+"\n");
                }
                fw.append("Instrucciones: \n");
                fw.append(r.getInstrucciones());
                fw.append("\n"+"- - - - - - - - - - - - - - - - - - - - - - - - "+"\n");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(RecetasChoez.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    
    
}
