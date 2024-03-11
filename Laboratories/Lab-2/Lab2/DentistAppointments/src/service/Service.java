package service;

import domain.Patient;
import domain.Appointment;
import repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Service
{
    // PATIENT
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;

    public Service(PatientRepo pr, AppointmentRepo ar)
    {
        this.patientRepo =  pr;
        this.appointmentRepo = ar;
    }

    public void addPatient(Integer ID, String name, int age, String dentalCondition)
    {
        Patient p = new Patient(ID, name, age, dentalCondition);
        this.patientRepo.addItem(ID,p);
    }

    public void deletePatient(Integer ID)
    {
        List<Patient> patientsList = getAllPatients();

        for (Patient p : patientsList)
        {
            if (p.getID().equals(ID))
            {
                patientRepo.deleteItem(ID);
                return;
            }
        }
        System.out.println("Appointment not found: " + ID);
    }

    public void updatePatient(int patientID, String newName, String newDentalCondition)
    {
        Patient patientToUpdate = patientRepo.findItem(patientID);
        if (patientToUpdate != null)
        {
            // Update the patient's name and dental condition
            patientToUpdate.setName(newName);
            patientToUpdate.setDentalCondition(newDentalCondition);

            patientRepo.updateItem(patientID, patientToUpdate);
            System.out.println("Patient details updated successfully.");
        }
        else
        {
            System.out.println("Patient with ID " + patientID + " was not found.");
        }
    }

    public boolean patientExistence(int ID)
    {
        List<Patient> patientsList = getAllPatients();

        for (Patient p : patientsList)
        {
            if (p.getID().equals(ID))
            {
                return true; // Patient exists
            }
        }
        return false; // Patient does not exist
    }

    public ArrayList<Patient> getAllPatients()
    {
        return this.patientRepo.getAll();
    }

    // APPOINTMENT

    public void addAppointment(Integer ID, Date dateTime, Integer patientID)
    {
        Appointment a = new Appointment(ID, dateTime, patientID);
        this.appointmentRepo.addItem(ID,a);

        // Find the patient by ID and add the appointment to their list
        Patient patient = patientRepo.getID(patientID);
        if (patient != null)
        {
            patient.addAppointment(a);
        }
    }

    public void deleteAppointment(Integer ID)
    {
        List<Appointment> appointmentsList = getAllAppointments();

        for (Appointment appointment : appointmentsList)
        {
            if (appointment.getID().equals(ID))
            {
                // Remove the appointment from the patient's list
                int patientID = appointment.getPatientID();
                Patient patient = patientRepo.findItem(patientID);

                if (patient != null)
                {
                    patient.removeAppointment(appointment);
                }

                // Remove the appointment from the appointmentRepo
                appointmentRepo.deleteItem(ID);

                return; // Exit the loop after deleting the appointment
            }
        }

        System.out.println("Appointment not found: " + ID);
    }

    public void updateAppointment(int appointmentID, Date newDateTime)
    {
        // Find the appointment by its ID within the repository
        Appointment appointmentToUpdate = appointmentRepo.findItem(appointmentID);

        if (appointmentToUpdate != null)
        {
            // Update the date and time of the appointment
            appointmentToUpdate.setDateTime(newDateTime);

            // Update the appointment in the repository
            appointmentRepo.updateItem(appointmentID, appointmentToUpdate);
        }
        else
        {
            System.out.println("Appointment with ID " + appointmentID + " not found.");
        }
    }

    public boolean appointmentExistence(int appointmentID)
    {
        ArrayList<Appointment> appointments = getAllAppointments();

        for (Appointment appointment : appointments)
        {
            if (appointment.getID() == appointmentID)
            {
                return true; // An appointment with the same ID already exists
            }
        }

        return false; // No conflicting appointment found
    }

    public boolean appForPatientExists(int patientID, Date dateTime)
    {
        ArrayList<Appointment> appointments = getAllAppointments();

        for (Appointment appointment : appointments)
        {
            if (appointment.getID() == patientID && appointment.getDateTime() == dateTime)
            {
                return true; // An appointment with the same patient and date/time already exists
            }
        }

        return false; // No conflicting appointment found
    }

    public ArrayList<Appointment> getAllAppointments()
    {
        return this.appointmentRepo.getAll();
    }

    // PATIENT'S APPOINTMENTS
    public List<Appointment> getPatientAppointments(int patientID)
    {
        List<Patient> patients = getAllPatients();

        for (Patient patient : patients)
        {
            if (patient.getID().equals(patientID))
            {
                // Found the patient, return their list of appointments
                return patient.getAppointments();
            }
        }

        // If the patient with the given ID is not found, return an empty list
        return new ArrayList<>();
    }


    // MISCELLANEOUS
    public boolean checkIDUniqueness(int patientID)
    {
        ArrayList<Patient> patients = patientRepo.getAll();
        for (Patient patient : patients)
        {
            if (patient.getID() == patientID)
            {
                return false; // ID is not unique
            }
        }
        return true; // ID is unique
    }
}
