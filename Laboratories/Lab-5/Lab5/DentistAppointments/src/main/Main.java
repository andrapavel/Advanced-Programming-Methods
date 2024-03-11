
package main;

import domain.Appointment;
import domain.Patient;
import gui.PatientsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.ReportService;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application
{

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String SETTINGS_FILE_PATH = "D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab5//DentistAppointments//src//settings.properties";

    public static void main(String[] args)
    {
        launch();
    }
    @SuppressWarnings("unchecked")

    @Override
    public void start(Stage stage) throws Exception
    {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(SETTINGS_FILE_PATH))
        {
            properties.load(reader);
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error reading properties file", e);
            return;
        }

        System.out.println("Properties loaded: " + properties);

        String repoType = properties.getProperty("repository_type");

        if (repoType == null)
        {
            LOGGER.warning("Repository type is not specified in settings.properties.");
            return;
        }

        repoType = repoType.trim();

        MemoryRepo<Integer, Patient> patientRepo;
        MemoryRepo<Integer, Appointment> appointmentRepo;

        try
        {
            patientRepo = setupRepositoryP(repoType, properties);
            appointmentRepo = setupRepositoryA(repoType, properties);
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error initializing repositories", e);
            return;
        }

        // Create ReportService
        ReportService reportService = new ReportService(patientRepo, appointmentRepo);

        // Display reports
        reportService.displayReports();

        if (patientRepo != null && appointmentRepo != null)
        {
            Service service = new Service(patientRepo, appointmentRepo);
            PatientsController controller = new PatientsController(service);

            // PATIENTS LIST
            Patient patient1 = new Patient(1, "Sarah Smith", "cavities");
            Patient patient2 = new Patient(2, "Blake Johnson", "gingivitis");
            Patient patient3 = new Patient(3, "Alex Croft", "tooth abscess");
            Patient patient4 = new Patient(4, "Clara Rose", "tooth sensitivity");

            service.addPatient(patient1);
            service.addPatient(patient2);
            service.addPatient(patient3);
            service.addPatient(patient4);

            // APPOINTMENTS LIST
            Appointment appointment1 = new Appointment(1, "annual check-up", "12/01/2024");
            Appointment appointment2 = new Appointment(2, "weekly check-up", "19/09/2024");
            Appointment appointment3 = new Appointment(3, "monthly check-up", "27/03/2024");
            Appointment appointment4 = new Appointment(4, "check-up", "02/01/2024");

            service.addAppointment(appointment1);
            service.addAppointment(appointment2);
            service.addAppointment(appointment3);
            service.addAppointment(appointment4);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PatientsGUI.fxml"));
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    private MemoryRepo<Integer, Patient> setupRepositoryP(String repoType, Properties properties)
    {
        return switch (repoType)
        {
            case "inMemory" -> new PatientRepo();
            case "textFile" -> setupTextRepositoriesP(properties);
            case "binaryFile" -> setupBinaryRepositories(properties);
            case "dataBase" -> setupDatabaseRepositoriesP(properties);
            case "json" -> setupJSONRepositoryP(properties);
            case "xml" -> setupXMLRepositoriesP(properties);
            default ->
            {
                LOGGER.warning("Invalid repository type specified in settings.properties: " + repoType);
                yield null;
            }
        };
    }

    private MemoryRepo<Integer, Appointment> setupRepositoryA(String repoType, Properties properties)
    {
        return switch (repoType)
        {
            case "inMemory" -> new AppointmentRepo();
            case "textFile" -> setupTextRepositoriesA(properties);
            case "binaryFile" -> setupBinaryRepositoriesA(properties);
            case "dataBase" -> setupDatabaseRepositoriesA(properties);
            case "json" -> setupJSONRepositoryA(properties);
            case "xml" -> setupXMLRepositoriesA(properties);
            default ->
            {
                LOGGER.warning("Invalid repository type specified in settings.properties: " + repoType);
                yield null;
            }
        };
    }

    //SET UP TEXT REPOSITORIES:
    // PATIENT
    private static PatientFileRepository setupTextRepositoriesP(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null)
        {
            System.out.println("Text repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientFileRepository(patientsFile);
    }

    //APPOINTMENT
    private static AppointmentFileRepository setupTextRepositoriesA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null)
        {
            System.out.println("Text repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentFileRepository(appointmentsFile);
    }

    //SETTING UP BINARY FILE REPOSITORIES:
    //PATIENT
    private static PatientBinaryFileRepository setupBinaryRepositories(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null)
        {
            System.out.println("Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientBinaryFileRepository(patientsFile);
    }

    //APPOINTMENT
    private static AppointmentBinaryFileRepository setupBinaryRepositoriesA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null)
        {
            System.out.println("Binary repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentBinaryFileRepository(appointmentsFile);
    }

    // SET UP DATABASE REPOSITORIES:
    // PATIENT
    private static PatientDBRepository setupDatabaseRepositoriesP(Properties properties)
    {
        String repositoryType = properties.getProperty("Patient_repository");

        if (repositoryType == null)
        {
            System.out.println("Database repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new PatientDBRepository(repositoryType);
    }

    // APPOINTMENT
    private static AppointmentDBRepository setupDatabaseRepositoriesA(Properties properties)
    {
        String repositoryType = properties.getProperty("Appointment_repository");
        // String location = properties.getProperty("Location");

        if (repositoryType == null)
        {
            System.out.println("Database repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new AppointmentDBRepository(repositoryType);
    }

    // SET UP JSON REPOSITORIES:
    // PATIENT
    private static PatientJSONRepo setupJSONRepositoryP(Properties properties)
    {
        //String location = properties.getProperty("Location");
        String patientsFile = properties.getProperty("Patient_repository");

        if ( patientsFile == null)
        {
            System.out.println("JSON repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new PatientJSONRepo(patientsFile);
    }

    // APPOINTMENT
    private static AppointmentJSONRepo setupJSONRepositoryA(Properties properties)
    {
        // String location = properties.getProperty("Location");
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if ( appointmentsFile == null)
        {
            System.out.println("JSON repository settings are not properly specified in settings.properties.");
            return null;
        }

        return new AppointmentJSONRepo( appointmentsFile);
    }

    // SET UP XML REPOSITORIES:
    //PATIENT

    private static PatientXMLRepo setupXMLRepositoriesP(Properties properties)
    {
        String patientsFile = properties.getProperty("Patient_repository");

        if (patientsFile == null)
        {
            System.out.println("XML repository file is not properly specified in settings.properties.");
            return null;
        }

        patientsFile = patientsFile.trim();
        return new PatientXMLRepo(patientsFile);
    }


    //APPOINTMENT
    private static AppointmentXMLRepo setupXMLRepositoriesA(Properties properties)
    {
        String appointmentsFile = properties.getProperty("Appointment_repository");

        if (appointmentsFile == null)
        {
            System.out.println("XML repository file is not properly specified in settings.properties.");
            return null;
        }

        appointmentsFile = appointmentsFile.trim();
        return new AppointmentXMLRepo(appointmentsFile);
    }
}
