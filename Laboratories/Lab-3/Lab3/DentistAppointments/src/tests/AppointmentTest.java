package tests;


import domain.Appointment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AppointmentTest {

    private Appointment appointment;

    @BeforeEach
    public void setUp()
    {
        appointment = new Appointment(1, "Dental Checkup", "2023-12-01");
    }

    @Test
    public void testGetId()
    {
        Assertions.assertEquals(1, appointment.getID());
    }

    @Test
    public void testGetDescription()
    {
        Assertions.assertEquals("Dental Checkup", appointment.getDescription());
    }

    @Test
    public void testSetDescription()
    {
        appointment.setDescription("General Checkup");
        Assertions.assertEquals("General Checkup", appointment.getDescription());
    }

    @Test
    public void testGetDate()
    {
        Assertions.assertEquals("2023-12-01", appointment.getDate());
    }

    @Test
    public void testSetDate()
    {
        appointment.setDate("2024-01-15");
        Assertions.assertEquals("2024-01-15", appointment.getDate());
    }

    @Test
    public void testEquals()
    {
        Appointment sameAppointment = new Appointment(1, "Dental Checkup", "2023-12-01");
        Appointment differentAppointment = new Appointment(2, "General Checkup", "2023-12-02");

        Assertions.assertEquals(appointment, sameAppointment);
        Assertions.assertNotEquals(appointment, differentAppointment);
    }

    @Test
    public void testToString()
    {
        String expectedToString = "Appointment {ID = 1, description = 'Dental Checkup', date = '2023-12-01'}";
        Assertions.assertEquals(expectedToString, appointment.toString());
    }
}
