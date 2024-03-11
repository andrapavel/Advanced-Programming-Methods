
package repository;

import domain.Appointment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppointmentFileRepository extends FileRepository<Appointment, Integer>
{

    private static final String DELIMITER = ",";
    private static final String NEW_LINE = "\n";

    public AppointmentFileRepository(String filename)
    {
        super(filename);
    }

    public void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] tokens = line.split(DELIMITER);
                if (tokens.length == 3)
                {
                    Integer id = Integer.parseInt(tokens[0].trim());
                    String description = tokens[1].trim();
                    String date = tokens[2].trim();
                    Appointment appointment = new Appointment(id, description, date);
                    repo.put(id, appointment);
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error reading from file", e);
        }
    }

    public void writeToFile()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
        {
            for (Appointment appointment : repo.values())
            {
                bw.write(appointment.getID() + DELIMITER + appointment.getDescription() + DELIMITER + appointment.getDate() + NEW_LINE);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error writing to file", e);
        }
    }
}
