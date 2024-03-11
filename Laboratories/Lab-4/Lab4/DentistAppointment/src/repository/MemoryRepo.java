package repository;

import domain.Identifiable;
import exceptions.ValidationException;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryRepo<U,T extends Identifiable<U>> implements IRepository<T, U>
{
    protected Map<U, T> data = new LinkedHashMap<U,T>();

    @Override
    public void addEntity(T item) throws ValidationException
    {
        if(data.containsKey(item.getID()))
        {
            throw new ValidationException("The element already exists.");
        }
            data.put(item.getID(),item);
    }

    @Override
    public void deleteEntity(U id) throws ValidationException
    {
        if (!data.containsKey(id))
        {
            throw new ValidationException("The element doesn't exist");
        }
        else
        {
            data.remove(id);
        }
    }

    @Override
    public T getEntity(int ID)
    {
        for (T entities : data.values())
        {
            if (entities.getID().equals(ID))
            {
                return entities;
            }
        }
        return null; // Patient not found
    }


    @Override
    public T findById(U id) throws ValidationException
    {
        if(!data.containsKey(id))
        {
            throw new ValidationException("The id doesn't exits");
        }
        return data.get(id);
    }

    @Override
    public Iterable<T> getAll()
    {
        return data.values();
    }

    @Override
    public void updateEntity(U id, T updatedEntity) throws ValidationException
    {
        if(!data.containsKey(id))
        {
            throw new ValidationException("The element doesn't exits");
        }
        else
        {
            data.put(id, updatedEntity);
        }
    }

}
