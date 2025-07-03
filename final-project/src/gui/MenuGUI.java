package gui;

import dao.MenuDAO;
import models.Menu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class MenuGUI extends JFrame {
    private JTextField namaField, hargaField, stokField, searchField;
    private JButton insertButton, updateButton, deleteButton, searchButton;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private MenuDAO dao;

    public MenuGUI() {
        dao = new MenuDAO();
        setTitle("Aplikasi PT Pudding");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        //Panel input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Form Menu"));

        inputPanel.add(new JLabel("Nama Menu:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        inputPanel.add(new JLabel("Harga Menu:"));
        hargaField = new JTextField();
        inputPanel.add(hargaField);

        inputPanel.add(new JLabel("Stok Menu:"));
        stokField = new JTextField();
        inputPanel.add(stokField);

        insertButton = new JButton("Tambah Menu");
        updateButton = new JButton("Update Menu");
        deleteButton = new JButton("Hapus Menu");

        inputPanel.add(insertButton);
        inputPanel.add(updateButton);
        inputPanel.add(new JLabel()); 
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        //Tabel
        String[] columnNames = {"Kode", "Nama", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columnNames, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Menu"));

        add(scrollPane, BorderLayout.CENTER);

        //Panel search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Pencarian"));

        searchField = new JTextField(20);
        searchButton = new JButton("Cari");

        searchPanel.add(new JLabel("Nama Menu:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.SOUTH);

        //action buttons
        insertButton.addActionListener(e -> insertMenu());
        updateButton.addActionListener(e -> updateMenu());
        deleteButton.addActionListener(e -> deleteMenu());
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadMenuData(); 
            } else {
                searchMenu(keyword);
            }
        });

        menuTable.getSelectionModel().addListSelectionListener(e -> {
            int row = menuTable.getSelectedRow();
            if (row >= 0) {
                namaField.setText(tableModel.getValueAt(row, 1).toString());
                hargaField.setText(tableModel.getValueAt(row, 2).toString()); 
                stokField.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        loadMenuData();
    }

    private void insertMenu() {
        String nama = namaField.getText().trim();
        String harga = hargaField.getText().trim(); 
        String stokText = stokField.getText().trim();

        if (nama.isEmpty() || harga.isEmpty() || stokText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        try {
            int stok = Integer.parseInt(stokText);
            String kode = "PD-" + new Random().nextInt(1000);

            Menu menu = new Menu(kode, nama, harga, stok);
            dao.insertMenu(menu);

            JOptionPane.showMessageDialog(this, "Menu berhasil ditambahkan!");
            clearForm();
            loadMenuData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
        }
    }

    private void updateMenu() {
        int row = menuTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate.");
            return;
        }

        String kode = tableModel.getValueAt(row, 0).toString();
        String nama = namaField.getText().trim();
        String harga = hargaField.getText().trim();
        String stokText = stokField.getText().trim();

        try {
            int stok = Integer.parseInt(stokText);
            Menu menu = new Menu(kode, nama, harga, stok);
            dao.updateMenu(menu);

            JOptionPane.showMessageDialog(this, "Menu berhasil diupdate!");
            clearForm();
            loadMenuData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
        }
    }

    private void deleteMenu() {
        int row = menuTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
            return;
        }

        String kode = tableModel.getValueAt(row, 0).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus menu ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            dao.deleteMenu(kode);
            JOptionPane.showMessageDialog(this, "Menu berhasil dihapus!");
            clearForm();
            loadMenuData();
        }
    }

    private void loadMenuData() {
        tableModel.setRowCount(0);

        List<Menu> menuList = dao.getAllMenu();
        for (Menu menu : menuList) {
            Object[] row = {
                menu.getKode(),
                menu.getNama(),
                menu.getHarga(), 
                menu.getStok()
            };
            tableModel.addRow(row);
        }
    }

    private void searchMenu(String keyword) {
        tableModel.setRowCount(0);

        List<Menu> menuList = dao.getAllMenu();
        for (Menu menu : menuList) {
            if (menu.getNama().toLowerCase().contains(keyword)) {
                Object[] row = {
                    menu.getKode(),
                    menu.getNama(),
                    menu.getHarga(),
                    menu.getStok()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void clearForm() {
        namaField.setText("");
        hargaField.setText("");
        stokField.setText("");
        searchField.setText("");
        menuTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuGUI().setVisible(true);
        });
    }
}
