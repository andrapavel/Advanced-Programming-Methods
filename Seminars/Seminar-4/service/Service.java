package service;

import domain.Doctor;
import repository.DoctorRepo;
import repository.Repository;

import javax.print.Doc;
import java.util.ArrayList;

public class Service {
    private DoctorRepo DocRepo;

    public Service(DoctorRepo dr) {
        this.DocRepo = dr;
    }

    public void addDoctor(Integer id, String name, String specialty, String location, double grade) {
        Doctor d = new Doctor(id, name, specialty, location, grade);
        this.DocRepo.addItem(d);
    }

    public ArrayList<Doctor> getAll()
    {
        return (ArrayList<Doctor>) this.DocRepo.getALlItems();
    }
}
