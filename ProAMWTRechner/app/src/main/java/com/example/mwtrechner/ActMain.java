package com.example.mwtrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class ActMain extends AppCompatActivity {
    static final boolean DBG        = true;
    static final String TAG         = "ActMain";
    static final String KEY_BRUTTO  = "brutto";
    static final String KEY_NETTO   = "netto";
    static final String KEY_STEUER  = "steuer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_main);
    }

        public void onClickBerechnen ( final View cmd ){


            final String MNAME = "onClickBerechnen()";
            if (DBG) Log.v(TAG, MNAME + "entering...");

            double dBrutto = 0.0, dNetto = 0.0, dSteuer = 0.0, dBetrag = 0.0;

            // Betrag:
            final EditText txtBetrag = findViewById(R.id.txtBetrag);
            String s = txtBetrag.getText().toString();
            if ((s != null) && (s.length() > 0)) dBetrag = Double.parseDouble(s);

            // MwSt-Satz:
            final Spinner spiMwstSatz = findViewById(R.id.spiMwstSatz);
            final int[] anProzente = getResources().getIntArray(R.array.mwstWerte);
    /* Variante:
     * final int mwst = (Integer) spiMwstSatz.getItemAtPosition(
                                  spiMwstSatz.getSelectedItemPosition() ); */
            final double dMwst =
                    ((double) anProzente[spiMwstSatz.getSelectedItemPosition()]) / 100;

            // Brutto oder Netto?
            final RadioGroup rg = findViewById(R.id.rbgBruttoNetto);
            if (rg.getCheckedRadioButtonId() == R.id.radNetto) {
                dNetto = dBetrag;
                dSteuer = dNetto * dMwst;
                dBrutto = dNetto + dSteuer;
            } else {
                // Brutto:
                dBrutto = dBetrag;
                dNetto = dBrutto / (1 + dMwst);
                dSteuer = dBrutto - dNetto;
            }

            final Intent intent = new Intent(this, ActErgebnis.class);

            // Daten an Activity Ergebnis übergeben:
            intent.putExtra(KEY_BRUTTO, dBrutto);
            intent.putExtra(KEY_NETTO, dNetto);
            intent.putExtra(KEY_STEUER, dSteuer);

            // Activity Ergebnis aufrufen:
            startActivity(intent);

            if (DBG) Log.d(TAG, MNAME + "...exiting");
        }
        }


