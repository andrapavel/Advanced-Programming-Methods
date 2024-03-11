
package repository;

import domain.Patient;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BinaryFileWriter
{
    private static final Logger logger = Logger.getLogger(BinaryFileWriter.class.getName());

    public static void main(String[] args)
    {
        // Create a Patient object
        Patient patient1 = new Patient(1, "John Doe", "cavities");

        // Specify the file path
        String filePath = "D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab3//DentistAppointments//src//patients.bin";

        // Write the object to a binary file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath)))
        {
            oos.writeObject(patient1);
            logger.log(Level.INFO, "Patient object written to {0}", filePath);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Error writing patient object to file", e);
        }
    }
}
