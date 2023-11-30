/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package timeplanner;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    protected Connection connection = null;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public final void openConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/time?user=root&password=");
        } catch (SQLException ex) {
            this.displayErrors(ex);
        }
    }
    
     public final void closeConnection() {
        try {
            if (this.resultSet != null) this.resultSet.close();
            if (this.statement != null) this.statement.close();
            if (this.preparedStatement != null) this.preparedStatement.close();
            if (this.connection != null) this.connection.close();
            
            this.resultSet = null;
            this.statement = null;
            this.preparedStatement = null;
            this.connection = null;
        } catch (SQLException ex) {
            this.displayErrors(ex);
        }
    }

     protected final void displayErrors(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
}

