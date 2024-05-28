package ru.laba6.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * Класс Соглашение со свойствами <b>client_id</b>, <b>datedoc</b>, <b>hotel_address</b>, <b>room_address</b>, <b>reserv_start</b>, <b>reserv_end</b> и <b>cost</b> .
 * <p>
 * Данный класс позволяет описать экземпляр соглашения с заданным id клиента, адресом отеля, адресом номера, датой начала брони и датой окончания брони.
 * @author Автор Севостьянов
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class Agreement {
    private int id;
    /** Поле id клиента */
    private int client_id;
    /** Поле даты составляения */
    private Date datedoc;
    /** Поле адреса отеля */
    private int hotel_address;
    /** Поле адреса номера */
    private int room_address;
    /** Поле даты начала брони */
    private Date reserv_start;
    /** Поле даты конца брони */
    private Date reserv_end;
    /** Поле стоимости */
    private int cost;

    /**
     * Конструктор – создание нового экземпляра с заданными параметрами
     * @param id
     * @param client_id
     * @param datedoc
     * @param hotel_address
     * @param room_address
     * @param reserv_start
     * @param reserv_end
     * @param cost
     * @see Agreement(String)
     */
    public Agreement(int id, int client_id, Date datedoc, int hotel_address, int room_address, Date reserv_start, Date reserv_end, int cost) {
        this.id = id;
        this.client_id = client_id;
        this.datedoc = datedoc;
        this.hotel_address = hotel_address;
        this.room_address = room_address;
        this.reserv_start = reserv_start;
        this.reserv_end = reserv_end;
        this.cost = cost;
    }
}
