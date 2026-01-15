// ENKAPSULASI: Semua atribut private dengan getter/setter
// PEWARISAN: Class abstract untuk diwarisi oleh subclass
// POLIMORFISME: Method abstract yang akan di-override

package model;

public abstract class Kamar {
    private String noKamar;
    private double harga;
    private String status; // "Tersedia", "Terisi"
    private int rating;
    private String imagePath;

    // ENKAPSULASI: Constructor
    public Kamar(String noKamar, double harga, String imagePath) {
        this.noKamar = noKamar;
        this.harga = harga;
        this.status = "Tersedia";
        this.rating = 5;
        this.imagePath = imagePath;
    }

    // POLIMORFISME: Method abstract yang harus diimplementasi subclass
    public abstract String getJenisKamar();
    public abstract String getFasilitas();

    // ENKAPSULASI: Getter dan Setter
    public String getNoKamar() {
        return noKamar;
    }

    public void setNoKamar(String noKamar) {
        this.noKamar = noKamar;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Method untuk menampilkan info kamar
    public String getInfo() {
        return String.format("%s - %s - Rp%.2f - %s",
                noKamar, getJenisKamar(), harga, status);
    }
}