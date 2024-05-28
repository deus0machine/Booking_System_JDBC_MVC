package ru.laba6.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import ru.laba6.dao.ClientDao;
import ru.laba6.models.Client;
import ru.laba6.services.ClientService;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientController {


    @FXML
    private TableView<Client> ClientsTable;

    @FXML
    private TableColumn<Client, String> chooseColumn;

    @FXML
    private TableColumn<Client, String> firstnameColumn;

    @FXML
    private TableColumn<Client, Integer> idColumn;

    @FXML
    private TableColumn<Client, String> lastnameColumn;

    @FXML
    private TableColumn<Client, String> midnameColumn;

    @FXML
    private TableColumn<Client, String> numberColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TableColumn<Client, String> seriesColumn;

    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService(new ClientDao());
    }
    ResourceBundle bundle = ResourceBundle.getBundle("text",
            Locale.getDefault());
    @FXML
    protected void initialize(){
        clients.addAll(clientService.findAllClient());
        firstnameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getFirstname()));
        lastnameColumn.setCellValueFactory(item->new SimpleStringProperty(item.getValue().getThirdname()));
        midnameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getSecondname()));
        numberColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getPassportNumber()));
        phoneColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getNumberOfPhone()));
        seriesColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getPassportSeries()));
        chooseColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getFilters()));
        idColumn.setCellValueFactory(item -> new SimpleObjectProperty<>(item.getValue().getId()));
        ClientsTable.setItems(clients);
        ClientsTable.getSortOrder().add(firstnameColumn);
        ClientsTable.getSortOrder().add(midnameColumn);
        ClientsTable.getSortOrder().add(lastnameColumn);

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
        Client client = new Client();
        try {
            showDialog(client);
            clientService.createClient(client);
            ClientsTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        if (clientService.getIsCorrect())
            clients.add(client);
    }
    private void showDialog(Client client) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getResource("/ru/laba6/createOrEditClient.fxml");
        if (location == null) {
            System.err.println("FXML file not found!");
            return;
        }
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Stage addStage = new Stage();
        addStage.setTitle(bundle.getString("edit.client.info"));
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Application.getStartStage());
        Scene scene = new Scene(root);
        addStage.setScene(scene);
        CreateOrEditClientController createOrEditClientController = fxmlLoader.getController();
        createOrEditClientController.setDialogStage(addStage);
        createOrEditClientController.setClient(client);
        addStage.showAndWait();
    }

    @FXML
    void del_click(ActionEvent event) {
        if (ClientsTable.isFocused()) {
            Client student = ClientsTable.getSelectionModel().getSelectedItem();
            clientService.deleteClient(student);
            int index = ClientsTable.getSelectionModel().getSelectedIndex();
            ClientsTable.getItems().remove(index);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Запись удалена");
            alert.showAndWait();
        }
    }

    @FXML
    void edit_click(ActionEvent event) {
        Client client = ClientsTable.getSelectionModel().getSelectedItem();
        if (client != null){
            try {
                showDialog(client);
                clientService.updateClient(client);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void openAgrem_click(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getResource("/ru/laba6/agreement-view.fxml");
        if (location == null) {
            System.err.println("FXML file not found!");
            return;
        }
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Stage addStage = new Stage();
        addStage.setTitle(bundle.getString("title.agreement"));
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Application.getStartStage());
        Scene scene = new Scene(root);
        addStage.setScene(scene);
        addStage.showAndWait();
    }

    @FXML
    void openHotels_click(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getResource("/ru/laba6/hotels-view.fxml");
        if (location == null) {
            System.err.println("FXML file not found!");
            return;
        }
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Stage addStage = new Stage();
        addStage.setTitle(bundle.getString("title.hotel"));
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Application.getStartStage());
        Scene scene = new Scene(root);
        addStage.setScene(scene);
        addStage.showAndWait();
    }

    @FXML
    void openRooms_click(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getResource("/ru/laba6/rooms-view.fxml");
        if (location == null) {
            System.err.println("FXML file not found!");
            return;
        }
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Stage addStage = new Stage();
        addStage.setTitle(bundle.getString("title.room"));
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Application.getStartStage());
        Scene scene = new Scene(root);
        addStage.setScene(scene);
        addStage.showAndWait();

    }

}
