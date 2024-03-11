package domain;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Patient implements Identifiable<Integer>
{
    private Integer patientID;
    private String name;
    private String dentalCondition;

    public Patient(Integer ID, String name, String dentalCondition)
    {
        this.patientID = ID;
        this.name = name;
        this.dentalCondition = dentalCondition;
    }

    public Integer getAppointmentID()
    {
        return patientID;
    }

    public void setAppointmentID(Integer appointmentID)
    {
        this.patientID = appointmentID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDentalCondition()
    {
        return dentalCondition;
    }

    public void setDentalCondition(String dentalCondition)
    {
        this.dentalCondition = dentalCondition;
    }

    @Override
    public String toString()
    {
        return "Patient {" +
                "ID = '" + getID() + '\'' +
                ", name = '" + name + '\'' +
                ", dentalCondition = '" + dentalCondition + '\'' +
                "}";
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof Patient p))
            return false;

        return p.getID().equals(this.getID())  && p.name.equals(this.name) &&
                p.dentalCondition.equals(this.dentalCondition);
    }

    @Override
    public Integer getID()
    {
        return patientID;
    }
}
