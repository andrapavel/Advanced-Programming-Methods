package service;

import domain.Doctor;
import repository.DoctorRepo;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Service {
    private IRepository<Doctor, Integer> DocRepo;

    public Service(IRepository<Doctor, Integer> dr) {
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

    public ArrayList<Doctor> filterBySpecialty(String specialty)
    {
        ArrayList<Doctor> all = getAll();
        ArrayList<Doctor> filteredDoctors = (ArrayList<Doctor>) all.stream()
                .filter(d -> d.getSpecialty().contains(specialty))
                .collect(Collectors.toList());
        return filteredDoctors;
    }
}
