package domain;

import java.util.List;
import java.util.ArrayList;

public class Patient extends Entity implements Identifiable<Integer>
{
    private String name;
    private final int age;
    private String dentalCondition;
    private final List<Appointment> appointments;

    public Patient(Integer ID, String name, int age, String dentalCondition)
    {
        super(ID);
        this.name = name;
        this.age = age;
        this.dentalCondition = dentalCondition;
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment a)
    {
        appointments.add(a);
    }

    public void setName(String newName)
    {
        this.name = newName;
    }

    public void setDentalCondition(String newDentalCondition)
    {
        this.dentalCondition = newDentalCondition;
    }

    public List<Appointment> getAppointments()
    {
        return appointments;
    }

    public void removeAppointment(Appointment appointment)
    {
        appointments.remove(appointment);
    }

    @Override
    public String toString()
    {
        return "Patient {" +
                "ID = '" + getID() + '\'' +
                ", name = '" + name + '\'' +
                ", age = '" + age + '\'' +
                ", illness = '" + dentalCondition + '\'' +
                "}";
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof Patient p))
            return false;

        return p.getID().equals(this.getID())  && p.name.equals(this.name) && p.age == this.age &&
                p.dentalCondition.equals(this.dentalCondition);
    }

}
