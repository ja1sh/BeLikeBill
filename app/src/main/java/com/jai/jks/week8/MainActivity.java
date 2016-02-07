package com.jai.jks.week8;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class MainActivity extends AppCompatActivity  {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private ImageView mImageView;
    private Bitmap mback, mbill;
    private EditText mText;
    private String mString;
    private TextPaint mPaint;

    private static final int REQUEST_WRITE_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (EditText) findViewById(R.id.editText);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE);
            }
        }}
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(getApplicationContext(), "Permission Error: Grant Permission First", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



    public void btn(View v){
        mString = mText.getText().toString();
        mback = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        mbill = BitmapFactory.decodeResource(getResources(), R.drawable.bill_1);
        Bitmap resized = Bitmap.createScaledBitmap(mbill,550,550, false);
        Bitmap resizedBack = Bitmap.createScaledBitmap(mback, 800, 800, false);

        mBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new TextPaint();
        mCanvas.drawBitmap(resizedBack, 0, 0, null);
        mCanvas.drawBitmap(resized, 250, 100, null);
        mPaint.setTextSize(45);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xff191919);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        Rect bounds = new Rect();
        StaticLayout sl = new StaticLayout(mString, mPaint, bounds.width(),
                Layout.Alignment.ALIGN_CENTER, 10, 10, true);
        mCanvas.save();
        float textYCoordinate = bounds.top;
        float textXCoordinate = bounds.left;
        mCanvas.translate(textXCoordinate, textYCoordinate);

        sl.draw(mCanvas);
        mCanvas.restore();
        //mCanvas.drawText(mString, 100, 150, mPaint);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageBitmap(mBitmap);


    }

    public void savnshare(View v){
        if (mBitmap == null){
            return;
        }
            File path = new File(Environment.getExternalStorageDirectory() + "/Bill");
            path.mkdirs();
            Random rand = new Random();
            int n = rand.nextInt(200);
            String filename = "bill_"+n+".png";
            File file = new File(path, filename);
            FileOutputStream stream;
            try{
                stream = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.close();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"errr try again...",Toast.LENGTH_SHORT).show();
            }
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent.createChooser(intent, "Share via...");
            startActivity(intent);


    }}



