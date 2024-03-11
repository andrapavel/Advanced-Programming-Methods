package repository;

import domain.Identifiable;
import exceptions.ValidationException;

public interface IRepository<T extends Identifiable<U>, U>
{
     void addEntity(T item) throws ValidationException;
     void deleteEntity(U id) throws ValidationException;

     T findById(U id) throws ValidationException;

     Iterable<T> getAll();

     void updateEntity(U id, T updatedEntity) throws ValidationException;

     T getEntity(int ID);
}
