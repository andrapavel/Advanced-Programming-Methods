package repository;

import domain.Identifiable;
import exceptions.ValidationException;

import java.util.ArrayList;

public interface IRepository <T extends Identifiable<ID>, ID>
{
    void addEntity(T item) throws ValidationException;
    void deleteEntity(ID id) throws ValidationException;
    void deleteAppointment(ID appID) throws  ValidationException;

    T findById(ID id) throws ValidationException;

    ArrayList<T> getAll();

    void updateEntity(ID id, T updatedEntity) throws ValidationException;
}
