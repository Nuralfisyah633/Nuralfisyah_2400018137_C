package gui;

import manager.KosManager;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DashboardFrame extends JFrame {
    private KosManager kosManager;
    private JTable tableKamar;
    private DefaultTableModel tableModel;
    private JPanel cardPanel;
    private Map<String, String> roomImages;
    private TableRowSorter<DefaultTableModel> sorter;

    public DashboardFrame() {
        kosManager = new KosManager();
        initRoomImages();
        setTitle("Dashboard - Sistem Manajemen Kos");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadDataKamar();
    }

    private void initRoomImages() {
        roomImages = new HashMap<>();
        roomImages.put("A1", "/getResourcess/A1.jpg");
        roomImages.put("B1", "/getResourcess/B1.jpg");
        roomImages.put("C1", "/getResourcess/C1.jpg");
        roomImages.put("D1", "/getResourcess/D1.jpg");
        roomImages.put("E1", "/getResourcess/E1.jpg");

        // Coba berbagai kemungkinan nama file profil
        roomImages.put("PROFILE", "/getResourcess/profile.jpeg");

        // Debug: Cek file mana yang ada
        String[] possibleProfiles = {
                "/getResourcess/profile.jpeg",
                "/getResourcess/profile.jpeg",
                "/getResourcess/Profile.jpeg",
                "/getResourcess/Profile.jpeg"
        };

        for (String path : possibleProfiles) {
            URL testURL = getClass().getResource(path);
            if (testURL != null) {
                System.out.println("✅ FOUND PROFILE: " + path);
                roomImages.put("PROFILE", path);
                break;
            } else {
                System.out.println("❌ NOT FOUND: " + path);
            }
        }
    }

    private ImageIcon loadImageFromFile(String path, int width, int height) {
        try {
            URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } else {
                System.out.println("❌ Gambar tidak ditemukan: " + path);
                return null;
            }
        } catch (Exception e) {
            System.out.println("❌ Error loading: " + path + " - " + e.getMessage());
            return null;
        }
    }

    private JLabel createRoundedProfilePhoto(String path, int size) {
        try {
            URL imgURL = getClass().getResource(path);
            if (imgURL == null) {
                System.out.println("❌ Profile tidak ditemukan: " + path);
                // Buat kotak dengan background dan emoji
                JLabel fallback = new JLabel("👤", SwingConstants.CENTER);
                fallback.setPreferredSize(new Dimension(size, size));
                fallback.setOpaque(true);
                fallback.setBackground(new Color(210, 155, 214));
                fallback.setFont(new Font("Arial", Font.PLAIN, 24));
                return fallback;
            }

            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);

            BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setClip(new Ellipse2D.Float(0, 0, size, size));
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();

            JLabel photoLabel = new JLabel(new ImageIcon(circleBuffer));
            photoLabel.setPreferredSize(new Dimension(size, size));
            System.out.println("✅ Profile berhasil dimuat!");
            return photoLabel;
        } catch (Exception e) {
            System.out.println("❌ Error foto profil: " + e.getMessage());
            JLabel fallback = new JLabel("👤", SwingConstants.CENTER);
            fallback.setPreferredSize(new Dimension(size, size));
            fallback.setOpaque(true);
            fallback.setBackground(new Color(255, 206, 235));
            return fallback;
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(229, 185, 239));

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(218, 173, 228));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Sistem Manajemen Kos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(120, 81, 169));
        panel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setBackground(new Color(218, 173, 228));

        JLabel photoLabel = createRoundedProfilePhoto(roomImages.get("PROFILE"), 50);

        JLabel userLabel = new JLabel("Kos NurPurple ●");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(new Color(132, 56, 140));

        userPanel.add(photoLabel);
        userPanel.add(userLabel);

        panel.add(userPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(165, 122, 175));
        panel.setPreferredSize(new Dimension(180, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuItems = {
                "Dashboard", "Data Kamar", "Data Penyewa",
                "Pembayaran", "Riwayat Pembayaran", "Daftar Kamar", "Logout"
        };

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(160, 35));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(new Color(180, 160, 220));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btn.addActionListener(e -> handleMenuClick(item));
            panel.add(btn);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBackground(Color.WHITE);

        JButton btnTambah = createButton("+ Tambah", new Color(46, 125, 50));
        btnTambah.addActionListener(e -> showTambahPenyewa());

        JButton btnCetak = createButton("🖨 Cetak", new Color(255, 152, 0));
        btnCetak.addActionListener(e -> cetakData());

        controlPanel.add(new JLabel("Tampilkan:"));
        controlPanel.add(new JComboBox<>(new String[]{"10", "25", "50", "100"}));
        controlPanel.add(new JLabel("Data"));
        controlPanel.add(btnTambah);
        controlPanel.add(btnCetak);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search:"));
        JTextField searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { search(searchField.getText()); }
            public void removeUpdate(DocumentEvent e) { search(searchField.getText()); }
            public void insertUpdate(DocumentEvent e) { search(searchField.getText()); }
        });
        searchPanel.add(searchField);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(controlPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        String[] columns = {"No Kamar", "Jenis", "Harga", "Bintang", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableKamar = new JTable(tableModel);
        tableKamar.setRowHeight(30);
        tableKamar.getTableHeader().setBackground(new Color(200, 190, 230));
        tableKamar.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // PENTING: Renderer untuk bintang EMAS di tabel
        tableKamar.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set warna EMAS untuk bintang
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setForeground(new Color(255, 215, 0)); // EMAS
                    label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
                }

                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        sorter = new TableRowSorter<>(tableModel);
        tableKamar.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tableKamar);

        cardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        cardPanel.setBackground(Color.WHITE);

        JPanel bottomSection = new JPanel(new BorderLayout());
        bottomSection.setBackground(Color.WHITE);
        bottomSection.add(new JLabel("Daftar Kamar (Card)"), BorderLayout.NORTH);
        bottomSection.add(new JScrollPane(cardPanel), BorderLayout.CENTER);

        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBackground(new Color(228, 203, 234));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Pembayaran"));
        paymentPanel.setPreferredSize(new Dimension(220, 0));

        // Tombol dengan icon uang yang lebih jelas
        JButton btnTambahPembayaran = new JButton(" + Tambah Pembayaran");
        btnTambahPembayaran.setBackground(new Color(163, 151, 53));
        btnTambahPembayaran.setForeground(Color.WHITE);
        btnTambahPembayaran.setFocusPainted(false);
        btnTambahPembayaran.setFont(new Font("Arial", Font.BOLD, 14));
        btnTambahPembayaran.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnTambahPembayaran.addActionListener(e -> showPembayaran());
        paymentPanel.add(btnTambahPembayaran, BorderLayout.NORTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomSection, BorderLayout.SOUTH);
        panel.add(paymentPanel, BorderLayout.EAST);

        return panel;
    }

    private void search(String text) {
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void cetakData() {
        try {
            boolean complete = tableKamar.print(
                    JTable.PrintMode.FIT_WIDTH,
                    new MessageFormat("Daftar Kamar - KosNurPurple"),
                    new MessageFormat("Halaman {0}")
            );

            if (complete) {
                JOptionPane.showMessageDialog(this,
                        "Pencetakan selesai!",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pencetakan dibatalkan",
                        "Info",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saat mencetak: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    private void loadDataKamar() {
        tableModel.setRowCount(0);
        cardPanel.removeAll();

        List<Kamar> kamarList = kosManager.getDaftarKamar();

        for (Kamar kamar : kamarList) {
            String bintang = "";
            for (int i = 0; i < kamar.getRating(); i++) {
                bintang += "⭐";
            }

            Object[] row = {
                    kamar.getNoKamar(),
                    kamar.getJenisKamar(),
                    String.format("Rp%.0f", kamar.getHarga()),
                    bintang,
                    kamar.getStatus()
            };
            tableModel.addRow(row);

            JPanel card = createRoomCard(kamar);
            cardPanel.add(card);
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private JPanel createRoomCard(Kamar kamar) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(150, 180));
        card.setBorder(BorderFactory.createLineBorder(new Color(228, 203, 234), 1));
        card.setBackground(Color.WHITE);

        JLabel imgLabel;
        String noKamar = kamar.getNoKamar();

        if (roomImages.containsKey(noKamar)) {
            ImageIcon icon = loadImageFromFile(roomImages.get(noKamar), 150, 100);
            if (icon != null) {
                imgLabel = new JLabel(icon);
            } else {
                imgLabel = new JLabel("🏠", SwingConstants.CENTER);
                imgLabel.setFont(new Font("Arial", Font.PLAIN, 40));
            }
        } else {
            imgLabel = new JLabel("🏠", SwingConstants.CENTER);
            imgLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        }

        imgLabel.setPreferredSize(new Dimension(150, 100));
        imgLabel.setBackground(new Color(228, 203, 234));
        imgLabel.setOpaque(true);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblNama = new JLabel(kamar.getNoKamar());
        lblNama.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblHarga = new JLabel(String.format("Rp%.0f", kamar.getHarga()));
        lblHarga.setForeground(new Color(76, 175, 80));
        lblHarga.setFont(new Font("Arial", Font.BOLD, 12));

        // BINTANG EMAS DI CARD - Pakai HTML untuk warna
        String starsHtml = "<html><span style='color:#FFD700; font-size:16px;'>";
        for (int i = 0; i < kamar.getRating(); i++) {
            starsHtml += "⭐";
        }
        starsHtml += "</span></html>";
        JLabel lblRating = new JLabel(starsHtml);

        JButton statusBtn = new JButton(kamar.getStatus());
        statusBtn.setFont(new Font("Arial", Font.BOLD, 10));
        statusBtn.setMaximumSize(new Dimension(140, 25));
        if (kamar.getStatus().equals("Tersedia")) {
            statusBtn.setBackground(new Color(76, 175, 80));
        } else {
            statusBtn.setBackground(new Color(244, 67, 54));
        }
        statusBtn.setForeground(Color.WHITE);
        statusBtn.setFocusPainted(false);

        infoPanel.add(lblNama);
        infoPanel.add(lblHarga);
        infoPanel.add(lblRating);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(statusBtn);

        card.add(imgLabel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);

        return card;
    }

    private void handleMenuClick(String menu) {
        switch (menu) {
            case "Dashboard":
            case "Data Kamar":
            case "Daftar Kamar":
                loadDataKamar();
                break;
            case "Data Penyewa":
                showDataPenyewa();
                break;
            case "Pembayaran":
                showPembayaran();
                break;
            case "Riwayat Pembayaran":
                showRiwayat();
                break;
            case "Logout":
                new LoginFrame().setVisible(true);
                dispose();
                break;
        }
    }

    private void showTambahPenyewa() {
        new TambahPenyewaDialog(this, kosManager).setVisible(true);
        loadDataKamar();
    }

    private void showDataPenyewa() {
        JDialog dialog = new JDialog(this, "Data Penyewa", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        String[] columns = {"ID", "Nama", "No Telepon", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Penyewa p : kosManager.getDaftarPenyewa()) {
            model.addRow(new Object[]{p.getId(), p.getNama(), p.getNoTelepon(), p.getEmail()});
        }

        JTable table = new JTable(model);
        dialog.add(new JScrollPane(table));
        dialog.setVisible(true);
    }

    private void showPembayaran() {
        new PembayaranDialog(this, kosManager).setVisible(true);
    }

    private void showRiwayat() {
        new RiwayatDialog(this, kosManager).setVisible(true);
    }
}