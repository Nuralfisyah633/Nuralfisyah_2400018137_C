// ENKAPSULASI: Semua atribut private dengan getter/setter

package model;

public class Penyewa {
    private String id;
    private String nama;
    private String noTelepon;
    private String alamat;
    private String email;

    public Penyewa(String id, String nama, String noTelepon, String alamat, String email) {
        this.id = id;
        this.nama = nama;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
        this.email = email;
    }

    // ENKAPSULASI: Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nama + " (" + id + ")";
    }
}