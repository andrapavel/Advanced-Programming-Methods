package tests;

import domain.Patient;
import domain.Appointment;
import repository.MemoryRepo;

import java.util.List;
import java.util.ArrayList;

import exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemoryRepoTest
{
    private MemoryRepo<Integer, Patient> patientRepo;
    private MemoryRepo<Integer, Appointment> appointmentRepo;

    @BeforeEach
    void setUp()
    {
        patientRepo = new MemoryRepo<>();
        appointmentRepo = new MemoryRepo<>();
    }

    @Test
    void addEntity() throws ValidationException
    {
        // Test adding a patient
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);
        assertTrue(containsEntity(patientRepo.getAll(), patient));

        // Test adding an appointment
        Appointment appointment = new Appointment(1, "Tooth extraction", "2023-12-15");
        appointmentRepo.addEntity(appointment);
        assertTrue(containsEntity(appointmentRepo.getAll(), appointment));
    }

    @Test
    void deleteEntity() throws ValidationException
    {
        // Test deleting a patient
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);
        patientRepo.deleteEntity(1);
        assertFalse(containsEntity(patientRepo.getAll(), patient));

        // Test deleting an appointment
        Appointment appointment = new Appointment(1, "Tooth extraction", "2023-12-15");
        appointmentRepo.addEntity(appointment);
        appointmentRepo.deleteEntity(1);
        assertFalse(containsEntity(appointmentRepo.getAll(), appointment));
    }

    @Test
    void deleteAppointment() throws ValidationException
    {
        // Test deleting an appointment
        Appointment appointment = new Appointment(1, "Tooth extraction", "2023-12-15");
        appointmentRepo.addEntity(appointment);
        appointmentRepo.deleteAppointment(1);
        assertFalse(containsEntity(appointmentRepo.getAll(), appointment));
    }

    @Test
    void findByID() throws ValidationException
    {
        // Test finding a patient by ID
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);
        assertEquals(patient, patientRepo.findByID(1));

        // Test finding an appointment by ID
        Appointment appointment = new Appointment(1, "Tooth extraction", "2023-12-15");
        appointmentRepo.addEntity(appointment);
        assertEquals(appointment, appointmentRepo.findByID(1));
    }

    @Test
    void getAll() throws ValidationException
    {
        // Test getting all patients
        Patient patient1 = new Patient(1, "John Doe", "Dental issue");
        Patient patient2 = new Patient(2, "Jane Smith", "Cavity");
        patientRepo.addEntity(patient1);
        patientRepo.addEntity(patient2);
        assertTrue(containsEntity(patientRepo.getAll(), patient1));
        assertTrue(containsEntity(patientRepo.getAll(), patient2));

        // Test getting all appointments
        Appointment appointment1 = new Appointment(1, "Tooth extraction", "2023-12-15");
        Appointment appointment2 = new Appointment(2, "Dental cleaning", "2023-12-16");
        appointmentRepo.addEntity(appointment1);
        appointmentRepo.addEntity(appointment2);
        assertTrue(containsEntity(appointmentRepo.getAll(), appointment1));
        assertTrue(containsEntity(appointmentRepo.getAll(), appointment2));
    }

    @Test
    void updateEntity() throws ValidationException
    {
        // Test updating a patient
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);

        Patient updatedPatient = new Patient(1, "John Doe", "New issue");
        patientRepo.updateEntity(1, updatedPatient);

        assertEquals(updatedPatient, patientRepo.findByID(1));

        // Test updating an appointment
        Appointment appointment = new Appointment(1, "Tooth extraction", "2023-12-15");
        appointmentRepo.addEntity(appointment);

        Appointment updatedAppointment = new Appointment(1, "Tooth extraction", "2023-12-16");
        appointmentRepo.updateEntity(1, updatedAppointment);

        assertEquals(updatedAppointment, appointmentRepo.findByID(1));
    }

    private <T> boolean containsEntity(Iterable<T> iterable, T entity)
    {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list.contains(entity);
    }
}
