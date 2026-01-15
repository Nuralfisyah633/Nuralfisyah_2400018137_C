package gui;

import manager.KosManager;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PembayaranDialog extends JDialog {
    private KosManager kosManager;
    private JComboBox<String> cbPenyewa;
    private JTextField tfJumlah;
    private JComboBox<String> cbMetode;
    private JComboBox<String> cbStatus;

    public PembayaranDialog(Frame parent, KosManager manager) {
        super(parent, "Tambah Pembayaran", true);
        this.kosManager = manager;
        setSize(450, 350);
        setLocationRelativeTo(parent);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(250, 255, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Tambah Pembayaran");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(76, 175, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Penyewa
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Penyewa:"), gbc);
        gbc.gridx = 1;
        cbPenyewa = new JComboBox<>();
        cbPenyewa.addItem("-- Pilih Penyewa --");
        for (Penyewa p : kosManager.getDaftarPenyewa()) {
            cbPenyewa.addItem(p.getId() + " - " + p.getNama());
        }
        mainPanel.add(cbPenyewa, gbc);

        // Jumlah
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Jumlah (Rp):"), gbc);
        gbc.gridx = 1;
        tfJumlah = new JTextField(20);
        mainPanel.add(tfJumlah, gbc);

        // Metode Pembayaran
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Metode:"), gbc);
        gbc.gridx = 1;
        cbMetode = new JComboBox<>(new String[]{
                "Transfer Bank", "Cash", "E-Wallet", "Kartu Kredit"
        });
        mainPanel.add(cbMetode, gbc);

        // Status
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        cbStatus = new JComboBox<>(new String[]{"Lunas", "Pending"});
        mainPanel.add(cbStatus, gbc);

        // Info icon with money
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel iconLabel = new JLabel("💵 Proses Pembayaran Bulanan", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        iconLabel.setForeground(new Color(100, 100, 100));
        mainPanel.add(iconLabel, gbc);

        // Buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(250, 255, 250));

        JButton btnSimpan = new JButton("Proses Pembayaran");
        btnSimpan.setBackground(new Color(76, 175, 80));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.addActionListener(e -> prosesPembayaran());

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBackground(new Color(244, 67, 54));
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setFocusPainted(false);
        btnBatal.addActionListener(e -> dispose());

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void prosesPembayaran() {
        if (cbPenyewa.getSelectedIndex() == 0 || tfJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Mohon lengkapi semua data!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String penyewaSelection = (String) cbPenyewa.getSelectedItem();
            String idPenyewa = penyewaSelection.split(" - ")[0];
            double jumlah = Double.parseDouble(tfJumlah.getText().trim());
            String metode = (String) cbMetode.getSelectedItem();
            String status = (String) cbStatus.getSelectedItem();

            // Cari kamar yang disewa penyewa ini
            String noKamar = "";
            for (Kamar k : kosManager.getDaftarKamar()) {
                if (kosManager.getPenyewaKamar(k.getNoKamar()) != null &&
                        kosManager.getPenyewaKamar(k.getNoKamar()).equals(idPenyewa)) {
                    noKamar = k.getNoKamar();
                    break;
                }
            }

            String id = kosManager.generateIdPembayaran();
            Pembayaran pembayaran = new Pembayaran(
                    id, idPenyewa, noKamar, jumlah,
                    LocalDate.now(), status, metode
            );

            kosManager.tambahPembayaran(pembayaran);

            JOptionPane.showMessageDialog(this,
                    "Pembayaran berhasil diproses!\nID: " + id,
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah harus berupa angka!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}