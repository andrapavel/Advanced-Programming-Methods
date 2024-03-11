package tests;

import domain.Patient;
import repository.PatientBinaryFileRepo;
import exceptions.ValidationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PatientBinaryFileRepoTest
{

    private PatientBinaryFileRepo patientRepo;
    private static final String TEST_FILENAME = "testPatients.dat";

    @BeforeEach
    void setUp() {
        patientRepo = new PatientBinaryFileRepo(TEST_FILENAME);
    }

    @AfterEach
    void tearDown()
    {
        // Clean up: delete the test file
        File file = new File(TEST_FILENAME);
        if (file.exists())
        {
            file.delete();
        }
    }

    @Test
    void addEntity() throws ValidationException
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);

        Patient retrievedPatient = patientRepo.findByID(1);
        assertNotNull(retrievedPatient);
        assertEquals(patient, retrievedPatient);
    }

    @Test
    void deleteEntity() throws ValidationException
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);

        patientRepo.deleteEntity(1);

        assertThrows(ValidationException.class, () -> patientRepo.findByID(1));
    }

    @Test
    void updateEntity() throws ValidationException
    {
        Patient patient = new Patient(1, "John Doe", "Dental issue");
        patientRepo.addEntity(patient);

        Patient updatedPatient = new Patient(1, "John Doe", "New issue");
        patientRepo.updateEntity(1, updatedPatient);

        assertEquals(updatedPatient, patientRepo.findByID(1));
    }

//    @Test
//    void readFromFile() throws ValidationException
//    {
//        Patient patient = new Patient(1, "John Doe", "Dental issue");
//        patientRepo.addEntity(patient);
//
//        // Create a new repository and read from the file
//        PatientBinaryFileRepo newRepo = new PatientBinaryFileRepo(TEST_FILENAME);
//
//        Patient retrievedPatient = newRepo.findByID(1);
//        assertNotNull(retrievedPatient);
//        assertEquals(patient, retrievedPatient);
//    }

//    @Test
//    void writeToFile() throws ValidationException
//    {
//        Patient patient = new Patient(1, "John Doe", "Dental issue");
//        patientRepo.addEntity(patient);
//
//        // Create a new repository and read from the file
//        PatientBinaryFileRepo newRepo = new PatientBinaryFileRepo(TEST_FILENAME);
//
//        Patient retrievedPatient = newRepo.findByID(1);
//        assertNotNull(retrievedPatient);
//        assertEquals(patient, retrievedPatient);
//    }
}
