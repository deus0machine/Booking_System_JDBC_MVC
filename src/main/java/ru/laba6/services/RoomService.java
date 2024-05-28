package ru.laba6.services;

import lombok.Getter;
import ru.laba6.dao.RoomDao;
import ru.laba6.models.Client;
import ru.laba6.models.Room;

import java.util.Collection;
/**
 * Класс - сервис сущности комната.
 */
public class RoomService {
    private final RoomDao roomDao;
    /**
     * Поле проверки корректности полей.
     */
    @Getter
    private Boolean isCorrect = false;
    /**
     * Конструктор сервиса сущности комната.
     * @param roomDao dao комнаты
     */
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }
    /**
     * Функция, возвращающая весь датасет комнат из базы.
     * @return возвращает коллекцию Комнат
     */
    public Collection<Room> findAllRoom(){
        return roomDao.findAll();
    }

    /**
     * Функция, выполняющая update комнаты в базе.
     * @param room экземпляр класса комната
     */
    public void updateRoom(Room room){
        roomDao.update(room);
    }
    /**
     * Функция, выполняющая удаление комнаты из базы.
     * @param room экземпляр класса комната
     */
    public void deleteRoom(Room room){
        roomDao.deleteById(room.getId());
    }
    /**
     * Функция, выполняющая создание клиента и добавление его в базу.
     * @param room экземпляр класса клиент
     */
    public void createRoom(Room room){
        if (room.getAddress_room() != "" && room.getCost() != 0
                && room.getType() != ""
                && room.getStatus() != ""
                && room.getHotel_id() != 0){
            isCorrect = true;
            roomDao.save(room);
        }
        else {
            isCorrect = false;
        }
    }

}
