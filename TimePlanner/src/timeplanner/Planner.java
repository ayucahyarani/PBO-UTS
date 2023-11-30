/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package timeplanner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FLOW
 */
public class Planner extends Database{
    private int id;
    private String judul;
    private String jenis;
    private String deskripsi;
    private LocalDateTime tenggat;
    private String lokasi;
    private String status;
    private int pengguna;

    public Planner(String judul, String jenis, String deskripsi, LocalDateTime tenggat, String lokasi, String status, int pengguna) {
        this.judul = judul;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.tenggat = tenggat;
        this.lokasi = lokasi;
        this.status = status;
        this.pengguna = pengguna;
    }
    
    public boolean isJudulExist(String judul) {
        try {
            openConnection();

            String query = "SELECT COUNT(*) AS count FROM tugas WHERE judul = ?";
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setString(1, judul);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
        return false;
    }
    
    // CREATE
    public boolean createPlanner() {
        if (isJudulExist(judul)) {
            JOptionPane.showMessageDialog(null, "Judul tugas sudah ada. Gunakan judul yang berbeda.", "Duplikasi Judul", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            openConnection();

            String query = "INSERT INTO tugas (judul, jenis, deskripsi, tenggat, lokasi_pengumpulan, status, pengguna_id_pengguna) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, judul);
            preparedStatement.setString(2, jenis);
            preparedStatement.setString(3, deskripsi);
            preparedStatement.setObject(4, tenggat);
            preparedStatement.setString(5, lokasi);
            preparedStatement.setString(6, status);
            preparedStatement.setInt(7, pengguna);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
    }

    // READ
    public DefaultTableModel read() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            openConnection();

            String query = "SELECT * FROM tugas WHERE pengguna_id_pengguna = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, pengguna);
            resultSet = preparedStatement.executeQuery();

            model.addColumn("ID");
            model.addColumn("Judul");
            model.addColumn("Jenis");
            model.addColumn("Deskripsi");
            model.addColumn("Tenggat");
            model.addColumn("Lokasi");
            model.addColumn("Status");

            while (resultSet.next()) {
                this.id = resultSet.getInt("id_tugas");
                this.judul = resultSet.getString("judul");
                this.jenis = resultSet.getString("jenis");
                this.deskripsi = resultSet.getString("deskripsi");
                this.tenggat = this.tenggat = resultSet.getTimestamp("tenggat").toLocalDateTime();
                this.lokasi = resultSet.getString("lokasi_pengumpulan");
                this.status = resultSet.getString("status");
                this.pengguna = resultSet.getInt("pengguna_id_pengguna");

                model.addRow(new Object[]{id, judul, jenis, deskripsi, tenggat, lokasi, status});
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return model;
    }
    
    public boolean cariJudul() {
        try {
            openConnection();

            String query = "SELECT judul FROM tugas WHERE pengguna_id_pengguna = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, pengguna);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                this.id = resultSet.getInt("id_tugas");
                this.judul = resultSet.getString("judul");
                this.jenis = resultSet.getString("jenis");
                this.deskripsi = resultSet.getString("deskripsi");
                this.tenggat = this.tenggat = resultSet.getTimestamp("tenggat").toLocalDateTime();
                this.lokasi = resultSet.getString("lokasi_pengumpulan");
                this.deskripsi = resultSet.getString("status");
                this.pengguna = resultSet.getInt("pengguna_id_pengguna");
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }
        return true;
    }
                                                                                                                
    // UPDATE
    public boolean updatePlanner() {
        try {
            openConnection();

            String query = "UPDATE tugas SET deskripsi=?, tenggat=?, lokasi_pengumpulan=?, status=? WHERE judul=? AND pengguna_id_pengguna=?";
            preparedStatement = getConnection().prepareStatement(query);

            preparedStatement.setString(1, deskripsi);
            preparedStatement.setObject(2, tenggat);
            preparedStatement.setString(3, lokasi);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, judul);
            preparedStatement.setInt(6, pengguna);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
    }


    // DELETE
    public boolean deletePlanner(int id) {
        try {
            openConnection();

            String query = "DELETE FROM tugas WHERE id_tugas = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
    }
    
    public void setJComboBoxJudul(JComboBox<String> cariTugas) {
        try {
            openConnection();

            String query = "SELECT * FROM tugas WHERE pengguna_id_pengguna = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pengguna);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    cariTugas.removeAllItems();

                    while (resultSet.next()) {
                        this.id = resultSet.getInt("id_tugas");
                        this.judul = resultSet.getString("judul");
                        this.jenis = resultSet.getString("jenis");
                        this.deskripsi = resultSet.getString("deskripsi");
                        this.tenggat = resultSet.getTimestamp("tenggat").toLocalDateTime();
                        this.lokasi = resultSet.getString("lokasi_pengumpulan");
                        this.status = resultSet.getString("status");
                        this.pengguna = resultSet.getInt("pengguna_id_pengguna");

                        cariTugas.addItem(judul);
                    }
                }
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }
    }
}