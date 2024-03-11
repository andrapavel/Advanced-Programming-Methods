package repository;

import domain.Appointment;
import domain.Identifiable;
import domain.Patient;
import exceptions.ValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryRepo<ID,T extends Identifiable<ID>> implements IRepository<T, ID>
{
    protected Map<ID,T> repo = new HashMap<>();

    public void replaceAllEntities(List<T> newEntities)
    {
        // Clear existing entities
        repo.clear();

        // Add new entities
        for (T entity : newEntities)
        {
            repo.put(entity.getID(), entity);
        }
    }

    @Override
    public void addEntity(T item) throws ValidationException
    {
        if(repo.containsKey(item.getID()))
        {
            //throw new ValidationException("The element already exists.");
        }
        repo.put(item.getID(),item);
    }

    @Override
    public void deleteEntity(ID id) throws ValidationException
    {
        if (!repo.containsKey(id))
        {
            throw new ValidationException("The element doesn't exist");
        }
        else
        {
            repo.remove(id);
            updateFile();
        }
    }

    private void updateFile()
    {
        try (FileWriter writer = new FileWriter("D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab5//DentistAppointments//src//patients.txt"))
        {
            // Iterate over the 'data' map and write each entry to the file
            for (Map.Entry<ID, T> entry : repo.entrySet())
            {
                ID id = entry.getKey();
                T entity = entry.getValue();

                if (entity instanceof Patient)
                {
                    Patient patient = (Patient) entity;
                    writer.write(String.format("%d,%s,%s%n", id, patient.getName(), patient.getDentalCondition()));
                }
                else if (entity instanceof Appointment)
                {
                    Appointment appointment = (Appointment) entity;
                    writer.write(String.format("%d,%s,%s%n", id, appointment.getDescription(), appointment.getDate()));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error updating file: " + e.getMessage());
        }
    }

    public void deleteAppointment(ID ID) throws ValidationException
    {
        if (!repo.containsKey(ID))
        {
            throw new ValidationException("The appointment doesn't exist");
        }
        else if (!(repo.get(ID) instanceof Appointment))
        {
            throw new ValidationException("The entity with the specified ID is not an appointment");
        }
        else
        {
            repo.remove(ID);
            updateAppointmentFile();
        }
    }

    private void updateAppointmentFile()
    {
        try (FileWriter writer = new FileWriter("D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab5//DentistAppointments//src//appointments.txt"))
        {
            // Iterate over the 'data' map and write each entry to the file
            for (Map.Entry<ID, T> entry : repo.entrySet())
            {
                ID id = entry.getKey();
                T entity = entry.getValue();

                if (entity instanceof Patient)
                {
                    Patient patient = (Patient) entity;
                    writer.write(String.format("%d,%s,%s%n", id, patient.getName(), patient.getDentalCondition()));
                }
                else if (entity instanceof Appointment)
                {
                    Appointment appointment = (Appointment) entity;
                    writer.write(String.format("%d,%s,%s%n", id, appointment.getDescription(), appointment.getDate()));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error updating file: " + e.getMessage());
        }
    }

    @Override
    public T findById(ID id) throws ValidationException
    {

        if(!repo.containsKey(id)){
            throw new ValidationException("The id doesn't exits");}
        return repo.get(id);
    }
    @Override
    public ArrayList<T> getAll()
    {
        return new ArrayList<>(repo.values());
    }

    @Override
    public void updateEntity(ID id, T updatedEntity) throws ValidationException
    {

        if(!repo.containsKey(id))
        {
            throw new ValidationException("The element doesn't exits");
        }
        else
        {
            repo.put(id, updatedEntity);
        }
    }
}
