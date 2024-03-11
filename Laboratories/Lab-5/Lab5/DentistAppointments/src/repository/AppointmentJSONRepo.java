
package repository;

import domain.Appointment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppointmentJSONRepo extends FileRepository<Appointment, Integer>
{

    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_DESCRIPTION = "description";
    private static final String JSON_KEY_DATE = "date";

    public AppointmentJSONRepo(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        try (FileReader reader = new FileReader(filename))
        {
            JSONParser jsonParser = new JSONParser();
            JSONArray appointmentsArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : appointmentsArray)
            {
                JSONObject appointmentObject = (JSONObject) obj;
                int id = Math.toIntExact((Long) appointmentObject.get(JSON_KEY_ID));
                String description = (String) appointmentObject.get(JSON_KEY_DESCRIPTION);
                String date = (String) appointmentObject.get(JSON_KEY_DATE);

                Appointment appointment = new Appointment(id, description, date);
                repo.put(id, appointment);
            }
        }
        catch (IOException | ParseException e)
        {
            //throw new RuntimeException("Error reading from JSON file", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeToFile()
    {
        JSONArray appointmentsArray = new JSONArray();
        for (Appointment appointment : repo.values())
        {
            JSONObject appointmentObject = new JSONObject();
            appointmentObject.put(JSON_KEY_ID, appointment.getID());
            appointmentObject.put(JSON_KEY_DESCRIPTION, appointment.getDescription());
            appointmentObject.put(JSON_KEY_DATE, formatAppointmentDate(appointment.getDate()));

            appointmentsArray.add(appointmentObject);
        }

        try (FileWriter fileWriter = new FileWriter(filename))
        {
            fileWriter.write(appointmentsArray.toJSONString());
        }
        catch (IOException e)

        {
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    private String formatAppointmentDate(String date)
    {
        // Format the date without escaped slashes
        return date.replace("\\/", "/");
    }
}
