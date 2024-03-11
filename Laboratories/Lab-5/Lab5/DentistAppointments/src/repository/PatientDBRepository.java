
package repository;

import domain.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDBRepository extends DBRepository<Patient, Integer>
{

    public PatientDBRepository(String tableName)
    {
        super(tableName);
        this.getData();
    }

    @Override
    public void getData()
    {
        try
        {
            this.openConnection();
            String selectString = "SELECT * FROM " + this.tableName + ";";
            try (PreparedStatement ps = this.conn.prepareStatement(selectString);
                 ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next())
                {
                    int id = resultSet.getInt("patientID");
                    String name = resultSet.getString("name");
                    String disease = resultSet.getString("dentalCondition");
                    Patient patient = new Patient(id, name, disease);
                    this.repo.put(id, patient);
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

    @Override
    public void addEntity(Patient elem)
    {
        try
        {
            this.openConnection();
            this.conn.setAutoCommit(false);
            String insertString = "INSERT INTO " + this.tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = this.conn.prepareStatement(insertString))
            {
                ps.setInt(1, elem.getID());
                ps.setString(2, elem.getName());
                ps.setString(3, elem.getDentalCondition());
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

    @Override
    public void deleteEntity(Integer patientID)
    {
        try
        {
            this.openConnection();
            this.conn.setAutoCommit(false);
            String deleteString = "DELETE FROM " + this.tableName + " WHERE patientID = ?;";
            try (PreparedStatement ps = this.conn.prepareStatement(deleteString))
            {
                ps.setInt(1, patientID);
                ps.executeUpdate();
            }

            this.conn.commit();
            this.repo.remove(patientID);
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

    @Override
    public void updateEntity(Integer patientId, Patient updatedPatient)
    {
        try
        {
            this.openConnection();
            this.conn.setAutoCommit(false);
            String updateString = "UPDATE " + this.tableName + " SET name = ?, dentalCondition = ? WHERE patientID = ?;";
            try (PreparedStatement ps = this.conn.prepareStatement(updateString))
            {
                ps.setString(1, updatedPatient.getName());
                ps.setString(2, updatedPatient.getDentalCondition());
                ps.setInt(3, patientId);
                ps.executeUpdate();
            }

            this.conn.commit();
            this.repo.put(patientId, updatedPatient);
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
