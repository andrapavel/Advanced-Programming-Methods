
package repository;

import domain.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDBRepository extends DBRepository<Appointment, Integer>
{
    private static final Logger LOGGER = Logger.getLogger(AppointmentDBRepository.class.getName());
    private static final String COL_APPOINTMENT_ID = "appointmentID";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_DATE = "date";

    public AppointmentDBRepository(String tableName)
    {
        super(tableName);
        this.getData();
    }

    public void getData()
    {
        try
        {
            this.openConnection();
            String selectString = "SELECT * FROM " + this.tableName + ";";
            try (PreparedStatement ps = this.conn.prepareStatement(selectString);
                 ResultSet resultSet = ps.executeQuery())
            {

                while (resultSet.next())
                {
                    int id = resultSet.getInt(COL_APPOINTMENT_ID);
                    String description = resultSet.getString(COL_DESCRIPTION);
                    String date = resultSet.getString(COL_DATE);
                    Appointment appointment = new Appointment(id, description, date);
                    this.repo.put(id, appointment);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error getting data from database", e);
        }
        finally
        {
            this.closeConnection();
        }
    }

    public void addEntity(Appointment elem)
    {
        try
        {
            this.openConnection();
            this.conn.setAutoCommit(false);
            String insertString = "INSERT INTO " + this.tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = this.conn.prepareStatement(insertString))
            {
                ps.setInt(1, elem.getID());
                ps.setString(2, elem.getDescription());
                ps.setString(3, elem.getDate());
                ps.executeUpdate();
            }
            this.conn.commit();
            this.repo.put(elem.getID(), elem);
        }
        catch (SQLException e)
        {
            try
            {
                this.conn.rollback();
            }
            catch (SQLException rollbackException)
            {
                throw new RuntimeException("Error rolling back transaction", rollbackException);
            }
            throw new RuntimeException("Error adding entity to database", e);
        }
        finally
        {
            this.closeConnection();
        }
    }

    public void deleteEntity(Integer appointmentID)
    {
        try
        {
            this.openConnection();
            this.conn.setAutoCommit(false);
            String deleteString = "DELETE FROM " + this.tableName + " WHERE " + COL_APPOINTMENT_ID + " = ?;";
            try (PreparedStatement ps = this.conn.prepareStatement(deleteString))
            {
                ps.setInt(1, appointmentID);
                ps.executeUpdate();
            }
            this.conn.commit();
            this.repo.remove(appointmentID);
        }
        catch (SQLException e)
        {
            try
            {
                this.conn.rollback();
            }
            catch (SQLException rollbackException)
            {
                throw new RuntimeException("Error rolling back transaction", rollbackException);
            }
            throw new RuntimeException("Error deleting entity from database", e);
        }
        finally
        {
            this.closeConnection();
        }
    }

    public void updateEntity(Integer appointmentId, Appointment updatedAppointment)
    {
        try
        {
            this.openConnection();
            this.conn.setAutoCommit(false);
            String updateString = "UPDATE " + this.tableName + " SET " + COL_DESCRIPTION + " = ?, " + COL_DATE + " = ? WHERE " + COL_APPOINTMENT_ID + " = ?;";
            try (PreparedStatement ps = this.conn.prepareStatement(updateString))
            {
                ps.setString(1, updatedAppointment.getDescription());
                ps.setString(2, updatedAppointment.getDate());
                ps.setInt(3, appointmentId);
                ps.executeUpdate();
            }
            this.conn.commit();
            this.repo.put(appointmentId, updatedAppointment);
        }
        catch (SQLException e)
        {
            try
            {
                this.conn.rollback();
            }
            catch (SQLException rollbackException)
            {
                throw new RuntimeException("Error rolling back transaction", rollbackException);
            }
            throw new RuntimeException("Error updating entity in database", e);
        }
        finally
        {
            this.closeConnection();
        }
    }
}
