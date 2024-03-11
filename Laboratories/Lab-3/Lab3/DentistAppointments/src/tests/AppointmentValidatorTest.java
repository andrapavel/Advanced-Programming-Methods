package tests;

import domain.Appointment;
import domain.AppointmentValidator;
import org.junit.jupiter.api.Test;

class AppointmentValidatorTest
{

    @Test
    public void validateIdTest()
    {
        Appointment Appointment = new Appointment(1, "Ana", "22/10/2022");
        try
        {
            AppointmentValidator.validateAppointment(Appointment);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Appointment Appointment1 = new Appointment(0, "Ana", "22/10/2022");
        try
        {
            AppointmentValidator.validateAppointment(Appointment1);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validateDescriptionTest()
    {
        Appointment Appointment = new Appointment(1, "Ana", "22/10/2022");
        try
        {
            AppointmentValidator.validateAppointment(Appointment);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Appointment Appointment1 = new Appointment(1, "", "22/10/2022");
        try
        {
            AppointmentValidator.validateAppointment(Appointment1);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validateDateTest()
    {
        Appointment Appointment = new Appointment(1, "Ana", "22/10/2022");
        try
        {
            AppointmentValidator.validateAppointment(Appointment);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Appointment Appointment1 = new Appointment(1, "Ana", "");
        try
        {
            AppointmentValidator.validateAppointment(Appointment1);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

}
