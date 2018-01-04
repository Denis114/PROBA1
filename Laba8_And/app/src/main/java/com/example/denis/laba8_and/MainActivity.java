package com.example.denis.laba8_and;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    Button BtDoownload;
    Button BtDoownload2;
    Button BtDoownload3;
    final int CAMERA_ID = 0;
    final boolean FULL_SCREEN = true;
    private SurfaceView surfaceView;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder holder;
    File photoFile;
    ImageView IvImage;
    ImageView IvImage1;
    ImageView IvImage2;
    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File CameraTest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        photoFile = new File(CameraTest, "myphoto.jpg");
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();


        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                camera.stopPreview();
                setCameraDisplayOrientation(CAMERA_ID);
                try {
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
        BtDoownload = (Button) findViewById(R.id.button2);
        BtDoownload2 = (Button) findViewById(R.id.button4);
        BtDoownload3 = (Button) findViewById(R.id.button5);
        IvImage = (ImageView) findViewById(R.id.imageView);
        IvImage1 = (ImageView) findViewById(R.id.imageView1);
        IvImage2 = (ImageView) findViewById(R.id.imageView2);

     //   BtDoownload.setOnClickListener(new View.OnClickListener() {
        View.OnClickListener myButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button2:
                            IvImage2.setVisibility(View.INVISIBLE);
                            IvImage1.setVisibility(View.INVISIBLE);
                            surfaceView.setVisibility(View.INVISIBLE);
                            Intent image1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            image1.putExtra("crop", "true");
                            image1.putExtra("aspectX", 0.5);
                            image1.putExtra("aspectY", 200);
                            image1.putExtra("outputX", 0.5);
                            image1.putExtra("outputY", 200);
                            image1.putExtra("return-data", true);
                            startActivityForResult(image1, 2);
                            IvImage.setVisibility(View.VISIBLE);
                            break;
                        case R.id.button4:
                            IvImage2.setVisibility(View.INVISIBLE);
                            IvImage.setVisibility(View.INVISIBLE);
                            surfaceView.setVisibility(View.INVISIBLE);
                            Intent image2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            image2.putExtra("crop", "true");
                            image2.putExtra("aspectX", 0.5);
                            image2.putExtra("aspectY", 200);
                            image2.putExtra("outputX", 0.5);
                            image2.putExtra("outputY", 200);
                            image2.putExtra("return-data", true);
                            startActivityForResult(image2, 3);
                            IvImage1.setVisibility(View.VISIBLE);
                            break;
                        case R.id.button5:
                            IvImage.setVisibility(View.INVISIBLE);
                            IvImage1.setVisibility(View.INVISIBLE);
                            surfaceView.setVisibility(View.INVISIBLE);
                            Intent image3 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            image3.putExtra("crop", "true");
                            image3.putExtra("aspectX", 0.5);
                            image3.putExtra("aspectY", 200);
                            image3.putExtra("outputX", 0.5);
                            image3.putExtra("outputY", 200);
                            image3.putExtra("return-data", true);
                            startActivityForResult(image3, 4);
                            IvImage2.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            };

        BtDoownload.setOnClickListener(myButton);
        BtDoownload2.setOnClickListener(myButton);
        BtDoownload3.setOnClickListener(myButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(CAMERA_ID);
        setPreviewSize(FULL_SCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();
        if (camera != null)
            camera.release();
        camera = null;
    }

    void setPreviewSize(boolean fullScreen) {

        // получаем размеры экрана
        Display display = getWindowManager().getDefaultDisplay();
        boolean widthIsMax = display.getWidth() > display.getHeight();

        // определяем размеры превью камеры
        Size size = camera.getParameters().getPreviewSize();

        RectF rectDisplay = new RectF();
        RectF rectPreview = new RectF();

        // RectF экрана, соотвествует размерам экрана
        rectDisplay.set(0, 0, display.getWidth(), display.getHeight());

        // RectF первью
        if (widthIsMax) {
            // превью в горизонтальной ориентации
            rectPreview.set(0, 0, size.width, size.height);
        } else {
            // превью в вертикальной ориентации
            rectPreview.set(0, 0, size.height, size.width);
        }

        Matrix matrix = new Matrix();
        // подготовка матрицы преобразования
        if (!fullScreen) {
            // если превью будет "втиснут" в экран (второй вариант из урока)
            matrix.setRectToRect(rectPreview, rectDisplay,
                    Matrix.ScaleToFit.START);
        } else {
            // если экран будет "втиснут" в превью (третий вариант из урока)
            matrix.setRectToRect(rectDisplay, rectPreview,
                    Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }
        // преобразование
        matrix.mapRect(rectPreview);

        // установка размеров surface из получившегося преобразования
        surfaceView.getLayoutParams().height = (int) (rectPreview.bottom);
        surfaceView.getLayoutParams().width = (int) (rectPreview.right);
    }

    void setCameraDisplayOrientation(int cameraId) {
// определяем насколько повернут экран от нормального положения
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result = 0;

// получаем инфо по камере cameraId
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);

// задняя камера
        if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation);
        } else
// передняя камера
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                result = ((360 - degrees) - info.orientation);
                result += 360;
            }
        result = result % 360;
        camera.setDisplayOrientation(result);
    }


    public void onClickPicture(View view) {
        IvImage.setVisibility(View.INVISIBLE);
        surfaceView.setVisibility(View.VISIBLE);
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                     FileOutputStream fos = new FileOutputStream(photoFile);
                    fos.write(data);
                       fos.close();


                    camera.stopPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();  // Если хотим использовать этот же объект для другой записи с другими настройками
            mediaRecorder.release();//Освобождаем объект
            mediaRecorder = null;
            camera.lock();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= 2 && resultCode == RESULT_OK && data != null) {
            Bundle extrss = data.getExtras();
            Bitmap bitmap = extrss.getParcelable("data");
            switch (requestCode) {
                case 2: IvImage.setImageBitmap(bitmap); break;
                case 3: IvImage1.setImageBitmap(bitmap); break;
                case 4: IvImage2.setImageBitmap(bitmap); break;}
              Uri imageUri=data.getData();
            try {
                switch (requestCode) {
                    case 2:  saveImageFile(bitmap,1); break;
                    case 3:  saveImageFile(bitmap,2); break;
                    case 4:  saveImageFile(bitmap,3); break;}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String saveImageFile(Bitmap bitmap,int pp) {
        FileOutputStream out = null;
        String filename = getFilename(pp);
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }
    String uriSting;
    private String getFilename(int mm) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "Pictures");
        if (!file.exists()) {
            file.mkdirs();
        }

        switch (mm) {
            case 1:    uriSting = (file.getAbsolutePath() + "/"
                    + "1.jpg"); break;
            case 2:    uriSting = (file.getAbsolutePath() + "/"
                    + "2.jpg"); break;
            case 3:    uriSting = (file.getAbsolutePath() + "/"
                    + "3.jpg"); break;
        }
                return uriSting;
    }

    public void rasp(View view) {
        Intent inten1 = new Intent(MainActivity.this,  Raspoznovanie.class);
        startActivity(inten1);
    }
}
