import repository.Repository;
import service.Service;
import ui.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        Repository repo = new Repository();
        Service serv = new Service(repo);
        UI ui = new UI(serv);

        String dateInput1 = "03/11/2023"; // Date string in the format dd/MM/yyyy
        String dateInput2 = "15/12/2023";
        String dateInput3 = "10/02/2024";
        String dateInput4 = "27/06/2024";
        String dateInput5 = "08/08/2024";

        // Patient1
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(dateInput1);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            serv.addPatient(1,"Sarah Smith", 38, "cancer", sqlDate);
        }
        catch (ParseException e)
        {
            logger.log(Level.SEVERE, "Error parsing the date", e);
            //System.out.println(e.getMessage());
        }

        // Patient2
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(dateInput2);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            serv.addPatient(2,"Blake Johnson", 27, "asthma", sqlDate);
        }
        catch (ParseException e)
        {
            logger.log(Level.SEVERE, "Error parsing the date", e);
        }

        //Patient3
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(dateInput3);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            serv.addPatient(3,"Alex Croft", 47, "arrhythmia", sqlDate);
        }
        catch (ParseException e)
        {
            logger.log(Level.SEVERE, "Error parsing the date", e);
        }

        // Patient4
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(dateInput4);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            serv.addPatient(4,"Rose James", 20, "astigmatism", sqlDate);
        }
        catch (ParseException e)
        {
            logger.log(Level.SEVERE, "Error parsing the date", e);
        }

        // Patient5
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(dateInput5);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            serv.addPatient(5,"Alan Jones", 52, "osteoporosis", sqlDate);
        }
        catch (ParseException e)
        {
            logger.log(Level.SEVERE, "Error parsing the date", e);
        }

        ui.run();
    }
}
