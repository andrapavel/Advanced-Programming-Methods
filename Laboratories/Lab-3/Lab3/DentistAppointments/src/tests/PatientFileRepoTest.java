package tests;

import domain.Patient;
import exceptions.ValidationException;
import repository.PatientFileRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class PatientFileRepoTest
{
    private PatientFileRepo patientFileRepository;

    @BeforeEach
    public void setUp()
    {
        patientFileRepository = new PatientFileRepo("D:/Faculty of Mathematics and Computer Science/2023-2024/First Semester/Advanced Programming Methods/Labs/Lab3/DentistAppointments/src/testPatients");
    }


    void addItem() throws ValidationException
    {
        Patient p1 = new Patient(1010, "Sarah James", "toothache");
        Patient p2 = new Patient(1010, "Sarah James", "toothache");
        patientFileRepository.addEntity(p1);
        Iterable<Patient> listPatients = patientFileRepository.getAll();
        boolean found = false;
        for (Patient patient : listPatients)
        {
            if (patient.getID() == 1010)
            {
                found = true;
                break;
            }
        }
        assertTrue(found);
        try
        {
            patientFileRepository.addEntity(p2);
        }
        catch (ValidationException e)
        {
            System.out.println("Item already exists.");
        }
    }

//    @Test
//    void updateById() throws ValidationException
//    {
//        Patient patientToUpdate = new Patient(1010, "Sarah James", "abscess");
//        patientFileRepository.updateEntity(1010, patientToUpdate);
//        assertEquals("Sarah James", patientFileRepository.findByID(1010).getName());
//    }

    @Test
    void getAllItems()
    {
        Iterable<Patient> listOfPatients = patientFileRepository.getAll();
        int counter = 0;
        for (Patient patient : listOfPatients)
        {
            counter++;
        }
        //assert counter == 2;
    }

    @Test
    void deleteEntity()
    {
        try {
            Patient patient = new Patient(1, "John Doe", "Fever");
            patientFileRepository.addEntity(patient);

            patientFileRepository.deleteEntity(1);

            assertFalse((BooleanSupplier) patientFileRepository.findByID(1));
        }
        catch (ValidationException e)
        {
            //fail("Exception not expected: " + e.getMessage());
        }
    }
}


