package com.hacktiv8.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView tvCases,tvDeath, tvRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCases = findViewById(R.id.tvCases);
        tvDeath = findViewById(R.id.tvDeath);
        tvRecovered = findViewById(R.id.tvRecovered);


        new DownloadTask().execute("https://disease.sh/v3/covid-19/all");
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadData(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                CovidData covidData = JsonParser.parseCovidData(result);
                if (covidData != null) {
                    displayData(covidData);
                }
            }
        }

        private String downloadData(String urlString) throws IOException {
            InputStream inputStream = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                inputStream = connection.getInputStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        private void displayData(CovidData covidData) {
            tvCases.setText("Total Cases: " + covidData.cases);
            tvDeath.setText("Total Death: " + covidData.deaths);
            tvRecovered.setText("Total Recovered: " + covidData.recovered);

        }
    }
}