package ru.laba6.dao;

import javafx.scene.control.Alert;
import ru.laba6.Application;
import ru.laba6.models.Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Класс доступа к данным сущности Клиент
 */
public class ClientDao implements Dao <Client, Integer>{
    static Properties property = new Properties();
    URL url =
            this.getClass()
                    .getResource("/ru/laba6/client.properties");

    FileInputStream fis;
    /** Поле для запроса FIND_ALL*/
    private static String FIND_ALL = null;
    /** Поле для запроса FULL_UPDATE*/
    private static String FULL_UPDATE = null;
    /** Поле для запроса SHORT_UPDATE*/
    private static String SHORT_UPDATE = null;
    /** Поле для запроса SAVE*/
    private static String SAVE = null;
    /** Поле для запроса DELETE */
    private static String DELETE = null;


    {
        try {
            fis = new FileInputStream(url.getFile());
            try {
                property.load(fis);
                FIND_ALL = property.getProperty("sql.find_all");
                FULL_UPDATE = property.getProperty("sql.full_update");
                SHORT_UPDATE = property.getProperty("sql.short_update");
                SAVE = property.getProperty("sql.insert");
                DELETE = property.getProperty("sql.delete");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Client findById(Integer integer) {
        return null;
    }

    /**
     * Функция, выполняющая построчное заполнение переменной list данными из датасета клиентов.
     * @param rs ResultSet
     * @return возвращает коллекцию list Клиентов.
     */
    protected List<Client> mapper (ResultSet rs) {
        List<Client> list = new ArrayList<>();
        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                list.add(new Client(rs.getInt("cl_id"),
                        rs.getString("cl_firstname"),
                        rs.getString("cl_midname"),
                        rs.getString("cl_lastname"),
                        rs.getString("cl_phonenumber"),
                        rs.getString("cl_idchoose"),
                        rs.getString("cl_passportseries"),
                        rs.getString("cl_passportnumber")
                ));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;
    }

    /**
     * Функция, выполняющая запрос к базе данных и заполняющая датасет клиентов
     * @return возвращает коллекцию Клиентов
     */
    @Override
    public Collection<Client> findAll() {
        List<Client> clients = null;
        ResultSet rs = null;
        try(PreparedStatement preparedStatement = Application.getConnection().prepareStatement(FIND_ALL)){
            rs = preparedStatement.executeQuery();
            clients = mapper(rs);
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        return clients;
    }

    /**
     * Функция, выполняющая сохранение экземпляра Клиент в базу данных
     * @param entity сущность клиента
     * @return возвращает экземпляр клиента
     */
    @Override
    public Client save(Client entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(SAVE, new String[] {"cl_id"})){
            statement.setString(1, entity.getFirstname());
            statement.setString(2, entity.getSecondname());
            statement.setString(3, entity.getThirdname());
            statement.setString(4, entity.getNumberOfPhone());
            statement.setString(5, entity.getFilters());
            statement.setString(6, entity.getPassportSeries());
            statement.setString(7, entity.getPassportNumber());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                // Получаем поле student_id
                int clientId = rs.getInt("cl_id");
                entity.setId(clientId);
            }
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }

    /**
     * Функция, выполняющая update экземпляра клиента в базе данных
     * @param entity сущность клиента
     * @return возвращает сущность клиента
     */
    @Override
    public Client update(Client entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(FULL_UPDATE)){
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getFirstname());
            statement.setString(3, entity.getSecondname());
            statement.setString(4, entity.getThirdname());
            statement.setString(5, entity.getNumberOfPhone());
            statement.setString(6, entity.getFilters());
            statement.setString(7, entity.getPassportSeries());
            statement.setString(8, entity.getPassportNumber());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }

    /**
     * Функция, выполняющая удаление экземпляра клиента из базы данных
     * @param entity сущность клиента
     */
    @Override
    public void delete(Client entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(DELETE)){
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Функция, выполняющая удаление экземпляра клиента из базы данных
     * @param integer id нужного клиента
     */
    @Override
    public void deleteById(Integer integer) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(DELETE)){
            statement.setInt(1, integer);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
