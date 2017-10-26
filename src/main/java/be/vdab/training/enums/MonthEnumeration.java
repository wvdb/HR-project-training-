package be.vdab.training.enums;

import java.util.Arrays;

/**
 * Created by admin on 22/09/2017.
 */
//public enum MonthEnumeration extends Enum {
public enum MonthEnumeration {
    JANUARY(new String[]{"januari", "janvier", "January"}, 1, 31),
    FEBRUARY(new String[]{"februari", "fevrier", "February"}, 2, 28),
    NOVEMBER(new String[]{"november", "novembre", "November"}, 11, 30),
    DECEMBER(new String[]{"december", "decembre", "December"}, 12, 31);

    private String[] monthLanguages;
    private int index;
    private int daysInMonth;

    private MonthEnumeration(String[] monthLanguages, int index, int daysInMonth) {
        this.monthLanguages = monthLanguages;
        this.index = index;
        this.daysInMonth = daysInMonth;
    }

    public int getIndex() {
        return index;
    }

    public String[] getMonthLanguages() {
        return monthLanguages;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    // we don't support the mutator method

    @Override
    public String toString() {
        return "MonthEnumeration{" +
                "monthLanguages=" + Arrays.toString(monthLanguages) +
                ", index=" + index +
                ", daysInMonth=" + daysInMonth +
                '}';
    }
}
