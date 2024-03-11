package repository;

import exceptions.ElementNotFoundException;
import domain.Identifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class MemoryRepo <T extends Identifiable<U>, U> implements IRepository<T,U>
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private final Map<U, T> memElements = new HashMap<>();

    @Override
    public T getID(U ID)
    {
        if (ID == null)
        {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return memElements.get(ID);
    }

    public ArrayList<T> getAll()
    {
        return new ArrayList<>(memElements.values());
    }

    @Override
    public void addItem(U ID, T entity)
    {
        if (ID == null || entity == null)
        {
            throw new IllegalArgumentException("ID and entity cannot be null.");
        }

        if (memElements.containsKey(ID))
        {
            throw new IllegalArgumentException("An element with the same ID already exists.");
        }

        memElements.put(ID, entity);
    }

    @Override
    public void updateItem(U ID, T entity)
    {
        if (ID == null || entity == null)
        {
            throw new IllegalArgumentException("ID and entity cannot be null.");
        }

        if (!memElements.containsKey(ID))
        {
            throw new IllegalArgumentException("No element with the specified ID found for update.");
        }

        memElements.put(ID, entity);
    }
    @Override
    public void deleteItem(U ID)
    {
        if (ID == null)
        {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        if (!memElements.containsKey(ID))
        {
            throw new IllegalArgumentException("No element with the specified ID found for deletion.");
        }

        memElements.remove(ID);
    }

    @Override
    public T findItem(U ID)
    {
        if (ID == null)
        {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        for (T element : memElements.values())
        {
            if (element.getID().equals(ID))
            {
                return element; // Found a match, return the element
            }
        }

        // If no match is found, throw an exception
        throw new ElementNotFoundException(ANSI_RED + "Element with ID " + ID + " not found." + ANSI_RESET);
    }
}
