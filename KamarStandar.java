// PEWARISAN: Mewarisi dari class Kamar
// POLIMORFISME: Override method abstract dari parent class

package model;

public class KamarStandar extends Kamar {

    public KamarStandar(String noKamar, double harga, String imagePath) {
        super(noKamar, harga, imagePath);
    }

    // POLIMORFISME: Override method dari parent
    @Override
    public String getJenisKamar() {
        return "Standar";
    }

    @Override
    public String getFasilitas() {
        return "Kasur, Lemari, Meja";
    }
}