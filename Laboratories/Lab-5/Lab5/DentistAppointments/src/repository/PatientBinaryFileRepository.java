
package repository;

import domain.Patient;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PatientBinaryFileRepository extends FileRepository<Patient, Integer> implements Serializable
{

    public PatientBinaryFileRepository(String filename)
    {
        super(filename);
        readFromFile(); // Automatically read from the file when the repository is created
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromFile()
    {
        File file = new File(filename);
        if (!file.exists())
        {
            // If the file doesn't exist, create an empty map and write it to the file
            Map<Integer, Patient> emptyMap = new HashMap<>();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
            {
                oos.writeObject(emptyMap);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Error creating the file: " + e.getMessage(), e);
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            Object obj = ois.readObject();

            if (obj instanceof Map)
            {
                repo = (Map<Integer, Patient>) obj;
            }
            else
            {
                throw new RuntimeException("Invalid data format in file");
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException("Error reading from the file: " + e.getMessage(), e);
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
            throw new RuntimeException("Error writing to the file: " + e.getMessage(), e);
        }
    }
}
