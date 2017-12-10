package com.example.denis.laba8_and;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Otchet extends AppCompatActivity {

    TextView OCRTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otchet);
        OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        Intent intent2 = getIntent();
        String nomer = intent2.getStringExtra("nomer");
        OCRTextView.setText(nomer);
    }

    public void OnClick_TEST(View view) {
          this.finish();
    }
}
