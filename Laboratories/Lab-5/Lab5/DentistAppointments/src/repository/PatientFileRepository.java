
package repository;

import domain.Patient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PatientFileRepository extends FileRepository<Patient, Integer>
{
    public PatientFileRepository(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(this.filename)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 3) {
                    Integer id = Integer.parseInt(tokens[0].trim());
                    String name = tokens[1].trim();
                    String disease = tokens[2].trim();
                    Patient patient = new Patient(id, name, disease);
                    this.repo.put(id, patient);
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error reading from file", e);
        }
    }

    @Override
    public void writeToFile()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filename)))
        {
            for (Patient patient : this.repo.values())
            {
                bw.write(String.format("%d,%s,%s%n", patient.getID(), patient.getName(), patient.getDentalCondition()));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error writing to file", e);
        }
    }
}

