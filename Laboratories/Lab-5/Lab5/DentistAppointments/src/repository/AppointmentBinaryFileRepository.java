
package repository;

import domain.Appointment;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBinaryFileRepository extends FileRepository<Appointment, Integer> implements Serializable
{

    public AppointmentBinaryFileRepository(String filename)
    {
        super(filename);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromFile() {
        File file = new File(filename);
        if (!file.exists()) {
            initializeEmptyFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            repo = (Map<Integer, Appointment>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException("Error reading from the binary file: " + e.getMessage(), e);
        }
    }

    @Override
    public void writeToFile()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            oos.writeObject(repo);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error writing to the binary file: " + e.getMessage(), e);
        }
    }

    private void initializeEmptyFile()
    {
        Map<Integer, Appointment> emptyMap = new HashMap<>();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            oos.writeObject(emptyMap);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error initializing an empty binary file: " + e.getMessage(), e);
        }
    }
}
