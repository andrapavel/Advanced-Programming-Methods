import domain.*;

import repository.*;
import service.*;
import ui.UI;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
{
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab4//DentistAppointment//src//settings.properties"))
        {
            properties.load(reader);
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error reading properties file: " + e.getMessage(), e);
            return;
        }

        System.out.println("Properties loaded: " + properties);

        String repoType = properties.getProperty("repository_type");
        // ##########
        String location = properties.getProperty("Location");
        // ##########
        if (repoType == null)
        {
            System.out.println("Repository type is not specified in settings.properties.");
            return;
        }

        repoType = repoType.trim();

        IRepository<Patient, Integer> patientRepo;
        IRepository<Appointment, Integer> appointmentRepo;


        switch (repoType)
        {
            case "inMemory":
                patientRepo = new PatientRepository();
                appointmentRepo = new AppointmentsRepository();
                break;
            case "textFile":
                patientRepo = setupTextRepositoriesP(properties);
                appointmentRepo = setupTextRepositoriesA(properties);
                break;
            case "binaryFile":
                patientRepo = setupBinaryRepositories(properties);
                appointmentRepo = setupBinaryRepositoriesA(properties);
                break;
            //#####
            case "dataBase":
                patientRepo = setupDatabaseRepositoriesP(properties);
                appointmentRepo = setupDatabaseRepositoriesA(properties);
                break;
            ///#####
            case "json":
                patientRepo = setupJSONRepositoryP(properties);
                appointmentRepo = setupJSONRepositoryA(properties);
                break;
            case "xml":
                patientRepo = setupXMLRepositoriesP(properties);
                appointmentRepo = setupXMLRepositoriesA(properties);
                break;
            default:
                System.out.println("Invalid repository type specified in settings.properties: " + repoType);
                return;
        }

        //Create ReportService
        ReportService reportService = new ReportService(patientRepo, appointmentRepo);


        // Display reports
        reportService.displayReports();

        if (patientRepo != null && appointmentRepo != null)
        {
            Service service = new Service(patientRepo, appointmentRepo);
            UI ui = new UI(service);
            ui.run();
        }
//
    }

    // SET UP TEXT REPOSITORIES:
    // PATIENT
    private static PatientFileRepository setupTextRepositoriesP(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null)
        {
            LOGGER.log(Level.SEVERE, "Text repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientFileRepository(patientsFile);
    }

    //APPOINTMENT
    private static AppointmentFileRepo setupTextRepositoriesA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null)
        {
            LOGGER.log(Level.SEVERE, "Text repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentFileRepo(appointmentsFile);
    }

    //SETTING UP BINARY FILE REPOSITORIES:
    //PATIENT
    private static PatientBinaryFileRepository setupBinaryRepositories(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null)
        {
            LOGGER.log(Level.SEVERE, "Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientBinaryFileRepository(patientsFile);
    }

    //APPOINTMENT
    private static AppointmentBinaryFileRepo setupBinaryRepositoriesA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null)
        {
            LOGGER.log(Level.SEVERE, "Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentBinaryFileRepo(appointmentsFile);
    }

    // SET UP DATABASE REPOSITORIES:
// PATIENT
    private static PatientDBRepository setupDatabaseRepositoriesP(Properties properties)
    {
        String repositoryType = properties.getProperty("Patient_repository");

        if (repositoryType == null)
        {
            LOGGER.log(Level.SEVERE, "Database repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new PatientDBRepository(repositoryType);
    }

    // APPOINTMENT
    private static AppointmentDBRepository setupDatabaseRepositoriesA(Properties properties)
    {
        String repositoryType = properties.getProperty("Appointment_repository");

        if (repositoryType == null)
        {
            LOGGER.log(Level.SEVERE, "Database repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new AppointmentDBRepository(repositoryType);
    }

    // SET UP JSON REPOSITORIES:
    // PATIENT
    private static PatientJSONRepository setupJSONRepositoryP(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if ( patientsFile == null)
        {
            LOGGER.log(Level.SEVERE, "JSON repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new PatientJSONRepository(patientsFile);
    }

    // APPOINTMENT
    private static AppointmentJSONRepository setupJSONRepositoryA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if ( appointmentsFile == null)
        {
            LOGGER.log(Level.SEVERE, "JSON repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new AppointmentJSONRepository( appointmentsFile);
    }

    // SET UP XML REPOSITORIES:
    //PATIENT

    private static PatientXMLRepository setupXMLRepositoriesP(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null)
        {
            LOGGER.log(Level.SEVERE, "XML repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientXMLRepository(patientsFile);
    }


    //APPOINTMENT
    private static AppointmentXMLRepository setupXMLRepositoriesA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null)
        {
            LOGGER.log(Level.SEVERE, "XML repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentXMLRepository(appointmentsFile);
    }

}