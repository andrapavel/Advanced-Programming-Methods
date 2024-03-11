package repository;

import domain.Identifiable;
import exceptions.ValidationException;


public abstract class FileRepository<T extends Identifiable<U>, U> extends MemoryRepo<U, T>
{
    protected String filename;

    public FileRepository(String filename)
    {
        this.filename = filename;
        this.readFromFile();
    }

    public abstract void readFromFile();

    public abstract void writeToFile();

    @Override
    public void addEntity(T elem) throws ValidationException
    {
        super.addEntity(elem);
        writeToFile();
    }

    @Override
    public void updateEntity(U id, T elem) throws ValidationException
    {
        super.updateEntity(id, elem);
        writeToFile();
    }

    @Override
    public T findByID(U id) throws ValidationException
    {

        return super.findByID(id);
    }


    @Override
    public Iterable<T> getAll() {
        return super.getAll();
    }
}
