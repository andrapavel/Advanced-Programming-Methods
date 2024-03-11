
package repository;

import domain.Identifiable;
import exceptions.ValidationException;

import java.util.ArrayList;

public abstract class FileRepository<T extends Identifiable<ID>, ID> extends MemoryRepo<ID, T>
{
    protected String filename;

    public FileRepository(String filename)
    {
        this.filename = filename;
        this.readFromFile();
    }

    public abstract void readFromFile();

    public abstract void writeToFile();

    public void addEntity(T elem) throws ValidationException
    {
        super.addEntity(elem);
        this.writeToFile();
    }

    public void updateEntity(ID id, T elem) throws ValidationException
    {
        super.updateEntity(id, elem);
        this.writeToFile();
    }

    public T findById(ID id) throws ValidationException
    {
        return super.findById(id);
    }

    public ArrayList<T> getAll()
    {
        return super.getAll();
    }
}
