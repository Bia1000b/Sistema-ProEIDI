module org.example.sistemaproeidi {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.ufrn.imd.sistemaproeidi to javafx.fxml;
    exports br.ufrn.imd.sistemaproeidi;
    exports br.ufrn.imd.sistemaproeidi.controller;
    opens br.ufrn.imd.sistemaproeidi.controller to javafx.fxml;
}