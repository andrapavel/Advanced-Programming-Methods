
import domain.Appointment;
import domain.Patient;
import repository.*;
import ui.UI;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {

        configureLogger(); // Configure logger settings

        Properties properties = loadProperties();
        if (properties == null)
        {
            logger.severe("Properties file could not be loaded. Exiting.");
            return;
        }

        System.out.println("Properties loaded: " + properties);

        IRepository<Patient, Integer> patientRepo;
        IRepository<Appointment, Integer> appointmentRepo;

        switch (properties.getProperty("repositoryType"))
        {
            case "inMemory":
                patientRepo = new PatientRepo();
                appointmentRepo = new AppointmentRepo();
                break;
            case "textFile":
                patientRepo = setupTextRepositories(properties);
                appointmentRepo = setupTextAppointmentRepositories(properties);
                break;
            case "binaryFile":
                patientRepo = setupBinaryRepositories(properties);
                appointmentRepo = setupBinaryAppointmentRepositories(properties);
                break;
            default:
                logger.severe("Invalid repository type specified in settings.properties: " + properties.getProperty("repositoryType"));
                return;
        }

        if (patientRepo != null)
        {
            Service service = new Service(patientRepo, appointmentRepo);
            UI ui = new UI(service);
            ui.run();
        }
    }

    private static void configureLogger()
    {
        try
        {
            // Create a file handler that writes log records to a file
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setLevel(Level.ALL);

            // Create a console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);

            // Create a simple formatter
            SimpleFormatter formatter = new SimpleFormatter();

            // Set the formatter for the handlers
            fileHandler.setFormatter(formatter);
            consoleHandler.setFormatter(formatter);

            // Add the handlers to the logger
            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);
        }
        catch (IOException e)
        {
            logger.severe("Error configuring logger: " + e.getMessage());
        }
    }

        private static Properties loadProperties()
        {
            Properties properties = new Properties();
            try (FileReader reader = new FileReader("D:/Faculty of Mathematics and Computer Science/2023-2024/First Semester/Advanced Programming Methods/Labs/Lab3/DentistAppointments/src/settings.properties")) {
                properties.load(reader);
            }
            catch (IOException e)
            {
                logger.log(Level.SEVERE, "Error reading properties file", e);
                System.out.println("Error reading properties file: " + e.getMessage());
                return null;
            }
            return properties;
        }

    private static PatientFileRepo setupTextRepositories(Properties properties)
    {
        String patientsFile = properties.getProperty("repositoryPatientsLocation");

        if (patientsFile == null)
        {
            System.out.println("Text repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientFileRepo(patientsFile);
    }
    private static AppointmentFileRepo setupTextAppointmentRepositories(Properties properties)
    {
        String appointmentsFile = properties.getProperty("repositoryAppointmentsLocation");

        if (appointmentsFile == null)
        {
            System.out.println("Text repository file for appointments is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentFileRepo(appointmentsFile);
    }

    private static PatientBinaryFileRepo setupBinaryRepositories(Properties properties)
    {
        String patientsFile = properties.getProperty("repositoryPatientsLocation");

        if (patientsFile == null)
        {
            System.out.println("Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();

        return new PatientBinaryFileRepo(patientsFile);
    }


    private static AppointmentBinaryFileRepo setupBinaryAppointmentRepositories(Properties properties)
    {
        String appointmentsFile = properties.getProperty("repositoryAppointmentsLocation");

        if (appointmentsFile == null)
        {
            System.out.println("Binary repository file for appointments is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentBinaryFileRepo(appointmentsFile);
    }
}