import gui.LoginFrame;
import javax.swing.*;

/**
 * Sistem Manajemen Kos
 *
 * Penerapan 3 Konsep OOP:
 *
 * 1. ENKAPSULASI (Encapsulation):
 *    - Semua atribut di class model (Kamar, Penyewa, Pembayaran) bersifat private
 *    - Akses ke atribut hanya melalui getter dan setter
 *    - Data collections di KosManager di-enkapsulasi sebagai private
 *
 * 2. PEWARISAN (Inheritance):
 *    - Class abstract Kamar sebagai parent class
 *    - KamarStandar, KamarAC, dan KamarEksklusif mewarisi dari Kamar
 *    - Subclass mewarisi semua atribut dan method dari parent
 *
 * 3. POLIMORFISME (Polymorphism):
 *    - Method abstract getJenisKamar() dan getFasilitas() di class Kamar
 *    - Setiap subclass mengimplementasi method tersebut dengan cara berbeda
 *    - List<Kamar> dapat menyimpan semua tipe kamar (KamarStandar, KamarAC, KamarEksklusif)
 *    - Saat memanggil getJenisKamar(), method yang dieksekusi sesuai dengan tipe objek aktual
 *
 * Fitur Aplikasi:
 * - Login System
 * - Dashboard dengan tabel dan card view
 * - Tambah Penyewa
 * - Sewa Kamar
 * - Proses Pembayaran
 * - Riwayat Pembayaran dengan filter dan summary
 */
public class Main {
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Jalankan aplikasi
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}