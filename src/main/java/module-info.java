module org.ibrahimjadaan.spaceinvaders {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    //exports application;
    //opens application to javafx.fxml;
    exports org.ibrahimjadaan.spaceinvaders;
    opens org.ibrahimjadaan.spaceinvaders to javafx.fxml;
}