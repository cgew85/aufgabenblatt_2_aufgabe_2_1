package io.github.cgew85.appvma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import io.github.cgew85.appvma.model.Verspaetung;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Verspaetung> verspaetungen = new ArrayList<>();

    private RadioButton radioButtonMorgens;
    private RadioButton radioButtonAbends;

    private SeekBar seekBar;

    private Button buttonSave;
    private Button buttonReset;
    private TextView textViewSummeMorgens;
    private TextView textViewSummeAbends;
    private TextView textViewAnzahlMorgens;
    private TextView textViewAnzahlAbends;
    private TextView textViewDurchscnittMorgens;
    private TextView textViewDurchscnittAbends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
    }

    /**
     * Click listener for the save button
     *
     * @param view the current view
     */
    public void onButtonSaveClicked(View view) {
        Verspaetung verspaetung = new Verspaetung(
                seekBar.getProgress(),
                radioButtonMorgens.isChecked(),
                radioButtonAbends.isChecked());

        verspaetungen.add(verspaetung);

        // adjust seek bar
        seekBar.setProgress(0);

        // calculations
        List<Verspaetung> verspaetungenMorgens = new ArrayList<>();
        List<Verspaetung> verspaetungenAbends = new ArrayList<>();

        for (Verspaetung v : verspaetungen) {
            if (v.isMorgens()) {
                verspaetungenMorgens.add(v);
            } else {
                verspaetungenAbends.add(v);
            }
        }

        textViewSummeMorgens.setText(getString(R.string.sum_morgens) + " " + getSumme(verspaetungenMorgens) + " ");
        textViewSummeAbends.setText(getString(R.string.sum_abends) + " " + getSumme(verspaetungenAbends) + " ");
        textViewAnzahlMorgens.setText(getString(R.string.anzahl_morgens) + " " + verspaetungenMorgens.size() + " ");
        textViewAnzahlAbends.setText(getString(R.string.anzahl_abends) + " " + verspaetungenAbends.size() + " ");
        textViewDurchscnittMorgens.setText(getString(R.string.average_morgens) + " " + getAverage(verspaetungenMorgens) + " ");
        textViewDurchscnittAbends.setText(getString(R.string.average_abends) + " " + getAverage(verspaetungenAbends) + " ");
    }

    /**
     * Click listener for the reset button
     *
     * @param view the current view
     */
    public void onButtonResetClicked(View view) {
        seekBar.setProgress(0);
        textViewSummeMorgens.setText(getString(R.string.sum_morgens));
        textViewSummeAbends.setText(getString(R.string.sum_abends));
        textViewAnzahlMorgens.setText(getString(R.string.anzahl_morgens));
        textViewAnzahlAbends.setText(getString(R.string.anzahl_abends));
        textViewDurchscnittMorgens.setText(getString(R.string.average_morgens));
        textViewDurchscnittAbends.setText(getString(R.string.average_abends));
        verspaetungen.clear();
    }

    /**
     * Reset counter to zero on radio button toggle
     *
     * @param view the current view
     */
    public void onRadioButtonClicked(View view) {
        seekBar.setProgress(0);
    }

    private String getAverage(List<Verspaetung> verspaetungen) {
        if(verspaetungen.isEmpty())
            return "0";

        // Auf zwei Nachkommastellen runden
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        double sum = 0;
        for (Verspaetung verspaetung : verspaetungen) {
            sum += verspaetung.getMinutes();
        }

        return df.format(sum / verspaetungen.size());
    }

    private int getSumme(List<Verspaetung> verspaetungen) {
        int summe = 0;
        for (Verspaetung verspaetung : verspaetungen) {
            summe += verspaetung.getMinutes();
        }

        return summe;
    }

    private void initWidgets() {
        radioButtonMorgens = findViewById(R.id.radio_morgens);
        radioButtonAbends = findViewById(R.id.radio_abends);

        seekBar = findViewById(R.id.simpleSeekBar);
        initSeekBar();

        buttonSave = findViewById(R.id.button_send);
        textViewSummeMorgens = findViewById(R.id.summorgens);
        textViewSummeAbends = findViewById(R.id.sumabends);
        textViewAnzahlMorgens = findViewById(R.id.anzahlmorgens);
        textViewAnzahlAbends = findViewById(R.id.anzahlabends);
        textViewDurchscnittMorgens = findViewById(R.id.averagemorgens);
        textViewDurchscnittAbends = findViewById(R.id.averageabends);
        buttonReset = findViewById(R.id.button_reset);
    }

    private void initSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, progressChangedValue + " Minuten Verspätung",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
