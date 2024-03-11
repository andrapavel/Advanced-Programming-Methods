package repository;

import domain.Appointment;
import domain.Patient;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBinaryFileRepo extends FileRepository<Appointment, Integer> implements Serializable
{

    public AppointmentBinaryFileRepo(String filename)
    {
        super(filename);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromFile()
    {
        File file = new File(filename);
        if (!file.exists())
        {
            Map<Integer,Patient > emptyMap = new HashMap<>();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
            {
                oos.writeObject(emptyMap);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            data = (Map<Integer, Appointment>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            oos.writeObject(data);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}