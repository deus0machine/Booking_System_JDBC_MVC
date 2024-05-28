package ru.laba6.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Номер со свойствами <b>hotel_id</b>, <b>address_room</b>, <b>cost</b>, <b>type</b>, <b>status</b> .
 * <p>
 * Данный класс позволяет описать экземпляр номера с заданным id отеля, адресом комнаты, посуточной ценой, типом и статусом.
 * @author Автор Севостьянов
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class Room {
    private int id;
    /** Поле id отеля */
    private int hotel_id;
    /** Поле адреса комнаты */
    private String address_room;
    /** Поле посуточной цены */
    private int cost;
    /** Поле тип номера */
    private String type;
    /** Поле статуса номера */
    private String status;

    /**
     * Конструктор – создание нового экземпляра с заданными параметрами
     * @param id
     * @param hotel_id
     * @param address_room
     * @param cost
     * @param type
     * @param status
     * @see Room(String)
     */
    public Room(int id, int hotel_id, String address_room, int cost, String type, String status) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.address_room = address_room;
        this.cost = cost;
        this.type = type;
        this.status = status;
    }
}
