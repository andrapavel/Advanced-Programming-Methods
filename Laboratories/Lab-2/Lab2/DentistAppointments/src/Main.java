
import repository.*;
import service.Service;
import ui.UI;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        PatientRepo pr = new PatientRepo();
        AppointmentRepo ar = new AppointmentRepo();
        Service serv = new Service(pr, ar);
        UI ui = new UI(serv);

        // ADD 5 PATIENTS TO THE REPOSITORY
        serv.addPatient(1, "Sarah Smith", 38, "cavities");
        serv.addPatient(2, "Blake Johnson", 27, "gingivitis");
        serv.addPatient(3, "Alex Croft", 47, "tooth abscess");
        serv.addPatient(4, "Rose James", 20, "tooth sensitivity");
        serv.addPatient(5, "Alan Jones", 52, "halitosis");

        // ADD 5 APPOINTMENTS, 1 FOR EACH PATIENT IN THE REPOSITORY
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            serv.addAppointment(1, new Date(dateFormat.parse("03/11/2023 08:30").getTime()), 1);
            serv.addAppointment(2, new Date(dateFormat.parse("15/12/2023 14:45").getTime()), 2);
            serv.addAppointment(3, new Date(dateFormat.parse("10/02/2024 09:15").getTime()), 3);
            serv.addAppointment(4, new Date(dateFormat.parse("27/06/2024 16:30").getTime()), 4);
            serv.addAppointment(5, new Date(dateFormat.parse("08/08/2024 10:00").getTime()), 5);
        }
        catch (ParseException e)
        {
            logger.log(Level.SEVERE, "Error parsing the date", e);
        }

        ui.run();
    }
}
