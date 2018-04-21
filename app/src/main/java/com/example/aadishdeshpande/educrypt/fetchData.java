package com.example.aadishdeshpande.educrypt;

/**
 * Created by Aadish Deshpande on 4/13/2018.
 */

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    static double x,y;
    static long time[] = new long[35];
    static double high[] = new double[35];
    double high_sum = 0.0;
    double low_sum = 0.0;
    static double low[] = new double[35];
    static  double avg = 0.0;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }



            JSONObject jsonObject = new JSONObject(data);
            JSONArray JA = jsonObject.optJSONArray("Data");
            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = JA.getJSONObject(i);
                x = JO.getDouble("high");
                high[i] = x;
                high_sum = high_sum + x;
                y = JO.getDouble("low");
                low_sum = low_sum + y;
                low[i] = y;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        avg = (low_sum + high_sum)/60;
    }
}