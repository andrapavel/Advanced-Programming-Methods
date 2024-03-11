
package repository;

import domain.Patient;

import java.io.*;

public class PatientFileRepo extends FileRepository<Patient, Integer>
{
    public PatientFileRepo(String filename)
    {
        super(filename);
    }

    @Override
    public void readFromFile()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] tokens = line.split(",");
                if (tokens.length != 3)
                    continue;
                int id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String dentalCondition = tokens[2].trim();
                Patient b = new Patient(id,name,dentalCondition);
                data.put(id, b);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: File not found - " + filename);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
        {
            for (Patient b: data.values())
            {
                bw.write(b.getID() + "," + b.getName() + "," + b.getDentalCondition() + "\n");
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}