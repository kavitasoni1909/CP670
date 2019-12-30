package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "WeatherForecast";
    String urlString = "http://api.openweathermap.org/data/2.5/weather?q=waterloo,ca&APPID=bcbde3d08b824f0f4715bfcd801acdf3&mode=xml&units=metric";

    TextView currentTemperatureTextView;
    TextView minimumTemperatureTextView;
    TextView maximumTemperatureTextView;
    ImageView weatherImage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currentTemperatureTextView = (TextView) findViewById(R.id.currentTempView);
        minimumTemperatureTextView = (TextView) findViewById(R.id.minTempView);
        maximumTemperatureTextView = (TextView) findViewById(R.id.maxTempView);
        weatherImage = (ImageView) findViewById(R.id.weatherImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarView);

        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute();
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        String currentTemperature;
        String maximumTemperature;
        String minimumTemperature;
        String windSpeed;
        Bitmap currentWeatherImage;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                parse(conn.getInputStream());
            } catch (IOException e) {
                Log.e(ACTIVITY_NAME, String.valueOf(e));
            }
            // return conn.getInputStream();
            return null;
        }

        public void parse(InputStream in) throws IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

                int type;
                //While you're not at the end of the document:
                while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
                {
                    //Are you currently at a Start Tag?
                    if(parser.getEventType() == XmlPullParser.START_TAG)
                    {
                        if(parser.getName().equals("temperature") )
                        {
                            currentTemperature = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minimumTemperature = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maximumTemperature = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }
                        else if (parser.getName().equals("weather")) {
                            String iconName = parser.getAttributeValue(null, "icon");
                            String fileName = iconName + ".png";

                            Log.i(ACTIVITY_NAME,"Looking for file: " + fileName);
                            if (fileExistance(fileName)) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(fileName);

                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Log.i(ACTIVITY_NAME,"Found the file locally");
                                currentWeatherImage = BitmapFactory.decodeStream(fis);
                            }
                            else {
                                String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                currentWeatherImage = getImage(new URL(iconUrl));

                                FileOutputStream outputStream = openFileOutput( fileName, Context.MODE_PRIVATE);
                                currentWeatherImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                Log.i(ACTIVITY_NAME,"Downloaded the file from the Internet");
                                outputStream.flush();
                                outputStream.close();
                            }
                            publishProgress(100);
                        }
                        else if (parser.getName().equals("wind")) {
                            parser.nextTag();
                            if(parser.getName().equals("speed") )
                            {
                                windSpeed = parser.getAttributeValue(null, "value");
                            }
                        }
                    }
                    // Go to the next XML event
                    parser.next();
                }
            }
            catch (IOException | XmlPullParserException e) {
                Log.e(ACTIVITY_NAME, String.valueOf(e));
            }
            finally {
                in.close();
            }
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String a) {
            progressBar.setVisibility(View.INVISIBLE);
            weatherImage.setImageBitmap(currentWeatherImage);
            currentTemperatureTextView.setText(currentTemperature + "C\u00b0");
            minimumTemperatureTextView.setText(minimumTemperature + "C\u00b0");
            maximumTemperatureTextView.setText(maximumTemperature + "C\u00b0");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}
