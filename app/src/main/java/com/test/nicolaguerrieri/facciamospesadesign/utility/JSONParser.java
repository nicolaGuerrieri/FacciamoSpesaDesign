package com.test.nicolaguerrieri.facciamospesadesign.utility;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSONParser extends AsyncTask<String, Integer, List<String>> {

    @Override
    protected void onPostExecute(List<String> result) {
        super.onPostExecute(result);

    }

    @Override
    protected List<String> doInBackground(String... urls) {
        List<String> lista = new ArrayList<>();
        try {
            URL u = new URL(urls[0]);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");

            conn.connect();
            InputStream is = conn.getInputStream();

            // Read the stream
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.read(b) != -1)
                baos.write(b);

            String JSONResp = new String(baos.toByteArray());

            JSONObject objectJson = new JSONObject(JSONResp);

            //JSONArray arr = new JSONArray(JSONResp);

            JSONArray arr = objectJson.getJSONArray("negozi");
            Log.d(" ", arr.length() + "");
            for (int i = 0; i < arr.length(); i++) {
                lista.add(arr.get(i).toString());
                //lista.add(arr.getJSONObject(i).tString());
            }

            return lista;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return lista;
    }

    private String convertContact(JSONObject obj) throws JSONException {
        String name = obj.getString("name");

        return "";
    }

    protected void onProgressUpdate(Integer... progress) {
        //    setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
}
