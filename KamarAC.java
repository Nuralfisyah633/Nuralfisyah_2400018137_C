// PEWARISAN: Mewarisi dari class Kamar
// POLIMORFISME: Override method abstract dari parent class

package model;

public class KamarAC extends Kamar {

    public KamarAC(String noKamar, double harga, String imagePath) {
        super(noKamar, harga, imagePath);
    }

    // POLIMORFISME: Override method dari parent
    @Override
    public String getJenisKamar() {
        return "AC";
    }

    @Override
    public String getFasilitas() {
        return "Kasur, Lemari, Meja, AC";
    }
}