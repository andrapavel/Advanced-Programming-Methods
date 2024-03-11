package repository;

import domain.Identifiable;

public interface IRepository<T extends Identifiable<U>, U>
{
    T getID(U ID);

    void addItem(U id, T entity);

    void updateItem(U id, T entity);

    void deleteItem(U id);

    T findItem(U id);
}
