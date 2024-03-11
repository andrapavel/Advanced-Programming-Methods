package tests;

import domain.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;


public class PatientTest
{
    @Test
    void testPatientConstructor()
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        Assertions.assertEquals(1, patient.getID());
        Assertions.assertEquals("John Doe", patient.getName());
        Assertions.assertEquals("Dental issue", patient.getDentalCondition());
    }

    @Test
    void testGetName()
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        Assertions.assertEquals("John Doe", patient.getName());

        patient.setName("Jane Smith");
        Assertions.assertEquals("Jane Smith", patient.getName());
    }

    @Test
    void testGetDentalCondition()
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        Assertions.assertEquals("Dental issue", patient.getDentalCondition());

        patient.setDentalCondition("Cavity");
        Assertions.assertEquals("Cavity", patient.getDentalCondition());
    }

    @Test
    void testToString()
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        String expectedString = "Patient {ID = '1', name = 'John Doe', dentalCondition = 'Dental issue'}";
        Assertions.assertEquals(expectedString, patient.toString());
    }

    @Test
    void testEquals()
    {
        Patient patient1 = new Patient(1, "John Doe", "Dental issue");
        Patient patient2 = new Patient(1, "John Doe", "Dental issue");
        Patient patient3 = new Patient(2, "Jane Smith", "Cavity");

        Assertions.assertEquals(patient1, patient2);
        Assertions.assertNotEquals(patient1, patient3);
    }
}