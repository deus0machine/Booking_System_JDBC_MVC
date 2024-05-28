package ru.laba6.services;

import lombok.Getter;
import ru.laba6.dao.ClientDao;
import ru.laba6.models.Client;

import java.util.Collection;

/**
 * Класс - сервис сущности клиент.
 */
public class ClientService {
    private final ClientDao clientDao;
    /**
     * Поле проверки корректности полей.
     */
    @Getter
    private Boolean isCorrect = false;

    /**
     * Конструктор сервиса сущности клиент.
     * @param clientDao dao клиента
     */
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Функция, возвращающая весь датасет клиентов из базы.
     * @return возвращает коллекцию Клиентов
     */
    public Collection<Client> findAllClient(){
        return clientDao.findAll();
    }

    /**
     * Функция, выполняющая update клиента в базе.
     * @param client экземпляр класса клиент
     */
    public void updateClient(Client client){
        clientDao.update(client);
    }

    /**
     * Функция, выполняющая удаление клиента из базы.
     * @param client экземпляр класса клиент
     */
    public void deleteClient(Client client){
        clientDao.deleteById(client.getId());
    }

    /**
     * Функция, выполняющая создание клиента и добавление его в базу.
     * @param client экземпляр класса клиент
     */
    public void createClient(Client client){
        if (!client.getFirstname().isEmpty() && !client.getSecondname().isEmpty()
                && !client.getThirdname().isEmpty()
        && !client.getPassportNumber().isEmpty() && !client.getPassportSeries().isEmpty()){
            isCorrect = true;
            clientDao.save(client);
        }
        else {
            isCorrect = false;
        }
    }

}
