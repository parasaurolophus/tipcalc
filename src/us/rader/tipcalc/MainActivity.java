package us.rader.tipcalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;

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

            float percent = Float.parseFloat(s) / 100.0f;
            s = billText.getText().toString();

            if (EMPTY.equals(s)) {

                payText.setText(EMPTY);
                tipText.setText(EMPTY);
                return;

            }

            float bill = Float.parseFloat(s.toString());
            float tip = bill * percent;
            float pay = bill + tip;
            tipText.setText(getString(R.string.monetary_format, tip));
            payText.setText(getString(R.string.monetary_format, pay));

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
     * Empty {@link String}
     */
    private static final String EMPTY = ""; //$NON-NLS-1$

    /**
     * {@link EditText} for the bill amount
     */
    private EditText            billText;

    /**
     * {@link EditText} for the amount to pay, including tip
     */
    private EditText            payText;

    /**
     * {@link EditText} for the tip percent
     */
    private EditText            percentText;

    /**
     * {@link EditText} for the amount of the tip
     */
    private EditText            tipText;

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
        payText = (EditText) findViewById(R.id.pay_text);
        percentText = (EditText) findViewById(R.id.percent_text);
        tipText = (EditText) findViewById(R.id.tip_text);
        TipTextWatcher watcher = new TipTextWatcher();
        billText.addTextChangedListener(watcher);
        percentText.addTextChangedListener(watcher);

    }

}
