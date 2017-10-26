package be.vdab.training.enums;

/**
 * Created by admin on 29/09/2017.
 */
public enum RijbewijsType {
    MOTOR("Type_A"),
    AUTO("Type_B"),
    VRACHTWAGEN("Type_C");

    private final String omschrijving;

    RijbewijsType(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
