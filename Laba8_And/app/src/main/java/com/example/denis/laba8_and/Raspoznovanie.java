package com.example.denis.laba8_and;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Raspoznovanie extends AppCompatActivity {
    ImageView mImageView;
    ImageView mImageView1;
    ImageView mImageView2;
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
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView1 = (ImageView) findViewById(R.id.imageView1);
        mImageView2 = (ImageView) findViewById(R.id.imageView2);
        logMemory();
        readImage();
        logMemory();
        logMemory1();
        readImage1();
        logMemory1();
        logMemory2();
        readImage2();
        logMemory2();
        String fname = "/sdcard/myImages/my_photo.jpg";

        //init image
        //  image = BitmapFactory.decodeResource(getResources(), R.drawable.test_image1);
        //  image1= BitmapFactory.decodeResource(getResources(), R.drawable.test_image2);

        //initialize Tesseract API
        String language = "rus";
        datapath = getFilesDir() + "/tesseract/";
        mTess = new TessBaseAPI();
        mTess1 = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);
        mTess1.init(datapath, language);

        OCRTextView = (TextView) findViewById(R.id.OCRTextView);
    }

    private void readImage() {
        File file = new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "1.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Log.d("log", String.format("bitmap size = %sx%s, byteCount = %s",
                bitmap.getWidth(), bitmap.getHeight(),
                (int) (bitmap.getByteCount() / 1024)));
        mImageView2.setImageBitmap(bitmap);
    }

    private void readImage1() {
        File file = new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"2.jpg");
        image = BitmapFactory.decodeFile(file.getAbsolutePath());
        Log.d("log", String.format("bitmap size = %sx%s, byteCount = %s",
                image.getWidth(), image.getHeight(),
                (int) (image.getByteCount() / 1024)));
        mImageView.setImageBitmap(image);
    }

    private void readImage2() {
        File file = new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"3.jpg");
        image1 = BitmapFactory.decodeFile(file.getAbsolutePath());
        Log.d("log", String.format("bitmap size = %sx%s, byteCount = %s",
                image1.getWidth(), image1.getHeight(),
                (int) (image1.getByteCount() / 1024)));
        mImageView1.setImageBitmap(image1);
    }

    private void logMemory() {
        Log.i("log", String.format("Total memory = %s",
                (int) (Runtime.getRuntime().totalMemory() / 1024)));
    }

    private void logMemory1() {
        Log.i("log", String.format("Total memory = %s",
                (int) (Runtime.getRuntime().totalMemory() / 1024)));
    }

    private void logMemory2() {
        Log.i("log", String.format("Total memory = %s",
                (int) (Runtime.getRuntime().totalMemory() / 1024)));
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
