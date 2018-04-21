package com.example.aadishdeshpande.educrypt;

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

/**
 * Created by Aadish Deshpande on 4/13/2018.
 */

public class fetchDataLtc extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    static double high = 0.0;
    static double low = 0.0;
    static double high_avg = 0.0;
    static double low_avg = 0.0;
    static double avg = 0.0;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://min-api.cryptocompare.com/data/histoday?fsym=ETH&tsym=USD&limit=30&aggregate=1&e=CCCAGG");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            /*JSONArray JA = new JSONArray(data);
            for(int i =2 ;i <JA.length(); i++){
                JSONObject JO = JA.getJSONObject(i);
                singleParsed =  "High:" + JO.get("high") + "\n"+
                        "Low:" + JO.get("low") + "\n"+
                        "Time:" + JO.get("time") + "\n"+
                        "Open:" + JO.get("open") + "\n";

                dataParsed = dataParsed + singleParsed +"\n" ;


            }*/

            JSONObject jsonObject = new JSONObject(data);
            JSONArray JA = jsonObject.optJSONArray("Data");
            for(int i =0 ;i <JA.length(); i++) {
                JSONObject JO = JA.getJSONObject(i);

                high += JO.getDouble("high");
                low  += JO.getDouble("low");

                dataParsed = dataParsed + singleParsed +"\n" ;
            }


            high_avg = high/JA.length();
            low_avg = low/JA.length();
            avg = (high_avg + low_avg)/2;

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

    }
}
