package ru.laba6.dao;

import ru.laba6.Application;
import ru.laba6.models.Client;
import ru.laba6.models.Hotel;
import ru.laba6.models.Room;

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
 * Класс доступа к данным сущности Номер
 */
public class RoomDao implements Dao<Room, Integer>{
    static Properties property = new Properties();
    URL url =
            this.getClass()
                    .getResource("/ru/laba6/rooms.properties");

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
    public Room findById(Integer integer) {
        return null;
    }
    /**
     * Функция, выполняющая построчное заполнение переменной list данными из датасета номеров.
     * @param rs ResultSet
     * @return возвращает коллекцию list Номеров.
     */
    protected List<Room> mapper (ResultSet rs) {
        List<Room> list = new ArrayList<>();
        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                list.add(new Room(rs.getInt("r_id"),
                        rs.getInt("r_idhotel"),
                        rs.getString("r_adress"),
                        rs.getInt("r_cost"),
                        rs.getString("r_type"),
                        rs.getString("r_status")
                ));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;
    }
    /**
     * Функция, выполняющая запрос к базе данных и заполняющая датасет номеров
     * @return возвращает коллекцию Номеров
     */
    @Override
    public Collection<Room> findAll() {
        List<Room> rooms = null;
        ResultSet rs = null;
        try(PreparedStatement preparedStatement = Application.getConnection().prepareStatement(FIND_ALL)){
            rs = preparedStatement.executeQuery();
            rooms = mapper(rs);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            //TODO выбросить алерт
        }
        return rooms;
    }
    /**
     * Функция, выполняющая сохранение экземпляра Номер в базу данных
     * @param entity сущность номера
     * @return возвращает экземпляр номера
     */
    @Override
    public Room save(Room entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(SAVE, new String[] {"r_id"})){
            statement.setString(1, entity.getStatus());
            statement.setString(2, entity.getType());
            statement.setString(3, String.valueOf(entity.getCost()));
            statement.setString(4, entity.getAddress_room());
            statement.setInt(5, entity.getHotel_id());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                // Получаем поле student_id
                int entityId = rs.getInt("r_id");
                entity.setId(entityId);
            }
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }
    /**
     * Функция, выполняющая update экземпляра номера в базе данных
     * @param entity сущность номера
     * @return возвращает сущность номера
     */
    @Override
    public Room update(Room entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(FULL_UPDATE)){
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getStatus());
            statement.setString(3, entity.getType());
            statement.setString(4, String.valueOf(entity.getCost()));
            statement.setString(5, entity.getAddress_room());
            statement.setInt(6, entity.getHotel_id());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }
    /**
     * Функция, выполняющая удаление экземпляра номера из базы данных
     * @param entity сущность номера
     */
    @Override
    public void delete(Room entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(DELETE)){
            statement.setString(1, String.valueOf(entity.getId()));
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Функция, выполняющая удаление экземпляра номера из базы данных
     * @param integer id нужного номера
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
