package com.example.stas.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////////////////

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        // инициализация
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
        // название вкладки
        tabSpec.setIndicator("Вкладка 1");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab1);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        // указываем название и картинку
        // в нашем случае вместо картинки идет xml-файл,
        // который определяет картинку по состоянию вкладки
        tabSpec.setIndicator("Вкладка 2", getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        // создаем View из layout-файла
        View v = getLayoutInflater().inflate(R.layout.tab_header, null);
        // и устанавливаем его, как заголовок
        tabSpec.setIndicator(v);
        tabSpec.setContent(R.id.tab3);
        tabHost.addTab(tabSpec);

        // вторая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag("tag2");

//         обработчик переключения вкладок
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
