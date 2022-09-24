module com.example.calculadoracliente {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.calculadoracliente to javafx.fxml;
    exports com.example.calculadoracliente;
    exports paquete;
    opens paquete to javafx.fxml;
}