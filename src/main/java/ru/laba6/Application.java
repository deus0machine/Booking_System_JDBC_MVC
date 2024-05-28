package ru.laba6;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import ru.laba6.utils.DBHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class Application extends javafx.application.Application {
    @Getter
    private static Stage startStage;
    @Getter
    private static Connection connection;

    @Override
    public void stop() throws Exception {
        if (connection != null)
            connection.close();
        super.stop();
    }
    @Override
    public void start(Stage stage) throws IOException {
        try {
            connection = DBHelper.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("text",
                Locale.getDefault());
        startStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("first-view.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load(), 920, 640);
        stage.setTitle(bundle.getString("title.client"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}