package repository;

import domain.Appointment;
import exceptions.ValidationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDBRepository extends DBRepository<Appointment, Integer>
{

    public AppointmentDBRepository(String tableName)
    {
        super(tableName);
        getData();
    }

    @Override
    public void getData()
    {
        try
        {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString))
            {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    String date = resultSet.getString("date");
                    Appointment appointment = new Appointment(id, description, date);
                    data.put(id, appointment);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                closeConnection();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void addEntity(Appointment elem) throws ValidationException
    {
        try
        {
            openConnection();
            conn.setAutoCommit(false);
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString))
            {
                ps.setInt(1, elem.getID());
                ps.setString(2, elem.getDescription());
                ps.setString(3, elem.getDate());
                ps.executeUpdate();
            }
            conn.commit();
            // Update the in-memory data
            data.put(elem.getID(), elem);

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                closeConnection();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void deleteEntity(Integer appointmentId)
    {
        try
        {
            openConnection();
            conn.setAutoCommit(false);

            String deleteString = "DELETE FROM " + tableName + " WHERE id = ?;";
            try (PreparedStatement ps = conn.prepareStatement(deleteString))
            {
                ps.setInt(1, appointmentId);
                ps.executeUpdate();
            }

            conn.commit();
            // Update the in-memory data
            data.remove(appointmentId);

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                closeConnection();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateEntity(Integer appointmentId, Appointment updatedAppointment) throws ValidationException
    {
        try
        {
            openConnection();
            conn.setAutoCommit(false);

            String updateString = "UPDATE " + tableName + " SET description = ?, date = ? WHERE id = ?;";
            try (PreparedStatement ps = conn.prepareStatement(updateString))
            {
                ps.setString(1, updatedAppointment.getDescription());
                ps.setString(2, updatedAppointment.getDate());
                ps.setInt(3, appointmentId);
                ps.executeUpdate();
            }

            conn.commit();
            // Update the in-memory data
            data.put(appointmentId, updatedAppointment);

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                closeConnection();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}