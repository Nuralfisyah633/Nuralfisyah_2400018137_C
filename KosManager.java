// ENKAPSULASI: Data management dengan private collections
// POLIMORFISME: Menggunakan List<Kamar> untuk semua jenis kamar

package manager;

import model.*;
import java.util.*;

public class KosManager {

    // ENKAPSULASI: Private data collections
    private List<Kamar> daftarKamar;
    private List<Penyewa> daftarPenyewa;
    private List<Pembayaran> daftarPembayaran;
    private Map<String, String> penyewaKamar; // noKamar -> idPenyewa

    public KosManager() {
        daftarKamar = new ArrayList<>();
        daftarPenyewa = new ArrayList<>();
        daftarPembayaran = new ArrayList<>();
        penyewaKamar = new HashMap<>();
        initializeData();
    }

    // POLIMORFISME: List dapat menyimpan berbagai tipe Kamar
    private void initializeData() {
        daftarKamar.add(new KamarStandar("A1", 500000, "Tersedia"));
        daftarKamar.add(new KamarAC("B1", 600000, "Tersedia"));
        daftarKamar.add(new KamarEksklusif("C1", 700000, "Tersedia"));
        daftarKamar.add(new KamarStandar("D1", 450000, "Tersedia"));
        daftarKamar.add(new KamarStandar("E1", 550000, "Tersedia"));
    }

    // === KAMAR METHODS ===
    public List<Kamar> getDaftarKamar() {
        return new ArrayList<>(daftarKamar);
    }

    public void tambahKamar(Kamar kamar) {
        daftarKamar.add(kamar);
    }

    public Kamar cariKamar(String noKamar) {
        for (Kamar k : daftarKamar) {
            if (k.getNoKamar().equals(noKamar)) {
                return k;
            }
        }
        return null;
    }

    public List<Kamar> getKamarTersedia() {
        List<Kamar> tersedia = new ArrayList<>();
        for (Kamar k : daftarKamar) {
            if ("Tersedia".equals(k.getStatus())) {
                tersedia.add(k);
            }
        }
        return tersedia;
    }

    // === PENYEWA METHODS ===
    public void tambahPenyewa(Penyewa penyewa) {
        daftarPenyewa.add(penyewa);
    }

    public List<Penyewa> getDaftarPenyewa() {
        return new ArrayList<>(daftarPenyewa);
    }

    public Penyewa cariPenyewa(String id) {
        for (Penyewa p : daftarPenyewa) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // === SEWA KAMAR ===
    public boolean sewaKamar(String noKamar, String idPenyewa) {
        Kamar kamar = cariKamar(noKamar);
        Penyewa penyewa = cariPenyewa(idPenyewa);

        if (kamar != null && penyewa != null && "Tersedia".equals(kamar.getStatus())) {
            kamar.setStatus("Terisi");
            penyewaKamar.put(noKamar, idPenyewa);
            return true;
        }
        return false;
    }

    public String getPenyewaKamar(String noKamar) {
        return penyewaKamar.get(noKamar);
    }

    // === PEMBAYARAN METHODS ===
    public void tambahPembayaran(Pembayaran pembayaran) {
        daftarPembayaran.add(pembayaran);
    }

    public List<Pembayaran> getDaftarPembayaran() {
        return new ArrayList<>(daftarPembayaran);
    }

    public List<Pembayaran> getPembayaranByPenyewa(String idPenyewa) {
        List<Pembayaran> result = new ArrayList<>();
        for (Pembayaran p : daftarPembayaran) {
            if (p.getIdPenyewa().equals(idPenyewa)) {
                result.add(p);
            }
        }
        return result;
    }

    // === RIWAYAT ===
    public Map<String, Object> getRiwayatKamar(String noKamar) {
        Map<String, Object> riwayat = new HashMap<>();
        Kamar kamar = cariKamar(noKamar);

        if (kamar != null) {
            riwayat.put("kamar", kamar);

            String idPenyewa = penyewaKamar.get(noKamar);
            if (idPenyewa != null) {
                riwayat.put("penyewa", cariPenyewa(idPenyewa));

                List<Pembayaran> pembayaran = new ArrayList<>();
                for (Pembayaran p : daftarPembayaran) {
                    if (p.getNoKamar().equals(noKamar)) {
                        pembayaran.add(p);
                    }
                }
                riwayat.put("pembayaran", pembayaran);
            }
        }
        return riwayat;
    }

    // === GENERATOR ID ===
    public String generateIdPenyewa() {
        return "P" + String.format("%03d", daftarPenyewa.size() + 1);
    }

    public String generateIdPembayaran() {
        return "PAY" + String.format("%03d", daftarPembayaran.size() + 1);
    }
}
