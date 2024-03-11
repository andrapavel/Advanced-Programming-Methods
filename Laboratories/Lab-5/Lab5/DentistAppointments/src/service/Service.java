package service;

import domain.Appointment;
import domain.Identifiable;
import domain.Patient;
import exceptions.ValidationException;
import repository.MemoryRepo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Service<T extends Identifiable<ID>, ID>
{
    private final MemoryRepo<Integer, Patient> repoPatients;
    private final MemoryRepo<Integer, Appointment> repoAppointments;

    public Service(MemoryRepo<Integer, Patient> rP, MemoryRepo<Integer, Appointment> rA)
    {
        this.repoPatients = rP;
        this.repoAppointments = rA;
    }

    public void setPatients(List<Patient> patients)
    {
        repoPatients.replaceAllEntities(patients);
    }

    public void setAppointments(List<Appointment> appointments)
    {
        repoAppointments.replaceAllEntities(appointments);
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

    public void addPatient(Patient patient) throws ValidationException
    {
        repoPatients.addEntity(patient);
    }
    public ArrayList<Patient> getAllPatients()
    {
        return repoPatients.getAll();
    }

    public void deletePatient(Integer patientId) throws ValidationException
    {
        repoPatients.deleteEntity(patientId);
    }

    public void updatePatient(Patient updatedPatient) throws ValidationException
    {
        repoPatients.updateEntity(updatedPatient.getID(), updatedPatient);
    }

    public boolean updatePatientB(Patient updatedPatient) throws ValidationException
    {
        repoPatients.updateEntity(updatedPatient.getID(), updatedPatient);
        return false;
    }

    public Patient findPatientByID(Integer id) throws ValidationException
    {
        return repoPatients.findById(id);
    }

    public ArrayList<Patient> filterByName(String name)
    {
        ArrayList<Patient> all = getAllPatients();
        return (ArrayList<Patient>) all.stream()
                .filter(p -> p.getName().contains(name))
                .collect(Collectors.toList());
    }

    public void addAppointment(Appointment appointment) throws ValidationException
    {
        repoAppointments.addEntity(appointment);
    }
    public ArrayList<Appointment> getAllAppointments()
    {
        return repoAppointments.getAll();
    }
    public void deleteAppointment(Integer appId) throws ValidationException
    {
        repoAppointments.deleteEntity(appId);
    }

    public void updateAppointment(Integer appId, Appointment updatedAppointment) throws ValidationException
    {
        repoAppointments.updateEntity(appId, updatedAppointment);
    }

    public boolean updateAppointmentB(Integer appId, Appointment updatedAppointment) throws ValidationException
    {
        repoAppointments.updateEntity(appId, updatedAppointment);
        return false;
    }

    public Appointment findAppointmentByID(Integer id) throws ValidationException
    {
        return repoAppointments.findById(id);
    }

    public ArrayList<Appointment> filterByDate(String date)
    {
        ArrayList<Appointment> all = getAllAppointments();
        return (ArrayList<Appointment>) all.stream()
                .filter(a -> a.getDate().contains(date))
                .collect(Collectors.toList());
    }
}
