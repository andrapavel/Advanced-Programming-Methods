package domain;

import java.sql.Date;

public class Appointment extends Entity implements Identifiable<Integer>
{
    private Date dateTime;
    private final Integer patientID;

    public Appointment(Integer ID, Date dateTime, Integer patientID)
    {
        super(ID);
        this.dateTime = dateTime;
        this.patientID = patientID;
    }

    public Date getDateTime()
    {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    public Integer getPatientID()
    {
        return this.patientID;
    }


    @Override
    public String toString()
    {
        return "Appointment {" +
                "ID = '" + getID() + '\'' +
                ", date = '" + dateTime + '\'' +
                "'}";
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof Appointment a))
            return false;

        return a.getID().equals(this.getID()) && a.dateTime.equals(this.dateTime);
    }
}
