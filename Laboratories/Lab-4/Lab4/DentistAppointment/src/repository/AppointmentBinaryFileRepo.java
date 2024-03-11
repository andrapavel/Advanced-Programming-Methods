package repository;

import domain.Appointment;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBinaryFileRepo extends FileRepository<Appointment, Integer> implements Serializable {

    public AppointmentBinaryFileRepo(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        File file = new File(filename);
        if (!file.exists())
        {
            initializeEmptyFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            data = (Map<Integer, Appointment>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException("Error reading from the file: " + e.getMessage(), e);
        }
    }

    private void initializeEmptyFile()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            Map<Integer, Appointment> emptyMap = new HashMap<>();
            oos.writeObject(emptyMap);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error initializing empty file: " + e.getMessage(), e);
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
            throw new RuntimeException("Error writing to the file: " + e.getMessage(), e);
        }
    }
}
