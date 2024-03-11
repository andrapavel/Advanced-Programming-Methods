package repository;

import domain.Patient;
import utils.Identifiable;

import java.util.ArrayList;

public class Repository
{
    private final ArrayList<Identifiable> patients = new ArrayList<>();

    public void addPatient(Identifiable a)
    {
        patients.add(a);
    }

    public void deletePatient(Identifiable a)
    {
        patients.remove(a);
    }

    public ArrayList<Identifiable> getAll()
    {
        return this.patients;
    }

    public void addIDs(ArrayList<Integer> ids, Patient a)
    {
        ids.add(a.getID());
    }

}
