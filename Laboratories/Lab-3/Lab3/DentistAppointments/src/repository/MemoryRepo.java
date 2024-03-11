package repository;

import domain.Identifiable;
import domain.Patient;
import domain.Appointment;
import exceptions.ValidationException;

import java.io.FileWriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryRepo<U,T extends Identifiable<U>> implements IRepository<T, U>
{
    protected Map<U, T> data = new LinkedHashMap<>();

    @Override
    public void addEntity(T item) throws ValidationException
    {
        if(data.containsKey(item.getID()))
        {
            throw new ValidationException("The element already exists.");
        }
        data.put(item.getID(),item);
    }

    @Override
    public void deleteEntity(U ID) throws ValidationException
    {
        if (!data.containsKey(ID))
        {
            throw new ValidationException("The element doesn't exist");
        }
        else
        {
            data.remove(ID);
            updateFile();
        }
    }

    @Override
    public T getEntity(int ID)
    {
        for (T entities : data.values())
        {
            if (entities.getID().equals(ID))
            {
                return entities;
            }
        }
        return null; // Patient not found
    }

    private void updateFile()
    {
        try (FileWriter writer = new FileWriter("D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab3//DentistAppointments//src//patients.txt")) {
            // Iterate over the 'data' map and write each entry to the file
            for (Map.Entry<U, T> entry : data.entrySet())
            {
                U id = entry.getKey();
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

    public void deleteAppointment(U ID) throws ValidationException
    {
        if (!data.containsKey(ID))
        {
            throw new ValidationException("The appointment doesn't exist");
        }
        else if (!(data.get(ID) instanceof Appointment))
        {
            throw new ValidationException("The entity with the specified ID is not an appointment");
        }
        else
        {
            data.remove(ID);
            updateAppointmentFile();
        }
    }

    private void updateAppointmentFile()
    {
        try (FileWriter writer = new FileWriter("D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab3//DentistAppointments//src//appointments.txt")) {
            // Iterate over the 'data' map and write each entry to the file
            for (Map.Entry<U, T> entry : data.entrySet())
            {
                U id = entry.getKey();
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
    public T findByID(U ID) throws ValidationException
    {
        if(!data.containsKey(ID))
        {
            throw new ValidationException("The id doesn't exits");
        }
        return data.get(ID);
    }

    @Override
    public Iterable<T> getAll()
    {
        return data.values();
    }

    @Override
    public void updateEntity(U ID, T updatedEntity) throws ValidationException
    {
        if(!data.containsKey(ID))
        {
            throw new ValidationException("The element doesn't exits");}
        else
        {
            data.put(ID, updatedEntity);
        }
    }
}
