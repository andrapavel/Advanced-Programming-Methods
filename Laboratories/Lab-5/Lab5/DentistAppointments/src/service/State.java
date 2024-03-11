package service;

import domain.Patient;
import domain.Appointment;

import java.util.ArrayList;
import java.util.List;

public record State(List<Patient> patients, List<Appointment> appointments)
{
    public State(List<Patient> patients, List<Appointment> appointments)
    {
        this.patients = new ArrayList<>(patients);
        this.appointments = new ArrayList<>(appointments);
    }

    public static State captureState(Service service)
    {
        List<Patient> patients = new ArrayList<>(service.getAllPatients());
        List<Appointment> appointments = new ArrayList<>(service.getAllAppointments());
        return new service.State(patients, appointments);
    }


    @Override
    public List<Patient> patients()
    {
        return new ArrayList<>(patients);
    }

    @Override
    public List<Appointment> appointments()
    {
        return new ArrayList<>(appointments);
    }
}

