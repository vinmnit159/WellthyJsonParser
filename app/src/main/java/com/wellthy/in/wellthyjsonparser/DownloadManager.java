package com.wellthy.in.wellthyjsonparser;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gulshan.Singh on 29-05-2017.
 */

public class DownloadManager {

    public static String TAG="JSON_DEBUG"+DownloadManager.class.getSimpleName();
    public InputStream in;
    public String downloadJson(String reqUrl){
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder st = new StringBuilder();
            String line;
                while ((line = reader.readLine()) != null) {
                    st.append(line).append('\n');
                }
            response = st.toString();
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        }catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return response;

    }
}
