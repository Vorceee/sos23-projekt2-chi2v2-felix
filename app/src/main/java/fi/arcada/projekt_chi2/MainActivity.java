package fi.arcada.projekt_chi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Deklarera 4 Button-objekt
    Button btn1, btn2, btn3, btn4, resetButton;
    // Deklarera 4 heltalsvariabler för knapparnas värden
    int val1, val2, val3, val4;

    TextView outputText, percentage1And2Text, percentage3And4Text;

    SharedPreferences sharedPref;
    SharedPreferences.Editor prefEditor;

    TextView kolumn1;
    TextView kolumn2;
    TextView row1;
    TextView row2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Koppla samman Button-objekten med knapparna i layouten
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        resetButton = findViewById(R.id.resetButton);

        outputText = findViewById(R.id.outputText);
        percentage1And2Text = findViewById(R.id.percentage1And2Text);
        percentage3And4Text = findViewById(R.id.percentage3And4Text);
        kolumn1 = findViewById(R.id.textViewCol1);
        kolumn2 = findViewById(R.id.textViewCol2);
        row1 = findViewById(R.id.textViewRow1);
        row2 = findViewById(R.id.textViewRow2);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = sharedPref.edit();

        kolumn1.setText(sharedPref.getString("kolumn1", "barn"));
        kolumn2.setText(sharedPref.getString("kolumn2", "vuxna"));
        row1.setText(sharedPref.getString("row1", "spelar"));
        row2.setText(sharedPref.getString("row2", "spelar inte"));

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCounters(v);
            }
        });

    }

    /**
     *  Klickhanterare för knapparna
     */
    public void buttonClick(View view) {

        // Skapa ett Button-objekt genom att type-casta (byta datatyp)
        // på det View-objekt som kommer med knapptrycket
        Button btn = (Button) view;

        // Kontrollera vilken knapp som klickats, öka värde på rätt vaiabel
        if (view.getId() == R.id.button1) val1++;
        if (view.getId() == R.id.button2) val2++;
        if (view.getId() == R.id.button3) val3++;
        if (view.getId() == R.id.button4) val4++;

        // Slutligen, kör metoden som ska räkna ut allt!
        calculate();
        calculatePercentage1And2();
        calculatePercentage3And4();
    }

    /**
     * Metod som uppdaterar layouten och räknar ut själva analysen.
     */
    public void calculate() {

        // Uppdatera knapparna med de nuvarande värdena
        btn1.setText(String.valueOf(val1));
        btn2.setText(String.valueOf(val2));
        btn3.setText(String.valueOf(val3));
        btn4.setText(String.valueOf(val4));

        // Mata in värdena i Chi-2-uträkningen och ta emot resultatet
        // i en Double-variabel
        double chi2 = Significance.chiSquared(val1, val3, val2, val4);

        // Mata in chi2-resultatet i getP() och ta emot p-värdet
        double pValue = Significance.getP(chi2);

        String result = "Chi-Square Value: " + String.format("%.2f", chi2) +
                "\nSignifikans nivå: " + sharedPref.getString("signnifikans", "0,05") +
                "\nP-Value: " + String.format("%.4f", pValue );

        outputText.setText(result);

        /**
         *  - Visa chi2 och pValue åt användaren på ett bra och tydligt sätt!
         *
         *  - Visa procentuella andelen jakande svar inom de olika grupperna.
         *    T.ex. (val1 / (val1+val3) * 100) och (val2 / (val2+val4) * 100
         *
         *  - Analysera signifikansen genom att jämföra p-värdet
         *    med signifikansnivån, visa reultatet åt användaren
         *
         */

    }

    public void calculatePercentage1And2() {
        int totalCount = val1 + val2;
        double percentage = 0.0;
        if (totalCount > 0) {
            percentage = ((double) val1 / totalCount) * 100;
        }
        percentage1And2Text.setText(String.format("%.2f%%", percentage));
    }

    public void calculatePercentage3And4() {
        int totalCount = val3 + val4;
        double percentage = 0.0;
        if (totalCount > 0) {
            percentage = ((double) val3 / totalCount) * 100;
        }
        percentage3And4Text.setText(String.format("%.2f%%", percentage));
    }

    public void resetCounters(View view) {

        val1 = 0;
        val2 = 0;
        val3 = 0;
        val4 = 0;

        percentage1And2Text.setText("");
        percentage3And4Text.setText("");
        outputText.setText("");

        calculate();
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


}