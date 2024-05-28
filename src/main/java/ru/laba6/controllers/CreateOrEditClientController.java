package ru.laba6.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import ru.laba6.models.Client;

public class CreateOrEditClientController {

    @FXML
    private TextField firstname_cl;

    @FXML
    private TextField num_cl;

    @FXML
    private TextField phone_cl;

    @FXML
    private TextField series_cl;

    @FXML
    private TextField sur_cl;

    @FXML
    private TextField thir_cl;
    private Client client;
    @Setter
    private Stage dialogStage;

    @FXML
    void cancel_click(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void ok_click(ActionEvent event) {
        client.setFirstname(firstname_cl.getText());
        client.setSecondname(sur_cl.getText());
        client.setNumberOfPhone(phone_cl.getText());
        client.setPassportNumber(num_cl.getText());
        client.setPassportSeries(series_cl.getText());
        client.setThirdname(thir_cl.getText());
        dialogStage.close();
    }
    public void setClient(Client client){
        this.client = client;
        firstname_cl.setText(client.getFirstname());
        sur_cl.setText(client.getSecondname());
        thir_cl.setText(client.getThirdname());
        num_cl.setText(client.getPassportNumber());
        phone_cl.setText(client.getNumberOfPhone());
        series_cl.setText(client.getPassportSeries());
    }

}
