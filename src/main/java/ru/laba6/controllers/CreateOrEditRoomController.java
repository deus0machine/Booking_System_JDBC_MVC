package ru.laba6.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import ru.laba6.models.Room;

public class CreateOrEditRoomController {

    @FXML
    private TextField adres_r;

    @FXML
    private TextField cost_r;

    @FXML
    private TextField idhotel_r;

    @FXML
    private TextField status_r;

    @FXML
    private TextField type_r;
    private Room room;
    @Setter
    private Stage dialogStage;
    @FXML
    void cancel_click(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void ok_click(ActionEvent event) {
        room.setAddress_room(adres_r.getText());
        room.setCost(Integer.parseInt(cost_r.getText()));
        room.setType(type_r.getText());
        room.setStatus(status_r.getText());
        room.setHotel_id(Integer.parseInt(idhotel_r.getText()));
        dialogStage.close();
    }
    public void setRoom(Room room){
        this.room = room;
        adres_r.setText(room.getAddress_room());
        cost_r.setText(String.valueOf(room.getCost()));
        type_r.setText(room.getType());
        status_r.setText(room.getStatus());
        idhotel_r.setText(String.valueOf(room.getHotel_id()));
    }

}
