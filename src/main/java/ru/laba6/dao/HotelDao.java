package ru.laba6.dao;

import javafx.scene.control.Alert;
import ru.laba6.Application;
import ru.laba6.models.Client;
import ru.laba6.models.Hotel;

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
 * Класс доступа к данным сущности Отель
 */
public class HotelDao implements Dao<Hotel, Integer>{
    static Properties property = new Properties();
    URL url =
            this.getClass()
                    .getResource("/ru/laba6/hotels.properties");

    FileInputStream fis;

    {
        try {
            fis = new FileInputStream(url.getFile());
            try {
                property.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /** Поле для запроса FIND_ALL*/
    private static String FIND_ALL = null;;
    /** Поле для запроса FULL_UPDATE*/
    private static String FULL_UPDATE = null;
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
    public Hotel findById(Integer integer) {
        return null;
    }
    /**
     * Функция, выполняющая построчное заполнение переменной list данными из датасета отелей.
     * @param rs ResultSet
     * @return возвращает коллекцию list Отелей.
     */
    protected List<Hotel> mapper (ResultSet rs) {
        List<Hotel> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Hotel(
                        rs.getInt("h_id"),
                        rs.getString("h_name"),
                        rs.getString("h_owner"),
                        rs.getString("h_adress"),
                        rs.getInt("h_rating"),
                        rs.getBytes("h_img")  // Используйте getBytes для извлечения всего байтового массива
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    /**
     * Функция, выполняющая запрос к базе данных и заполняющая датасет отелей
     * @return возвращает коллекцию ОТелей
     */
    @Override
    public Collection<Hotel> findAll() {
        List<Hotel> hotels = null;
        ResultSet rs = null;
        try(PreparedStatement preparedStatement = Application.getConnection().prepareStatement(FIND_ALL)){
            rs = preparedStatement.executeQuery();
            hotels = mapper(rs);
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        return hotels;
    }
    /**
     * Функция, выполняющая сохранение экземпляра Отель в базу данных
     * @param entity сущность отеля
     * @return возвращает экземпляр отеля
     */
    @Override
    public Hotel save(Hotel entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(SAVE, new String[] {"h_id"})){
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getOwner());
            statement.setString(3, entity.getAdress());
            statement.setInt(4, entity.getRating());
            statement.setBytes(5, entity.getImg());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                int entityId = rs.getInt("h_id");
                entity.setId(entityId);
            }
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }
    /**
     * Функция, выполняющая update экземпляра отеля в базе данных
     * @param entity сущность отеля
     * @return возвращает сущность отеля
     */
    @Override
    public Hotel update(Hotel entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(FULL_UPDATE)){
            statement.setString(1, String.valueOf(entity.getId()));
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getOwner());
            statement.setString(4, entity.getAdress());
            statement.setInt(5, entity.getRating());
            statement.setBytes(6, entity.getImg());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }
    /**
     * Функция, выполняющая удаление экземпляра отеля из базы данных
     * @param entity сущность отеля
     */
    @Override
    public void delete(Hotel entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(DELETE)){
            statement.setString(1, String.valueOf(entity.getId()));
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Функция, выполняющая удаление экземпляра отеля из базы данных
     * @param integer id нужного отеля
     */
    @Override
    public void deleteById(Integer integer) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(DELETE)){
            statement.setString(1, String.valueOf(integer));
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
