package kelas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import gui.Dashboard;
import gui.Detail;
import gui.History;
import gui.List;
import gui.Login;
import gui.Profile;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author Amalia
 */
public class Databases {
    private static Connection c;
    private static Statement s;
    private static ResultSet rs;
    private static PreparedStatement ps;
    private ImageIcon format = null;

    public void register(String username, String no_hp, String password) {
        try {
            openDb();
            if (isUsernameExists(username)) {
                JOptionPane.showMessageDialog(null, "Username sudah terdaftar. Silakan gunakan username lain.");
            } else {
                ps = c.prepareStatement("INSERT INTO `datauser` VALUES (?, ?, ?)");
                ps.setString(1, username);
                ps.setString(2, no_hp);
                ps.setString(3, password);
                ps.executeUpdate();
                Login login = new Login();
                login.setVisible(true);
            }            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public boolean isUsernameExists(String username) throws SQLException {
        ps = c.prepareStatement("SELECT `username` FROM `datauser` WHERE `username` = ?");
        ps.setString(1, username);
        rs = ps.executeQuery();

        return rs.next();
    }
    
    public void login(String username, String password) {
        try {
            openDb();
            ps = c.prepareStatement("SELECT `username`, `password` FROM `datauser` WHERE `username` = ? AND `password` = ?");
            ps.setString(1, username);
            ps.setString(2, password); // Use the password directly, assuming it's a String
            rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successfully");
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void showUser(Profile profile) {
        String username = Login.usernameTxt.getText();
        String no_hp;
        try {
            openDb();
            ps = c.prepareStatement("SELECT `username`, `no_hp` FROM `datauser` WHERE `username` = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                no_hp = rs.getString("no_hp");
                profile.setProfile(username, no_hp);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void showFoto(Profile profile) {
        String username = Login.usernameTxt.getText();
        JLabel imageLabel = profile.getFoto();
        try {
            openDb();  
            ps = c.prepareStatement("SELECT `gambar` FROM `fotouser` WHERE `username` = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();            
            if(rs.next()) {
                byte[] imageData = rs.getBytes("gambar");
                format = new ImageIcon(imageData);
                Image mm = format.getImage();
                int width = imageLabel.getWidth();
                int height = imageLabel.getHeight();
                Image img2 = mm.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(img2);
                imageLabel.setIcon(image);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void foto(String username, byte[] foto) {
        try {
            openDb();
            if (isFotoExists(username)) {
                updateFoto(username, foto);
            } else {
                addFoto(username, foto);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void addFoto(String username, byte[] foto) {
        try{
            openDb();
            ps = c.prepareStatement("INSERT INTO `fotouser` VALUES (?, ?)");
            if (foto != null) {
                ps.setString(1, username);
                ps.setBytes(2, foto);
                ps.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void updateFoto(String username, byte[] foto) {
        try {
            openDb();
            if(foto != null) {
                String q = "UPDATE `fotouser` SET `gambar` = ? WHERE `username` = "
                    + "?";               
                ps = c.prepareStatement(q);                
                ps.setBytes(1, foto);
                ps.setString(2, username);
                ps.execute(); 
                JOptionPane.showMessageDialog(null, "Image has been changed");
            } else {
                String q = "UPDATE `fotouser` SET `gambar` = ? WHERE `username` = "
                    + "?";               
                ps = c.prepareStatement(q);                
                ps.setBytes(1, null);
                ps.setString(2, username);
                ps.execute();
                JOptionPane.showMessageDialog(null, "Image has been changed");
            }
        }catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            closeDb();
        }
    }
    
    public boolean isFotoExists(String username) throws SQLException {
        ps = c.prepareStatement("SELECT `username` FROM `fotouser` WHERE `username` = ?");
        ps.setString(1, username);
        rs = ps.executeQuery();

        return rs.next();
    }
    
    public void modelCar(Car car, List list) {
        ArrayList<ImageIcon> imageIcons = new ArrayList<>();
        ArrayList<String> models = new ArrayList<>();
        try {
            openDb();
            ps = c.prepareStatement("SELECT `gambar`, `model` FROM `list` WHERE `jenis` = ?");
            ps.setString(1, car.getSelectedCarType());
            rs = ps.executeQuery();
            ArrayList<JLabel> imageLabel = list.getList();
            int currentIndex = 0;
            while (rs.next() && currentIndex < imageLabel.size()) {
                byte[] imageData = rs.getBytes("gambar");
                format = new ImageIcon(imageData);
                Image mm = format.getImage();
                int width = imageLabel.get(currentIndex).getWidth();
                int height = imageLabel.get(currentIndex).getHeight();
                Image img2 = mm.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(img2);
                imageIcons.add(image);
                models.add(rs.getString("model"));
                currentIndex++;
            }
            list.setList(imageIcons, models);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void detailCar(Detail detail, String model) {
        try {
            openDb();
            ps = c.prepareStatement("SELECT `gambar`, `jenis`, `model`, `ukuran`, `harga`, `pemilik`, `hp_pemilik` FROM `detail` WHERE `model` = ?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            if (rs.next()) {
                detail.setAllDetails(rs.getString("jenis"),rs.getString("model"), rs.getString("ukuran"), rs.getString("pemilik"), rs.getString("hp_pemilik"), rs.getInt("harga"), rs.getBytes("gambar"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void createHistory(Car car, Detail detail) {
        String username = Login.usernameTxt.getText();
        try{
            openDb();
            ps = c.prepareStatement("INSERT INTO `history` VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, null);
            ps.setString(2, username);
            ps.setString(3, car.getSelectedCarType());
            ps.setString(4, detail.getModel());
            ps.setString(5, detail.getUkuran());
            ps.setInt(6, detail.getHarga());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void deleteHistory(int kode) {
        try{
            openDb();
            ps = c.prepareStatement("DELETE FROM `history` WHERE `id` = " + kode);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void recordHistory() {
        int newAutoIncrement;
        try{
            openDb();
            ps = c.prepareStatement("SELECT MAX(id) + 1 AS new_auto_increment FROM `history`");
            rs = ps.executeQuery();
            if(rs.next()) {
                newAutoIncrement = rs.getInt("new_auto_increment");
                ps = c.prepareStatement("ALTER TABLE `history` AUTO_INCREMENT = ?");
                ps.setInt(1, newAutoIncrement);
                ps.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void delnRecord(String selected) {
        int kode = Integer.parseInt(selected);
        try {
            openDb();
            deleteHistory(kode);
            recordHistory();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void resetHistory() {
        try {
            openDb();
            ps = c.prepareStatement("DELETE FROM `history`");
            ps.executeUpdate();
            recordHistory();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    public void showHistory(String username, History history) {
        ArrayList<String> jenis = new ArrayList<>();
        ArrayList<String> model = new ArrayList<>();
        ArrayList<String> ukuran = new ArrayList<>();
        ArrayList<Integer> harga = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();
        try{            
            openDb();
            ps = c.prepareStatement("SELECT `id`, `jenis`, `model`, `ukuran`, `harga` FROM `history` WHERE `username` = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
                jenis.add(rs.getString("jenis"));
                model.add(rs.getString("model"));
                ukuran.add(rs.getString("ukuran"));
                harga.add(rs.getInt("harga"));
                history.setTable(id, jenis, model, ukuran, harga);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        } finally {
            closeDb();
        }
    }
    
    private static void openDb() throws ClassNotFoundException, SQLException {
        String URL = "jdbc:mysql://localhost:3306/dbmobil";
        String USERNAME = "root";
        String PASSWORD = "";
        Class.forName("com.mysql.cj.jdbc.Driver");
        c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    private static void closeDb() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

