/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles the connection and provides operations to the chosen database.
 * 
 * @author Lukasz Jamroz
 * @version 1.0
 */
public class DatabaseController {

    /**
     *  Database connection handler.
     */
    private Connection connHandler;

    /**
     * The DatabaseController constructor, which initialize a connection to chosen database,
     * with chosen username, password and driver.
     *
     * @param driver - the client driver
     * @param database -  the database path
     * @param username - the database username
     * @param password - the database password
     */
    public DatabaseController(String driver, String database, String username, String password) {
        try {
            Class.forName(driver);
            connHandler = DriverManager.getConnection(database, username, password);            
            createTableIfNExist();
        } catch (SQLException | ClassNotFoundException excp) {
            System.err.println("SQLException while controler init: " + excp.getMessage());
            try {
                if (connHandler != null) {
                    connHandler.close();
                }
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    }
    /**
     * Closes the connection with database.
     */
    public void close() {
        try {
            if (connHandler.isValid(0)) {
                connHandler.close();
            }
        } catch (SQLException ex) {
            System.err.println("SQLException while controler close: " + ex.getMessage());
        }
    }

    /**
     * Insert new line to the History table.
     *
     * @param result the conversion outcome text
     * @param source the conversion source text
     * @throws java.sql.SQLException passes exception, which occurs while inserting data
     */
    public void insert(String result, String source) throws SQLException {
            PreparedStatement statement = connHandler.prepareStatement("INSERT INTO History(Result,Source) VALUES (?,?)");
            statement.setString(1, result);
            statement.setString(2, source);
            statement.executeUpdate();
    }

    /**
     * Provides access to all the results and their sources from History table.
     *
     * @return list of all the rows, where each row is represented as String[]{Source,Result}
     * @throws java.sql.SQLException passes exception, which occurs while selecting data
     */
    public ArrayList<String[]> selectAll() throws SQLException {
        ArrayList<String[]> tempList = new ArrayList<>();

        ResultSet result = connHandler.createStatement().executeQuery("SELECT Result, Source FROM History");
        while (result.next()) {
            String[] tempElement = new String[]{result.getString("Source"),result.getString("Result")};
            tempList.add(tempElement);
        }
        return tempList;
    }
    
    /**
     * Deletes the whole data from History table.
     */
    public void deleteAll() {
        try {
            PreparedStatement statement = connHandler.prepareStatement("DELETE FROM History");
            statement.executeUpdate();         
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        }
    }
    
    /**
     * Creates the history table if such table does not exist in the database.
     */
    private void createTableIfNExist() {
        try {
            ResultSet rs = connHandler.createStatement().executeQuery("SELECT * FROM History WHERE Id=0");            
        } catch (SQLException excp) {
            try {
                Statement statement = connHandler.createStatement();
                statement.executeUpdate("CREATE TABLE History "
                        + "(Id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),"
                        + " Result VARCHAR(255), Source VARCHAR(255) )");
            } catch (SQLException sqlex) {
                System.err.println("SQL exception: " + sqlex.getMessage());
            }
        }
    }
}
