package com.example.denis.laba8_and;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Raspoznovanie extends AppCompatActivity {

    Bitmap image;
    Bitmap image1;
    private TessBaseAPI mTess;
    private TessBaseAPI mTess1;
    String datapath = "";
    TextView OCRTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspoznovanie);

        //init image
        image = BitmapFactory.decodeResource(getResources(), R.drawable.test_image1);
        image1= BitmapFactory.decodeResource(getResources(), R.drawable.test_image2);

        //initialize Tesseract API
        String language = "rus";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();
        mTess1 = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);
        mTess1.init(datapath, language);

        OCRTextView = (TextView) findViewById(R.id.OCRTextView);
    }

    public void processImage(View view){
        String OCRresult = null;
        String OCRresult1 = null;
        mTess.setImage(image);
        mTess1.setImage(image1);
        OCRresult = mTess.getUTF8Text();
        OCRresult1 = mTess1.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult + " " + OCRresult1);

    }

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/rus.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/rus.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/rus.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void vivod(View view) {
        Intent intent2 = new Intent(Raspoznovanie.this,  Otchet.class);
        intent2.putExtra("nomer", OCRTextView.getText().toString());
        startActivity(intent2);
    }
}
