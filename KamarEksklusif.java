// PEWARISAN: Mewarisi dari class Kamar
// POLIMORFISME: Override method abstract dari parent class

package model;

public class KamarEksklusif extends Kamar {

    public KamarEksklusif(String noKamar, double harga, String imagePath) {
        super(noKamar, harga, imagePath);
    }

    // POLIMORFISME: Override method dari parent
    @Override
    public String getJenisKamar() {
        return "Eksklusif";
    }

    @Override
    public String getFasilitas() {
        return "Kasur King, Lemari, Meja, AC, Kamar Mandi Dalam, TV";
    }
}