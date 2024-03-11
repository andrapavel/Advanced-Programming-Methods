package domain;

import utils.Identifiable;

import java.sql.Date;

public class Patient implements Identifiable
{
    private final int ID;
    private final String name;
    private final String illness;
    private Date dateOfAppointment;
    private final int age;

    public Patient(int ID, String name, int age, String illness, Date dateOfAppointment)
    {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.illness = illness;
        this.dateOfAppointment = dateOfAppointment;
    }

    @Override
    public int getID()
    {
        return this.ID;
    }

    public void setAppointmentDate(Date newAppointmentDate)
    {
        this.dateOfAppointment = newAppointmentDate;
    }

    @Override
    public String toString()
    {
        return "Patient {" +
                "ID = '" + ID + '\'' +
                ", name = '" + name + '\'' +
                ", age = '" + age + '\'' +
                ", illness = '" + illness + '\'' +
                ", dateOfAppointment = '" + dateOfAppointment +
                "'}";
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof Patient))
            return false;

        Patient a = (Patient)o;

        return a.ID == this.ID && a.name.equals(this.name) && a.age == this.age &&
                a.illness.equals(this.illness) &&
                a.dateOfAppointment.equals(this.dateOfAppointment);
    }

}
