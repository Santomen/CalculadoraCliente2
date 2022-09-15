package com.example.calculadoracliente;

import com.example.calculadoracliente.Paquete;
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

import static java.lang.Integer.parseInt;

public class HelloController implements Runnable{

    @FXML
    private VBox VB;

    @FXML
    private ScrollPane sc;

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
        Socket enviaReceptor=new Socket("127.0.0.1",14502);
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
        try{
            ServerSocket servidor=new ServerSocket(14002);
            //ahora que acepte cualquier conexion que venga del exterior con el metodo accept

            while(ban){
                Socket misocket=servidor.accept();//aceptara las conexiones que vengan del exterior
                ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
                Paquete mensaje=(Paquete)flujo_entrada.readObject();
                if(mensaje.getCodigo()==1){
                    paquete.setCadena(mensaje.getCadena());
                    //CONTENIDO CHAT
                    //System.out.println(mensaje);
                    pantalla=new Label(mensaje.getCadena());
                    Platform.runLater(()->{
                        VB.getChildren().add(pantalla);
                    });
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

}
