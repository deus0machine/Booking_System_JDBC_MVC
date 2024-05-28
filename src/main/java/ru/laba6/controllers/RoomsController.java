package ru.laba6.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.laba6.Application;
import ru.laba6.dao.RoomDao;
import ru.laba6.models.Client;
import ru.laba6.models.Hotel;
import ru.laba6.models.Room;
import ru.laba6.services.RoomService;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class RoomsController {

    @FXML
    private TableColumn<Room, String> adressColumn;

    @FXML
    private TableColumn<Room, Integer> costColumn;

    @FXML
    private TableColumn<Room, Integer> hotelColumn;

    @FXML
    private TableColumn<Room, Integer> idColumn;

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, String> statusColumn;

    @FXML
    private TableColumn<Room, String> typeColumn;

    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    private RoomService roomService;

    public RoomsController() {
        this.roomService = new RoomService(new RoomDao());
    }
    @FXML
    protected void initialize(){
        rooms.addAll(roomService.findAllRoom());
        adressColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getAddress_room()));
        costColumn.setCellValueFactory(item->new SimpleObjectProperty<>(item.getValue().getCost()));
        hotelColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getHotel_id()));
        statusColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getStatus()));
        typeColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getType()));
        idColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getId()));
        roomsTable.setItems(rooms);
        roomsTable.getSortOrder().add(hotelColumn);


        /*ClientsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observableValue, Client student, Client t1) {
                if (t1 != null)
                    label1.setText(t1.toString());
            }
        });*/
    }
    ResourceBundle bundle = ResourceBundle.getBundle("room_eng",
            Locale.getDefault());
    @FXML
    void add_click(ActionEvent event) {
        Room room = new Room();
        try {
            showDialog(room);
            roomService.createRoom(room);
            roomsTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        if (roomService.getIsCorrect())
            rooms.add(room);
    }
    private void showDialog(Room room) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getResource("/ru/laba6/createOrEditRoom.fxml");
        if (location == null) {
            System.err.println("FXML file not found!");
            return;
        }
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Stage addStage = new Stage();
        addStage.setTitle(bundle.getString("edit.room.info"));
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Application.getStartStage());
        Scene scene = new Scene(root);
        addStage.setScene(scene);
        CreateOrEditRoomController createOrEditRoomController = fxmlLoader.getController();
        createOrEditRoomController.setDialogStage(addStage);
        createOrEditRoomController.setRoom(room);
        addStage.showAndWait();
    }

    @FXML
    void del_click(ActionEvent event) {
        if (roomsTable.isFocused()) {
            Room room = roomsTable.getSelectionModel().getSelectedItem();
            roomService.deleteRoom(room);
            int index = roomsTable.getSelectionModel().getSelectedIndex();
            roomsTable.getItems().remove(index);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Запись удалена");
            alert.showAndWait();
        }
    }

    @FXML
    void edit_click(ActionEvent event) {
        Room room = roomsTable.getSelectionModel().getSelectedItem();
        if (room != null){
            try {
                showDialog(room);
                roomService.updateRoom(room);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

}
