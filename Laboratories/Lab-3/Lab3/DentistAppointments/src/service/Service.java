package service;

import domain.*;
import repository.*;
import exceptions.ValidationException;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Collection;

public class Service<T extends Identifiable<U>, U>
{
    private final IRepository<T, U> repoPatients;
    private final IRepository<T, U> repoAppointments;

    private Collection<T> IDs;
    private Collection<Patient> Patients;
    private Collection<Patient> Appointments;

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
            return props.getProperty("repositoryType").trim();
        }
    }

    public void addPatient(T patient) throws ValidationException
    {
        repoPatients.addEntity(patient);
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

    public void deleteAppointment(U appID) throws ValidationException
    {
        repoAppointments.deleteAppointment(appID);
    }

    public void updateAppointment(U appID, T updatedAppointment) throws ValidationException
    {
        repoAppointments.updateEntity(appID, updatedAppointment);
    }

    public Appointment getAppointmentByID(int appointmentID)
    {
        return (Appointment) repoAppointments.getEntity(appointmentID);
    }
}
