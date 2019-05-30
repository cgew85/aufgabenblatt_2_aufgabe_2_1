package io.github.cgew85.appvma.model;

/**
 * Created by cgew85 on 30.05.2019.
 */
final public class Verspaetung {
    final private int minutes;
    final private boolean isMorgens;
    final private boolean isAbends;

    public Verspaetung(int minutes, boolean isMorgens, boolean isAbends) {
        if(isMorgens == isAbends)
            throw new IllegalArgumentException("Muss ungleich sein");
        this.minutes = minutes;
        this.isMorgens = isMorgens;
        this.isAbends = isAbends;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean isMorgens() {
        return isMorgens;
    }

    public boolean isAbends() {
        return isAbends;
    }
}
