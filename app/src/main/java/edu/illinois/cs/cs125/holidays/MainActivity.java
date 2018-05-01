package edu.illinois.cs.cs125.holidays;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Main class for our UI design lab.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Holidays:Main";

    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    String requestURL = "";

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        final Button check = findViewById(R.id.checkButton);
        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Check Button clicked");

                final EditText country = findViewById(R.id.inputCountry);
                final EditText year = findViewById(R.id.inputYear);
                final EditText month = findViewById(R.id.inputMonth);
                final EditText day = findViewById(R.id.inputDay);

                requestURL = Uri.parse("https://holidayapi.com/v1/holidays?")
                        .buildUpon()
                        .appendQueryParameter("key", BuildConfig.API_KEY)
                        .appendQueryParameter("country", country.getText().toString())
                        .appendQueryParameter("year", year.getText().toString())
                        .appendQueryParameter("month", month.getText().toString())
                        .appendQueryParameter("day", day.getText().toString())
                        .build()
                        .toString();

                startAPICall();
            }
        });
    }

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void finishSet(String response) {
        TextView output = findViewById(R.id.output);
        output.setText(Holidays.name(response));
        output.setTextColor(Color.BLACK);
    }

    /**
     * Make a call to the holiday API.
     */
    void startAPICall() {
        Log.d(TAG, "Using URL: " + requestURL);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, requestURL,
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            finishSet(response.toString());
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

