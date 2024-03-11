package repository;

import domain.Identifiable;
import domain.Patient;
import exceptions.ValidationException;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBRepository<T extends Identifiable<U>, U> extends MemoryRepo<U, T>
{

    protected final String URL = "jdbc:sqlite:D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab4//DentistAppointment//src//data//Dentistry.db";

    protected String tableName;
    protected Connection conn = null;

    public DBRepository(String tableName)
    {
        this.tableName = tableName;
    }

    public abstract void getData();

    public void openConnection() throws SQLException
    {
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(URL);
            if (conn == null || conn.isClosed())
            {
                conn = dataSource.getConnection();
                System.out.println("Connection established successfully.");
            }
            else
            {
                System.out.println("Connection already established.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }


    public void closeConnection() throws SQLException
    {
        if (conn != null && !conn.isClosed())
        {
            conn.close();
        }
    }
}