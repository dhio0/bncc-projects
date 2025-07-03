package dao;

import models.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection conn;

    public MenuDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pt_pudding", "root", "");
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal: " + e.getMessage());
        }
    }

    public void insertMenu(Menu menu) {
        String sql = "INSERT INTO menu (Kode, Nama, Harga, Stok) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, menu.getKode());
            ps.setString(2, menu.getNama());
            ps.setString(3, menu.getHarga()); 
            ps.setInt(4, menu.getStok());
            ps.executeUpdate();

            System.out.println("Menu berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan menu: " + e.getMessage());
        }
    }

    public List<Menu> getAllMenu() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String kode = rs.getString("Kode");
                String nama = rs.getString("Nama");
                String harga = rs.getString("Harga"); 
                int stok = rs.getInt("Stok");

                Menu menu = new Menu(kode, nama, harga, stok);
                list.add(menu);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data menu: " + e.getMessage());
        }

        return list;
    }   
    public void updateMenu(Menu menu) {
        String sql = "UPDATE menu SET Nama = ?, Harga = ?, Stok = ? WHERE Kode = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, menu.getNama());
            ps.setString(2, menu.getHarga()); 
            ps.setInt(3, menu.getStok());
            ps.setString(4, menu.getKode());
            ps.executeUpdate();

            System.out.println("Menu berhasil diupdate.");
        } catch (SQLException e) {
            System.out.println("Gagal update menu: " + e.getMessage());
        }
    }

    public void deleteMenu(String kode) {
        String sql = "DELETE FROM menu WHERE Kode = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kode);
            ps.executeUpdate();
            System.out.println("Menu berhasil dihapus.");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus menu: " + e.getMessage());
        }
    }
}
