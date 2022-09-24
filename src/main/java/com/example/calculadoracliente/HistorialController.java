package com.example.calculadoracliente;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class HistorialController {
    @FXML
    private VBox VBH;

    void cargarResultados(List<String> resultados){
        for(String resultado:resultados){
           VBH.getChildren().add(new Label(resultado));
        }
    }

}
