package service;

import domain.Patient;
import repository.Repository;
import utils.Identifiable;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Service
{
    private final Repository repo;

    public Service(Repository r)
    {
        this.repo = r;
    }

    public void addPatient(int ID, String name, int age, String illness, Date dateOfAppointment)
    {
        Patient p = new Patient(ID, name, age, illness, dateOfAppointment);
        this.repo.addPatient(p);
    }

    public void deletePatient(int ID)
    {
        List<Identifiable> patientsList;
        patientsList = getAll();

        for (Identifiable p : patientsList)
        {
            if (p.getID() == ID)
            {
                this.repo.deletePatient(p);
                return;
            }
        }
        System.out.println("Patient not found: " + ID);
    }

    public void changeAppointment(int ID, Date newAppointmentDate)
    {
        List<Identifiable> patientsList = getAll();

        for (Identifiable p : patientsList)
        {
            if (p.getID() == ID)
            {
                p.setAppointmentDate(newAppointmentDate);
                System.out.println("Appointment for patient " + p.getID() + " changed to " + newAppointmentDate);
                return;
            }
        }
        System.out.println("Patient with ID " + ID + " not found.");
    }

    public static boolean isIDUnique(int ID, List<Integer> existingIDs)
    {
        for (int existingID : existingIDs)
        {
            if (existingID == ID)
            {
                return false; // ID is not unique
            }
        }
        return true; // ID is unique
    }

    public ArrayList<Integer> getAllIDs()
    {
        ArrayList<Identifiable> appointments = getAll();
        ArrayList<Integer> ids = new ArrayList<>();

        for (Identifiable appointment : appointments)
        {
            repo.addIDs(ids, (Patient) appointment);
        }

        return ids;
    }

    public ArrayList<Identifiable> getAll()
    {
        return this.repo.getAll();
    }

}
