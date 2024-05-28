package ru.laba6.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.laba6.dao.AgreementDao;
import ru.laba6.dao.RoomDao;
import ru.laba6.models.Agreement;
import ru.laba6.models.Room;
import ru.laba6.services.AgreementService;
import ru.laba6.services.RoomService;

import java.util.Date;

public class AgreementController {

    @FXML
    private TableView<Agreement> agreementTable;

    @FXML
    private TableColumn<Agreement, Integer> clientColumn;

    @FXML
    private TableColumn<Agreement, Integer> costColumn;

    @FXML
    private TableColumn<Agreement, Date> datedocColumn;

    @FXML
    private TableColumn<Agreement, Date> endColumn;

    @FXML
    private TableColumn<Agreement, Integer> hotelColumn;

    @FXML
    private TableColumn<Agreement, Integer> idColumn;

    @FXML
    private TableColumn<Agreement, Integer> roomColumn;

    @FXML
    private TableColumn<Agreement, Date> startColumn;
    private ObservableList<Agreement> agreements = FXCollections.observableArrayList();
    private AgreementService agreementService;

    public AgreementController() {
        this.agreementService = new AgreementService(new AgreementDao());
    }
    @FXML
    protected void initialize(){
        agreements.addAll(agreementService.findAllAgreement());
        clientColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getClient_id()));
        costColumn.setCellValueFactory(item->new SimpleObjectProperty<>(item.getValue().getCost()));
        hotelColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getHotel_address()));
        datedocColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getDatedoc()));
        endColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getReserv_end()));
        startColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getReserv_start()));
        idColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getId()));
        roomColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getRoom_address()));
        agreementTable.setItems(agreements);
        agreementTable.getSortOrder().add(datedocColumn);


        /*ClientsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observableValue, Client student, Client t1) {
                if (t1 != null)
                    label1.setText(t1.toString());
            }
        });*/
    }

    @FXML
    void add_click(ActionEvent event) {

    }

    @FXML
    void del_click(ActionEvent event) {
        if (agreementTable.isFocused()) {
            Agreement agreement = agreementTable.getSelectionModel().getSelectedItem();
            agreementService.deleteAgreement(agreement);
            int index = agreementTable.getSelectionModel().getSelectedIndex();
            agreementTable.getItems().remove(index);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Запись удалена");
            alert.showAndWait();
        }
    }

    @FXML
    void edit_click(ActionEvent event) {

    }

}
