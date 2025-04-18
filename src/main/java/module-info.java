module com.olim.bankaccountmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires eu.hansolo.tilesfx;
        
    opens com.olim.bankaccountmanagementsystem to javafx.fxml;
    opens com.olim.bankaccountmanagementsystem.controller to javafx.fxml;
    exports com.olim.bankaccountmanagementsystem;
    exports com.olim.bankaccountmanagementsystem.controller;
}