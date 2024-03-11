package tests;

import domain.Patient;
import domain.PatientValidator;
import org.junit.jupiter.api.Test;

class PatientValidatorTest
{

    @Test
    public void validateIdTest()
    {
        Patient patient = new Patient(1, "Ana",  "braces");
        try
        {
            PatientValidator.validatePatient(patient);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient(0, "Ana",  "braces");
        try
        {
            PatientValidator.validatePatient(patient1);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validateNameTest()
    {
        Patient patient = new Patient(1, "Ana",  "gingivitis");
        try{
            PatientValidator.validatePatient(patient);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient(1, "",  "gingivitis");
        try{
            PatientValidator.validatePatient(patient1);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validateDiseaseTest()
    {
        Patient patient = new Patient(1, "Ana",  "cavities");
        try
        {
            PatientValidator.validatePatient(patient);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient(1, "Ana",  "");
        try
        {
            PatientValidator.validatePatient(patient1);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }
}
