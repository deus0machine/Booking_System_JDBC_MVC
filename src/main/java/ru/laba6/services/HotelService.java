package ru.laba6.services;

import lombok.Getter;
import ru.laba6.dao.HotelDao;
import ru.laba6.models.Client;
import ru.laba6.models.Hotel;

import java.util.Collection;

/**
 * Класс - сервис сущности Отель.
 */
public class HotelService {
    private final HotelDao hotelDao;
    /**
     * Поле проверки корректности полей.
     */
    @Getter
    private Boolean isCorrect = false;
    /**
     * Конструктор сервиса сущности клиент.
     * @param hotelDao dao отеля
     */
    public HotelService(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }
    /**
     * Функция, возвращающая весь датасет отелей из базы.
     * @return возвращает коллекцию Отелей
     */
    public Collection<Hotel> findAllHotel(){
        return hotelDao.findAll();
    }
    /**
     * Функция, выполняющая update отель в базе.
     * @param hotel экземпляр класса отель
     */
    public void updateHotel(Hotel hotel){
        hotelDao.update(hotel);
    }
    /**
     * Функция, выполняющая удаление отеля из базы.
     * @param hotel экземпляр класса отель
     */
    public void deleteHotel(Hotel hotel){
        hotelDao.deleteById(hotel.getId());
    }
    /**
     * Функция, выполняющая создание отеля и добавление его в базу.
     * @param hotel экземпляр класса отель
     */
    public void createHotel(Hotel hotel){
        if (hotel.getRating() != 0 && !hotel.getAdress().isEmpty()
                && !hotel.getName().isEmpty()
                && !hotel.getOwner().isEmpty()){
            isCorrect = true;
            hotelDao.save(hotel);
        }
        else {
            isCorrect = false;
        }
    }
}
