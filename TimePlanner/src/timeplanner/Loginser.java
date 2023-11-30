/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package timeplanner;

import java.sql.SQLException;

/**
 *
 * @author FLOW
 */
public class Loginser extends Database {
    private int id;
    private int password;

    public Loginser(int id, int password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
    
    
    
    public boolean loginUser() {
        boolean isAuthenticated = false;
        
        try {
            openConnection();

            String query = "SELECT * FROM pengguna WHERE id_pengguna = ? AND password = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isAuthenticated = true;
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return isAuthenticated;
    }
    
    public boolean registerUser() {
        boolean isRegistered = false;

        try {
            openConnection();

            String query = "INSERT INTO pengguna (id_pengguna, password) VALUES (?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, password);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                isRegistered = true;
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return isRegistered;
    } 
}
