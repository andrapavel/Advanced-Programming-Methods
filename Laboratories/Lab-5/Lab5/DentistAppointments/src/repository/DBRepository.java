
package repository;

import domain.Identifiable;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBRepository<T extends Identifiable<ID>, ID> extends MemoryRepo<ID, T>
{

    protected String tableName;
    protected Connection conn = null;

    // Use a constant for the database URL
    private static final String DATABASE_URL = "jdbc:sqlite:D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab5//DentistAppointments//src//data//Dentistry.db";

    public DBRepository(String tableName)
    {
        this.tableName = tableName;
    }

    public abstract void getData();

    public void openConnection()
    {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(DATABASE_URL);

        try
        {
            if (this.conn == null || this.conn.isClosed())
            {
                this.conn = dataSource.getConnection();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error opening database connection", e);
        }
    }

    public void closeConnection()
    {
        try
        {
            if (this.conn != null && !this.conn.isClosed())
            {
                this.conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error closing database connection", e);
        }
    }
}
