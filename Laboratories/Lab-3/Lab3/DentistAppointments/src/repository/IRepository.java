package repository;

import domain.Identifiable;
import domain.Patient;
import exceptions.ValidationException;

public interface IRepository<T extends Identifiable<U>, U>
{
    //T getID(U ID);

    void addEntity(T entity) throws ValidationException;

    void updateEntity(U id, T entity) throws ValidationException;

    void deleteEntity(U id) throws ValidationException;

    T findByID(U id) throws ValidationException;

    Iterable<T> getAll();

    void deleteAppointment(U appID) throws  ValidationException;

    T getEntity(int ID);
}
