// ENKAPSULASI: Semua atribut private dengan getter/setter

package model;

import java.time.LocalDate;

public class Pembayaran {
    private String id;
    private String idPenyewa;
    private String noKamar;
    private double jumlah;
    private LocalDate tanggal;
    private String status; // "Lunas", "Pending"
    private String metodePembayaran;

    public Pembayaran(String id, String idPenyewa, String noKamar,
                      double jumlah, LocalDate tanggal, String status, String metodePembayaran) {
        this.id = id;
        this.idPenyewa = idPenyewa;
        this.noKamar = noKamar;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.status = status;
        this.metodePembayaran = metodePembayaran;
    }

    // ENKAPSULASI: Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPenyewa() {
        return idPenyewa;
    }

    public void setIdPenyewa(String idPenyewa) {
        this.idPenyewa = idPenyewa;
    }

    public String getNoKamar() {
        return noKamar;
    }

    public void setNoKamar(String noKamar) {
        this.noKamar = noKamar;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
}