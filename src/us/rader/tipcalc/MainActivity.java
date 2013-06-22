package us.rader.tipcalc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

/**
 * "Classic" approach to Android UI using an {@link Activity} with no fragments
 * 
 * @author Kirk
 */
public class MainActivity extends Activity {

    /**
     * {@link TextWatcher} for {@link MainActivity#billText} and
     * {@link MainActivity#percentText}
     */
    private final class TipTextWatcher implements TextWatcher {

        /**
         * Update {@link MainActivity#tipText} and {@link MainActivity#payText}
         * 
         * @param editable
         *            Ignored
         * 
         * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
         */
        @Override
        public void afterTextChanged(Editable editable) {

            String s;
            s = percentText.getText().toString();

            if (EMPTY.equals(s)) {

                payText.setText(EMPTY);
                tipText.setText(EMPTY);
                return;

            }

            float newPercent = Float.parseFloat(s) / 100.0f;
            s = billText.getText().toString();

            if (EMPTY.equals(s)) {

                payText.setText(EMPTY);
                tipText.setText(EMPTY);
                return;

            }

            float bill = Float.parseFloat(s.toString());
            float tip = bill * newPercent;
            float pay = bill + tip;
            tipText.setText(getString(R.string.monetary_format, tip));
            payText.setText(getString(R.string.monetary_format, pay));

            if (percent != newPercent) {

                percent = newPercent;
                storePercent(percent);

            }
        }

        /**
         * ignored
         * 
         * @param editable
         *            ignored
         * 
         * @param start
         *            ignored
         * 
         * @param count
         *            ignored
         * 
         * @param after
         *            ignored
         * 
         * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
         *      int, int, int)
         */
        @Override
        public void beforeTextChanged(CharSequence editable, int start,
                int count, int after) {

            // ignored

        }

        /**
         * ignored
         * 
         * @param editable
         *            ignored
         * 
         * @param start
         *            ignored
         * @param before
         *            ignored
         * 
         * @param count
         *            ignored
         * 
         * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence,
         *      int, int, int)
         */
        @Override
        public void onTextChanged(CharSequence editable, int start, int before,
                int count) {

            // ignored

        }

    }

    /**
     * Default value or {@link #percent} when no value has yet been stored
     */
    private static final float  DEFAULT_PERCENT   = 0.2f;

    /**
     * Empty {@link String}
     */
    private static final String EMPTY             = "";       //$NON-NLS-1$

    /**
     * Name of the in which to store {@link #percent}
     */
    private static final String STORAGE_FILE_NAME = "percent"; //$NON-NLS-1$

    /**
     * {@link EditText} for the bill amount
     */
    private EditText            billText;

    /**
     * {@link EditText} for the amount to pay, including tip
     */
    private TextView            payText;

    /**
     * Previously stored value for tip percentage
     */
    private float               percent;

    /**
     * {@link EditText} for the tip percent
     */
    private EditText            percentText;

    /**
     * {@link EditText} for the amount of the tip
     */
    private TextView            tipText;

    /**
     * Inflate the options {@link Menu}
     * 
     * @param menu
     *            options {@link Menu}
     * 
     * @return <code>true</code>
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    /**
     * Prepare this {@link Activity} to be displayed
     * 
     * @param savedInstanceState
     *            saved state (e.g. during screen rotation) or <code>null</code>
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        billText = (EditText) findViewById(R.id.bill_text);
        payText = (TextView) findViewById(R.id.pay_text);
        percentText = (EditText) findViewById(R.id.percent_text);
        tipText = (TextView) findViewById(R.id.tip_text);
        TipTextWatcher watcher = new TipTextWatcher();
        billText.addTextChangedListener(watcher);
        percentText.addTextChangedListener(watcher);

    }

    /**
     * Display this instance
     * 
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {

        super.onResume();
        percent = restorePercent();
        int n = (int) (percent * 100.0f);
        percentText.setText(Integer.toString(n));

    }

    /**
     * Get the preciously stored tip percentage
     * 
     * @return restored tip percentage or {@link #DEFAULT_PERCENT}
     */
    private float restorePercent() {

        try {

            FileInputStream inputStream = openFileInput(STORAGE_FILE_NAME);
            DataInputStream dataStream = new DataInputStream(inputStream);

            try {

                return dataStream.readFloat();

            } finally {

                dataStream.close();

            }

        } catch (Exception e) {

            Log.e(getClass().getName(), "restorePercent", e); //$NON-NLS-1$
            return DEFAULT_PERCENT;

        }
    }

    /**
     * Store the given value
     * 
     * @param percent
     *            the value to store
     */
    private void storePercent(float percent) {

        try {

            FileOutputStream outputStream = openFileOutput(STORAGE_FILE_NAME,
                    MODE_PRIVATE);
            DataOutputStream dataStream = new DataOutputStream(outputStream);

            try {

                dataStream.writeFloat(percent);

            } finally {

                dataStream.close();

            }

        } catch (IOException e) {

            Log.e(getClass().getName(), "storePercent", e); //$NON-NLS-1$

        }
    }

}
