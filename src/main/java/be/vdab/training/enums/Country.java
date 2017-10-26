package be.vdab.training.enums;

/**
 * Created by admin on 19/10/2017.
 */
public enum Country {
    BELGIUM(1, "Belg"),
    NETHERLANDS(2, "Nederlander"),
    GERMANY(3, "Germany");

    private final int countryIndex;
    private final String nationality;

    Country(int countryIndex, String nationality) {
         this.countryIndex = countryIndex;
         this.nationality = nationality;
    }

    public int getCountryIndex() {
        return countryIndex;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryIndex=" + countryIndex +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
