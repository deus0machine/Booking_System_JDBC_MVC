package ru.laba6.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import ru.laba6.models.Client;
import ru.laba6.models.Hotel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class CreateOrEditHotelController {

    @FXML
    private TextField adress_h;

    @FXML
    private TextField name_h;

    @FXML
    private TextField owner_h;

    @FXML
    private TextField rating_h;

    @FXML
    private TextField img_h;
    @FXML
    private ImageView imgView;
    private Hotel hotel;
    @Setter
    private Stage dialogStage;

    @FXML
    void cancel_click(ActionEvent event) {
        dialogStage.close();
    }
    byte[] imageData;
    @FXML
    void load_click(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp")
        );

        // Предполагается, что у вас есть доступ к Stage
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                imageData = Files.readAllBytes(selectedFile.toPath());
                Image image = new Image(new ByteArrayInputStream(imageData));
                imgView.setImage(image);
            } catch (IOException e) {
                // Обработка ошибок при чтении файла
                e.printStackTrace();
            }
        }
    }

    @FXML
    void ok_click(ActionEvent event) {
        hotel.setAdress(adress_h.getText());
        hotel.setName(name_h.getText());
        hotel.setOwner(owner_h.getText());
        hotel.setRating(Integer.parseInt(rating_h.getText()));
        hotel.setImg(imageData);
        dialogStage.close();
    }
    public void setHotel(Hotel hotel){
        this.hotel = hotel;
        adress_h.setText(hotel.getAdress());
        name_h.setText(hotel.getName());
        owner_h.setText(hotel.getOwner());
        rating_h.setText(String.valueOf(hotel.getRating()));
        img_h.setText(Arrays.toString(hotel.getImg()));
        try{
            imgView.setImage(new Image(new ByteArrayInputStream(hotel.getImg())));
        }
            catch (Exception e){
                e.printStackTrace();
            }
    }

}
