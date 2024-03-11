package domain;

import java.io.Serializable;
import java.util.Objects;

public class Appointment extends Entity implements Identifiable<Integer>, Serializable
{
    private Integer appointmentID;
    private String Description;
    private String Date;

    public Appointment(Integer appointmentID, String description, String date)
    {
        super(appointmentID);
        this.Description = description;
        this.Date = date;
    }

    public Integer getPatientID()
    {
        return appointmentID;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getDate()
    {
        return Date;
    }

    public void setDate(String date)
    {
        Date = date;
    }

    @Override
    public String toString()
    {
        return "Appointment {" +
                "ID = " + getID()  +
                ", description = '" + Description + '\'' +
                ", date = '" + Date  +
                "'}";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentID, that.appointmentID) && Objects.equals(Description, that.Description) && Objects.equals(Date, that.Date);
    }
}
