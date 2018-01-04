package com.example.denis.laba8_and;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class Otchet extends AppCompatActivity {

    TextView OCRTextView;
    public static Avto avto;
    String API_PATH = "http://ddmm123.000webhostapp.com/";
    String URL = "";
    public static TextView et1;
    public static TextView et2;
    public static TextView et3;
    public static TextView et4;
    public static TextView et5;
    public static TextView et6;
    public static TextView et7;
    public static TextView et8;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otchet);
        avto = new Avto();
        OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        Intent intent2 = getIntent();
        String nomer = intent2.getStringExtra("nomer");
        OCRTextView.setText(nomer);
        Log.i("App", "nomer = " + nomer);
        Viborka fd = new Viborka();
        fd.setTask("GetAuto");

        nomer = nomer.replaceAll("\\s+","_");
        URL = API_PATH + "api.php?act=getinfo&nomer=" + nomer;
        Log.i("App", "URL= "+URL);
        fd.execute(URL);
        Log.i("App", "Executed Viborka");
        et1 = (TextView) findViewById(R.id.textView13);
        et2 = (TextView) findViewById(R.id.textView3);
        et3 = (TextView) findViewById(R.id.textView14);
        et4 = (TextView) findViewById(R.id.textView15);
        et5 = (TextView) findViewById(R.id.textView16);
        et6 = (TextView) findViewById(R.id.textView17);
        et7 = (TextView) findViewById(R.id.textView18);
        et8 = (TextView) findViewById(R.id.textView12);
    }

    public void OnClick_TEST(View view) {
        this.finish();
    }
}


