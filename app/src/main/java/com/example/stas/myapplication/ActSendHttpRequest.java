package com.example.stas.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ActSendHttpRequest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_http_request);
    }

    public void button1Click(View view) {
        final EditText text3 = (EditText) findViewById(R.id.editText1);
        text3.setText("qwe");

        // WebServer Request URL
        String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";

        // Use AsyncTask execute Method To Prevent ANR Problem
        EditText serverText = (EditText) findViewById(R.id.serverText);
        LongOperation mt = new LongOperation(this, serverText.getText().toString());

        mt.execute(serverURL);
        FunctionResult<String> res = null;
        try {
            res = mt.get(10, TimeUnit.SECONDS);
            text3.setText(res.result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (res.error == null){
            TextView uiUpdate = (TextView) findViewById(R.id.output);
            TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);

            String resStr = res.result;

            uiUpdate.setText( resStr );

            /****************** Start Parse Response JSON Data *************/

            ObjectMapper mapper = new ObjectMapper();
            JsonTest u = null;
            try {
                u = mapper.readValue(resStr, JsonTest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String OutputData = "";
            for(int i=0; i < u.Android.size(); i++)
            {
                  OutputData += " Name           : "+ u.Android.get(i).name + "\n"
                        + "Number      : "+ u.Android.get(i).number + "\n"
                        + "Time                : "+ u.Android.get(i).date_added + "\n"
                        + "--------------------------------------------------" + "\n"
                ;
            }
            jsonParsed.setText( OutputData );
        }
    }
}
