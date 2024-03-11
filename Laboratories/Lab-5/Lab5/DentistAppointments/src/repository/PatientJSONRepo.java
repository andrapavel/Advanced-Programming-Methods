
package repository;

import domain.Patient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class PatientJSONRepo extends FileRepository<Patient, Integer>
{

    public PatientJSONRepo(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        try (FileReader reader = new FileReader(filename))
        {
            JSONParser jsonParser = new JSONParser();
            JSONArray patientsArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : patientsArray)
            {
                JSONObject patientObject = (JSONObject) obj;
                int id = Math.toIntExact((Long) Objects.requireNonNull(patientObject.get("id")));
                String name = (String) Objects.requireNonNull(patientObject.get("name"));
                String disease = (String) Objects.requireNonNull(patientObject.get("dental condition"));

                Patient patient = new Patient(id, name, disease);
                repo.put(id, patient);
            }
        }
        catch (IOException | ParseException | NullPointerException e)
        {
            //throw new RuntimeException("Error reading from file", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeToFile()
    {
        JSONArray patientsArray = new JSONArray();
        for (Patient patient : repo.values())
        {
            JSONObject patientObject = new JSONObject();
            patientObject.put("id", patient.getID());
            patientObject.put("name", patient.getName());
            patientObject.put("dental condition", patient.getDentalCondition());

            patientsArray.add(patientObject);
        }

        try (FileWriter fileWriter = new FileWriter(filename))
        {
            fileWriter.write(patientsArray.toJSONString());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error writing to file", e);
        }
    }
}
