package ru.laba6.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Отель со свойствами <b>name</b>, <b>owner</b>, <b>adress</b>, <b>rating</b>, <b>img</b> .
 * <p>
 * Данный класс позволяет описать экземпляр отеля с заданным названием, адресом отеля, именем владельца, рейтингом и изображением.
 * @author Автор Севостьянов
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class Hotel {
    private int id;
    /** Поле названия */
    private String name;
    /** Поле фио владельца*/
    private String owner;
    /** Поле адреса */
    private String adress;
    /** Поле рейтинга */
    private int rating;
    /** Поле изображнеия */
    private byte[] img;

    /**
     * Конструктор – создание нового экземпляра с заданными параметрами
     * @param id
     * @param name
     * @param owner
     * @param adress
     * @param rating
     * @param img
     * @see Hotel(String)
     */
    public Hotel(int id, String name, String owner, String adress, int rating, byte[] img) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.adress = adress;
        this.rating = rating;
        this.img = img;
    }
}
