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
import ru.laba6.dao.HotelDao;
import ru.laba6.models.Hotel;
import ru.laba6.services.HotelService;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class HotelsController {

    @FXML
    private TableColumn<Hotel, String> addressColumn;

    @FXML
    private TableView<Hotel> hotelsTable;

    @FXML
    private TableColumn<Hotel, Integer> idColumn;

    @FXML
    private TableColumn<Hotel, byte[]> imgColumn;

    @FXML
    private TableColumn<Hotel, String> nameColumn;

    @FXML
    private TableColumn<Hotel, String> ownerColumn;

    @FXML
    private TableColumn<Hotel, Integer> ratingColumn;
    private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private HotelService hotelService;
    public HotelsController() {
        this.hotelService = new HotelService(new HotelDao());
    }
    ResourceBundle bundle = ResourceBundle.getBundle("hotel_eng",
            Locale.getDefault());

    @FXML
    protected void initialize(){
        hotels.addAll(hotelService.findAllHotel());
        nameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        ownerColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getOwner()));
        ratingColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getRating()));
        imgColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getImg()));
        addressColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getAdress()));

        idColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getId()));
        hotelsTable.setItems(hotels);
        hotelsTable.getSortOrder().add(nameColumn);
        hotelsTable.getSortOrder().add(ownerColumn);

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
        Hotel hotel = new Hotel();
        try {
            showDialog(hotel);
            hotelService.createHotel(hotel);
            hotelsTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        if (hotelService.getIsCorrect())
            hotels.add(hotel);
    }

    @FXML
    void del_click(ActionEvent event) {
        if (hotelsTable.isFocused()) {
            Hotel hotel = hotelsTable.getSelectionModel().getSelectedItem();
            hotelService.deleteHotel(hotel);
            int index = hotelsTable.getSelectionModel().getSelectedIndex();
            hotelsTable.getItems().remove(index);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Запись удалена");
            alert.showAndWait();
        }
    }

    @FXML
    void edit_click(ActionEvent event) {
        Hotel hotel = hotelsTable.getSelectionModel().getSelectedItem();
        if (hotel != null){
            try {
                showDialog(hotel);
                hotelService.updateHotel(hotel);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }
    private void showDialog(Hotel hotel) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getResource("/ru/laba6/createOrEditHotel.fxml");
        if (location == null) {
            System.err.println("FXML file not found!");
            return;
        }
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Stage addStage = new Stage();
        addStage.setTitle(bundle.getString("edit.hotel.info"));
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Application.getStartStage());
        Scene scene = new Scene(root);
        addStage.setScene(scene);
        CreateOrEditHotelController createOrEditHotelController = fxmlLoader.getController();
        createOrEditHotelController.setDialogStage(addStage);
        createOrEditHotelController.setHotel(hotel);
        addStage.showAndWait();
    }

}
