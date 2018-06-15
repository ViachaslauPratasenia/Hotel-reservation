package database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import exceptions.MySQLException;

import java.sql.*;

/**
 * Created by user on 20.11.2017.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hoteldatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Statement statement;
    private Driver driver;
    private Connection connection;

    public void connection() throws MySQLException{
        try {
            driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        }catch(SQLException e) {
            System.out.println("Connection wasn't installed");
            throw new MySQLException("SQL exception");
        }
    }

    public ResultSet showInfo(String query){
        try {
           ResultSet rs =  statement.executeQuery(query);
           return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addInfo(String query){
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(String query1){
        try {
            statement.executeUpdate(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
