package ru.laba6.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Клиент со свойствами <b>secondname</b>, <b>firstname</b>, <b>thirdname</b>, <b>numberofphone</b>, <b>filters</b>, <b>passportseries</b> и <b>passportnumber</b> .
 * <p>
 * Данный класс позволяет описать экземпляр клиента с заданным именем, фамилией, отчеством, номером телефона и данными пасспорта.
 * В дальнейшем номер телефона может быть изменён. При этом имя остается постоянным
 на все время существования экземпляра.
 * @author Автор Севостьянов
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class Client {
    private int id;
    /** Поле фамилии */
    private String secondname;
    /** Поле имени */
    private String firstname;
    /** Поле отчества */
    private String thirdname;
    /** Поле номера телефона */
    private String numberOfPhone;
    /** Поле фильтров */
    private String filters;
    /** Поле серии паспорта */
    private String passportSeries;
    /** Поле номера паспорта */
    private String passportNumber;

    /**
     * Конструктор – создание нового экземпляра с заданными параметрами
     * @param id
     * @param secondname
     * @param firstname
     * @param thirdname
     * @param numberOfPhone
     * @param filters
     * @param passportSeries
     * @param passportNumber
     * @see Client(String)
     */
    public Client(int id, String secondname, String firstname, String thirdname, String numberOfPhone, String filters, String passportSeries, String passportNumber) {
        this.id = id;
        this.secondname = secondname;
        this.firstname = firstname;
        this.thirdname = thirdname;
        this.numberOfPhone = numberOfPhone;
        this.filters = filters;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }
}
