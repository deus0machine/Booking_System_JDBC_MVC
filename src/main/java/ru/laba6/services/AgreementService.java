package ru.laba6.services;

import lombok.Getter;
import ru.laba6.dao.AgreementDao;
import ru.laba6.models.Agreement;
import ru.laba6.models.Client;

import java.util.Collection;
/**
 * Класс - сервис сущности соглашение.
 */
public class AgreementService {
    private final AgreementDao agreementDao;
    /**
     * Поле проверки корректности полей.
     */
    @Getter
    private Boolean isCorrect = false;
    /**
     * Конструктор сервиса сущности клиент.
     * @param agreementDao dao соглашения
     */
    public AgreementService(AgreementDao agreementDao) {
        this.agreementDao = agreementDao;
    }
    /**
     * Функция, возвращающая весь датасет соглашений из базы.
     * @return возвращает коллекцию Соглашений
     */
    public Collection<Agreement> findAllAgreement(){
        return agreementDao.findAll();
    }
    /**
     * Функция, выполняющая update клиента в базе.
     * @param agreement экземпляр класса клиент
     */
    public void updateAgreement(Agreement agreement){
        agreementDao.update(agreement);
    }
    /**
     * Функция, выполняющая удаление клиента из базы.
     * @param agreement экземпляр класса клиент
     */
    public void deleteAgreement(Agreement agreement){
        agreementDao.deleteById(agreement.getId());
    }
    /**
     * Функция, выполняющая создание клиента и добавление его в базу.
     * @param agreement экземпляр класса клиент
     */
    public void createAgreement(Agreement agreement){
        if (agreement.getClient_id() != 0 && agreement.getReserv_end() != null
                && agreement.getReserv_start() != null
                && agreement.getHotel_address() != 0
                && agreement.getRoom_address() != 0){
            isCorrect = true;
            agreementDao.save(agreement);
        }
        else {
            isCorrect = false;
        }
    }
}
