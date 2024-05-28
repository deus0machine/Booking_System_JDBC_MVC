module ru.laba6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    opens ru.laba6 to javafx.fxml;
    exports ru.laba6;
    exports ru.laba6.controllers;
    opens ru.laba6.controllers to javafx.fxml;
}