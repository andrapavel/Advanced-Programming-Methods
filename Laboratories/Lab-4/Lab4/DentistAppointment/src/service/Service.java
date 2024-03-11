package service;

import domain.Appointment;
import domain.Identifiable;
import domain.Patient;
import exceptions.ValidationException;
import repository.IRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Service<T extends Identifiable<U>, U>
{
    private final IRepository<T, U> repoPatients;
    private final IRepository<T, U> repoAppointments;

    public Service(IRepository<T, U> rP, IRepository<T, U> rA)
    {
        this.repoPatients = rP;
        this.repoAppointments = rA;
    }

    public static String getRepositoryType(String filePath) throws IOException
    {
        try (FileReader fr = new FileReader(filePath))
        {
            Properties props = new Properties();
            props.load(fr);
            return props.getProperty("repository_type").trim();
        }
    }

    public void addPatient(T patient) throws ValidationException
    {
        repoPatients.addEntity(patient);
       // repoPatients.add(patient); // Update the in-memory data
    }

    public Iterable<T> getAllPatients()
    {
        return repoPatients.getAll();
    }

    public void deletePatient(U patientId) throws ValidationException
    {
        repoPatients.deleteEntity(patientId);
    }

    public void updatePatient(T updatedPatient) throws ValidationException
    {
        repoPatients.updateEntity(updatedPatient.getID(), updatedPatient);
    }

    public Patient getPatientByID(int patientID)
    {
        return (Patient) repoPatients.getEntity(patientID);
    }

    public void addAppointment(T appointment) throws ValidationException
    {
        repoAppointments.addEntity(appointment);
    }

    public Iterable<T> getAllAppointments()
    {
        return repoAppointments.getAll();
    }

    public void deleteAppointment(U appId) throws ValidationException
    {
        repoAppointments.deleteEntity(appId);
    }

    public void updateAppointment(U appId, T updatedAppointment) throws ValidationException
    {
        repoAppointments.updateEntity(appId, updatedAppointment);
    }

    public Appointment getAppointmentByID(int appointmentID)
    {
        return (Appointment) repoAppointments.getEntity(appointmentID);
    }
}
