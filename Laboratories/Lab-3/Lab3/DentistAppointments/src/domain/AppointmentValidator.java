package domain;

public class AppointmentValidator {

    public static void validateAppointment(Appointment appointment)
    {
        validateId(appointment.getID());
        validateDescription(appointment.getDescription());
        validateDate(appointment.getDate());
    }
    private static void validateId(Integer id)
    {
        if(id == 0 )
            throw new IllegalArgumentException("Id must be >0 blank");
    }

    private static void validateDescription(String descr)
    {
        if (descr == null || descr.trim().isEmpty())
        {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
    }

    private static void validateDate(String date)
    {
        if (date == null || date.trim().isEmpty())
            throw new IllegalArgumentException("Date must NOT be blank");
    }
}
