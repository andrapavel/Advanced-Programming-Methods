package settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Settings
{
    private final Properties properties;
    private final String filename = "D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab3//DentistAppointments//src//settings.properties";

    public Settings()
    {
        properties = new Properties();
        try
        {
            properties.load(new FileInputStream(filename));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getRepositoryType()
    {
        return properties.getProperty("Repository");
    }

    public String getPatientFile()
    {
        return properties.getProperty("Patients");
    }

    public String getAppointmentFile()
    {
        return properties.getProperty("Appointments");
    }

    public void updateRepositoryType(String repositoryType)
    {
        properties.setProperty("Repository", repositoryType);
        savePropertiesToFile();
    }

    public void updatePatientsFile(String patientsFile)
    {
        properties.setProperty("Patients", patientsFile);
        savePropertiesToFile();
    }

    public void updateAppointmentsFile(String appointmentsFile)
    {
        properties.setProperty("Appointments", appointmentsFile);
        savePropertiesToFile();
    }

    private void savePropertiesToFile()
    {
        try (OutputStream outputStream = new FileOutputStream(filename))
        {
            properties.store(outputStream, "Updated settings");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
