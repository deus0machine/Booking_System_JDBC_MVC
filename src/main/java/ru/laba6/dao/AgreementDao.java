package ru.laba6.dao;

import ru.laba6.Application;
import ru.laba6.models.Agreement;
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
 * Класс доступа к данным сущности Соглашение
 */
public class AgreementDao implements Dao<Agreement, Integer>{
    static Properties property = new Properties();
    URL url =
            this.getClass()
                    .getResource("/ru/laba6/agreement.properties");

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
    public Agreement findById(Integer integer) {
        return null;
    }
    /**
     * Функция, выполняющая построчное заполнение переменной list данными из датасета соглашений.
     * @param rs ResultSet
     * @return возвращает коллекцию list соглашений.
     */
    protected List<Agreement> mapper (ResultSet rs) {
        List<Agreement> list = new ArrayList<>();
        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                list.add(new Agreement(rs.getInt("ag_id"),
                        rs.getInt("ag_idclient"),
                        rs.getDate("ag_datedoc"),
                        rs.getInt("ag_idhotel"),
                        rs.getInt("ag_idroom"),
                        rs.getDate("ag_resstart"),
                        rs.getDate("ag_resend"),
                        rs.getInt("ag_cost")
                ));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;
    }
    /**
     * Функция, выполняющая запрос к базе данных и заполняющая датасет соглашений
     * @return возвращает коллекцию Соглашений
     */
    @Override
    public Collection<Agreement> findAll() {
        List<Agreement> agreements = null;
        ResultSet rs = null;
        try(PreparedStatement preparedStatement = Application.getConnection().prepareStatement(FIND_ALL)){
            rs = preparedStatement.executeQuery();
            agreements = mapper(rs);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            //TODO выбросить алерт
        }
        return agreements;
    }
    /**
     * Функция, выполняющая сохранение экземпляра Соглашение в базу данных
     * @param entity сущность соглашения
     * @return возвращает экземпляр соглашения
     */
    @Override
    public Agreement save(Agreement entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(SAVE, new String[] {"ag_id"})){
            statement.setString(1, String.valueOf(entity.getClient_id()));
            statement.setString(2, String.valueOf(entity.getDatedoc()));
            statement.setString(3, String.valueOf(entity.getHotel_address()));
            statement.setString(4, String.valueOf(entity.getRoom_address()));
            statement.setString(5, String.valueOf(entity.getReserv_start()));
            statement.setString(6, String.valueOf(entity.getReserv_end()));
            statement.setString(7, String.valueOf(entity.getCost()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                int entityId = rs.getInt("ag_id");
                entity.setId(entityId);
            }
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }
    /**
     * Функция, выполняющая update экземпляра соглашения в базе данных
     * @param entity сущность соглашения
     * @return возвращает сущность соглашения
     */
    @Override
    public Agreement update(Agreement entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(FULL_UPDATE)){
            statement.setString(1, String.valueOf(entity.getId()));
            statement.setString(2, String.valueOf(entity.getClient_id()));
            statement.setString(3, String.valueOf(entity.getDatedoc()));
            statement.setString(4, String.valueOf(entity.getHotel_address()));
            statement.setString(5, String.valueOf(entity.getRoom_address()));
            statement.setString(6, String.valueOf(entity.getReserv_start()));
            statement.setString(7, String.valueOf(entity.getReserv_end()));
            statement.setString(8, String.valueOf(entity.getCost()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }
    /**
     * Функция, выполняющая удаление экземпляра соглашения из базы данных
     * @param entity сущность соглашения
     */
    @Override
    public void delete(Agreement entity) {
        try(PreparedStatement statement = Application.getConnection().prepareStatement(DELETE)){
            statement.setString(1, String.valueOf(entity.getId()));
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Функция, выполняющая удаление экземпляра соглашения из базы данных
     * @param integer id нужного соглашения
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
