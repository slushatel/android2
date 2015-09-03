package com.example.stas.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by stas on 02.09.2015.
 */
class LongOperation extends AsyncTask<String, Void, FunctionResult<String>> {

    private MainActivity mainActivity;
    private String Content;
    private String Error = null;
    private ProgressDialog Dialog = null;
    String data = "";
    int sizeData = 0;
    String serverText = "";


    public LongOperation(MainActivity mainActivity, String serverText) {
        this.mainActivity = mainActivity;
        this.serverText = serverText;
        this.Dialog = new ProgressDialog(mainActivity);
    }

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)
        Dialog.setMessage("Please wait..");
        Dialog.show();

        try {
            // Set Request parameter
            data += "&" + URLEncoder.encode("data", "UTF-8") + "=" + serverText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Call after onPreExecute method
    protected FunctionResult<String> doInBackground(String... urls) {
        BufferedReader reader = null;

        // Send data
        try {
//                TimeUnit.SECONDS.sleep(5);

            // Defined URL  where to send data
            URL url = new URL(urls[0]);

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            // Append Server Response To Content String
            Content = sb.toString();
        } catch (Exception ex) {
            Error = ex.getMessage();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }

        return new FunctionResult<String>(Content, Error);
    }

    protected void onPostExecute(FunctionResult<String> result) {
        // NOTE: You can call UI Element here.

        Dialog.dismiss();
    }

}
