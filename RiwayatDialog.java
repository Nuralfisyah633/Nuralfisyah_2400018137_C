package gui;

import manager.KosManager;
import model.Pembayaran;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RiwayatDialog extends JDialog {
    private KosManager kosManager;
    private JTable tableRiwayat;
    private DefaultTableModel tableModel;

    public RiwayatDialog(Frame parent, KosManager manager) {
        super(parent, "Riwayat Pembayaran", true);
        this.kosManager = manager;
        setSize(800, 500);
        setLocationRelativeTo(parent);

        initComponents();
        loadRiwayat();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Riwayat Pembayaran");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(63, 81, 181));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(new JLabel("Filter Status:"));
        JComboBox<String> cbFilter = new JComboBox<>(new String[]{
                "Semua", "Lunas", "Pending"
        });
        cbFilter.addActionListener(e -> filterRiwayat((String) cbFilter.getSelectedItem()));
        filterPanel.add(cbFilter);
        headerPanel.add(filterPanel, BorderLayout.EAST);

        // Table
        String[] columns = {"ID", "ID Penyewa", "No Kamar", "Jumlah", "Tanggal", "Metode", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRiwayat = new JTable(tableModel);
        tableRiwayat.setRowHeight(25);
        tableRiwayat.getTableHeader().setBackground(new Color(200, 200, 230));
        tableRiwayat.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tableRiwayat);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        summaryPanel.setBackground(new Color(245, 245, 250));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        summaryPanel.add(createSummaryCard("Total Transaksi", "0", new Color(33, 150, 243)));
        summaryPanel.add(createSummaryCard("Total Lunas", "Rp 0", new Color(76, 175, 80)));
        summaryPanel.add(createSummaryCard("Total Pending", "Rp 0", new Color(255, 152, 0)));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createSummaryCard(String label, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(color, 2));

        JLabel lblTitle = new JLabel(label);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 16));
        lblValue.setForeground(color);
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(lblTitle);
        card.add(lblValue);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private void loadRiwayat() {
        tableModel.setRowCount(0);
        List<Pembayaran> daftarPembayaran = kosManager.getDaftarPembayaran();

        double totalLunas = 0;
        double totalPending = 0;

        for (Pembayaran p : daftarPembayaran) {
            Object[] row = {
                    p.getId(),
                    p.getIdPenyewa(),
                    p.getNoKamar(),
                    String.format("Rp %.0f", p.getJumlah()),
                    p.getTanggal().toString(),
                    p.getMetodePembayaran(),
                    p.getStatus()
            };
            tableModel.addRow(row);

            if (p.getStatus().equals("Lunas")) {
                totalLunas += p.getJumlah();
            } else {
                totalPending += p.getJumlah();
            }
        }

        updateSummary(daftarPembayaran.size(), totalLunas, totalPending);
    }

    private void filterRiwayat(String status) {
        tableModel.setRowCount(0);
        List<Pembayaran> daftarPembayaran = kosManager.getDaftarPembayaran();

        double totalLunas = 0;
        double totalPending = 0;
        int count = 0;

        for (Pembayaran p : daftarPembayaran) {
            if (status.equals("Semua") || p.getStatus().equals(status)) {
                Object[] row = {
                        p.getId(),
                        p.getIdPenyewa(),
                        p.getNoKamar(),
                        String.format("Rp %.0f", p.getJumlah()),
                        p.getTanggal().toString(),
                        p.getMetodePembayaran(),
                        p.getStatus()
                };
                tableModel.addRow(row);
                count++;
            }

            if (p.getStatus().equals("Lunas")) {
                totalLunas += p.getJumlah();
            } else {
                totalPending += p.getJumlah();
            }
        }

        updateSummary(count, totalLunas, totalPending);
    }

    private void updateSummary(int total, double lunas, double pending) {
        // Update summary cards
        Component[] components = ((JPanel) ((JPanel) getContentPane().getComponent(0))
                .getComponent(2)).getComponents();

        if (components.length >= 3) {
            updateCardValue((JPanel) components[0], String.valueOf(total));
            updateCardValue((JPanel) components[1], String.format("Rp %.0f", lunas));
            updateCardValue((JPanel) components[2], String.format("Rp %.0f", pending));
        }
    }

    private void updateCardValue(JPanel card, String value) {
        Component[] components = card.getComponents();
        for (Component c : components) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                if (label.getFont().getStyle() == Font.BOLD) {
                    label.setText(value);
                    break;
                }
            }
        }
    }
}