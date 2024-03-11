package domain;

public class PatientValidator
{

    public static void validatePatient(Patient patient)
    {
        validateId(patient.getID());
        validateName(patient.getName());
        validateDisease(patient.getDentalCondition());
    }
    private static void validateId(int id)
    {
        if(id == 0)
            throw new IllegalArgumentException("Id must be >0 blank");
    }

    private static void validateName(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
    }

    private static void validateDisease(String disease)
    {
        if (disease == null || disease.trim().isEmpty())
            throw new IllegalArgumentException("Disease must NOT be blank");
    }

}