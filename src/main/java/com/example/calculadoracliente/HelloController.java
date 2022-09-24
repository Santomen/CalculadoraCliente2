package com.example.calculadoracliente;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import paquete.Paquete;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class HelloController implements Runnable{
    @FXML
    private Label lblCalcuN;
    @FXML
    private VBox VB;

    @FXML
    private ScrollPane sc;
    int nodo=0;

    List<String> resultados=new ArrayList<>();
    private Paquete paquete=new Paquete();
    void actualizar_pantalla(){
        pantalla.setText(paquete.getCadena());
    }
    @FXML
    void borrar(ActionEvent event) {
        paquete.setCadena("");
        actualizar_pantalla();
    }

    @FXML
    void dividir(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"/");
        actualizar_pantalla();
    }

    @FXML
    void mandar_server(ActionEvent event) throws IOException {
        Socket enviaReceptor=new Socket("127.0.0.1",nodo);
        ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaReceptor.getOutputStream());
        paquete.setCodigo(0);
        paqueteReenvio.writeObject(paquete);
        paqueteReenvio.close();
        enviaReceptor.close();
       
    }

    @FXML
    void multiplicar(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"*");
        actualizar_pantalla();
    }

    @FXML
    void restar(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"-");
        actualizar_pantalla();
    }

    @FXML
    void setCero(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"0");
        actualizar_pantalla();
    }

    @FXML
    void setCinco(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"5");
        actualizar_pantalla();
    }

    @FXML
    void setCuatro(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"4");
        actualizar_pantalla();
    }

    @FXML
    void setDos(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"2");
        actualizar_pantalla();
    }

    @FXML
    void setNueve(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"9");
        actualizar_pantalla();
    }

    @FXML
    void setOcho(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"8");
        actualizar_pantalla();
    }

    @FXML
    void setPunto(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+".");
        actualizar_pantalla();
    }

    @FXML
    void setSeis(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"6");
        actualizar_pantalla();
    }

    @FXML
    void setSiete(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"7");
        actualizar_pantalla();
    }

    @FXML
    void setTres(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"3");
        actualizar_pantalla();
    }

    @FXML
    void setUno(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"1");
        actualizar_pantalla();
    }

    @FXML
    void sumar(ActionEvent event) {
        paquete.setCadena(paquete.getCadena()+"+");
        actualizar_pantalla();
    }

    Label pantalla=new Label();
    public void initialize() {
         VB.getChildren().add(pantalla);
        Thread hilo1=new Thread(this);
        hilo1.start();
    }
    public void run(){
        //Server socket pondra a la app a la escucha de un puerto
        boolean ban=true;
        int puertos_clientes[]={14000,14001,14002,14003,14004,14005,14006};
        ServerSocket servidor = null;

        int puerto_celula=0;
        for(int puerto:puertos_clientes){
            try{servidor=new ServerSocket(puerto);
                puerto_celula=puerto;
                nodo=puerto+1000;
                Platform.runLater(()->{
                    lblCalcuN.setText("Calculadora: "+String.valueOf(puerto));
                });
                break;
            }
            catch(IOException e){
                System.out.println(e);

            }
        }

        try{
            //ahora que acepte cualquier conexion que venga del exterior con el metodo accept
            while(ban){
                Socket misocket=servidor.accept();//aceptara las conexiones que vengan del exterior
                ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
                Paquete mensaje=(Paquete)flujo_entrada.readObject();
                if(mensaje.getCodigo()==1){
                    paquete.setCadena(mensaje.getCadena());
                    //CONTENIDO CHAT
                    //System.out.println(mensaje);

                    //System.out.println("memoria pantalla: "+pantalla+" memoria VB "+VB);
                    Platform.runLater(()->{
                        pantalla.setText(mensaje.getCadena());
                        //VB.getChildren().add(new Label(mensaje.getCadena()));
                    });
                    resultados.add(mensaje.getCadena());
                    VB.heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            sc.setVvalue((Double)t1);
                        }
                    });
                }
                else{
                    System.out.println("LLego un mensaje que el cliente no puede procesar");
                }
                flujo_entrada.close();
                misocket.close();
            }

        }
        catch(IOException e){

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void historial(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HistorialView.fxml"));
        Parent root =fxmlLoader.load();
        HistorialController historialController=fxmlLoader.getController();
        Scene scene=new Scene(root);
        Stage stage1=new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setScene(scene);
        historialController.cargarResultados(resultados);
        stage1.showAndWait();*/
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HistorialView.fxml"));
        Parent root =fxmlLoader.load();
        Scene scene=new Scene(root);
        Stage stage1=new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setScene(scene);
        HistorialController historialController=fxmlLoader.getController();
        System.out.println(historialController);
        historialController.cargarResultados(resultados);
        stage1.showAndWait();

    }
}
