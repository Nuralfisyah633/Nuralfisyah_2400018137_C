package gui;

import manager.KosManager;
import model.*;
import javax.swing.*;
import java.awt.*;

public class TambahPenyewaDialog extends JDialog {
    private KosManager kosManager;
    private JTextField tfNama, tfTelepon, tfEmail, tfAlamat;
    private JComboBox<String> cbKamar;

    public TambahPenyewaDialog(Frame parent, KosManager manager) {
        super(parent, "Tambah Penyewa", true);
        this.kosManager = manager;
        setSize(450, 400);
        setLocationRelativeTo(parent);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(250, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Tambah Penyewa Baru");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(120, 81, 169));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Nama
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        tfNama = new JTextField(20);
        mainPanel.add(tfNama, gbc);

        // Telepon
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("No Telepon:"), gbc);
        gbc.gridx = 1;
        tfTelepon = new JTextField(20);
        mainPanel.add(tfTelepon, gbc);

        // Email
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        tfEmail = new JTextField(20);
        mainPanel.add(tfEmail, gbc);

        // Alamat
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        tfAlamat = new JTextField(20);
        mainPanel.add(tfAlamat, gbc);

        // Pilih Kamar
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Pilih Kamar:"), gbc);
        gbc.gridx = 1;

        cbKamar = new JComboBox<>();
        cbKamar.addItem("-- Pilih Kamar --");
        for (Kamar k : kosManager.getKamarTersedia()) {
            cbKamar.addItem(k.getNoKamar() + " - " + k.getJenisKamar() +
                    " - Rp" + k.getHarga());
        }
        mainPanel.add(cbKamar, gbc);

        // Buttons
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(250, 250, 255));

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBackground(new Color(76, 175, 80));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.addActionListener(e -> simpan());

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

    private void simpan() {
        String nama = tfNama.getText().trim();
        String telepon = tfTelepon.getText().trim();
        String email = tfEmail.getText().trim();
        String alamat = tfAlamat.getText().trim();
        String kamarSelection = (String) cbKamar.getSelectedItem();

        if (nama.isEmpty() || telepon.isEmpty() || email.isEmpty() ||
                alamat.isEmpty() || cbKamar.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Mohon lengkapi semua data!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Generate ID
        String id = kosManager.generateIdPenyewa();

        // Tambah penyewa
        Penyewa penyewa = new Penyewa(id, nama, telepon, alamat, email);
        kosManager.tambahPenyewa(penyewa);

        // Sewa kamar
        String noKamar = kamarSelection.split(" - ")[0];
        kosManager.sewaKamar(noKamar, id);

        JOptionPane.showMessageDialog(this,
                "Penyewa berhasil ditambahkan!\nID: " + id,
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}