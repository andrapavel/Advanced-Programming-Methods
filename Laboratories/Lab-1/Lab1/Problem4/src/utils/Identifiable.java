package utils;

import java.sql.Date;

public interface Identifiable
{
    int getID();

    void setAppointmentDate(Date newAppointmentDate);
}
