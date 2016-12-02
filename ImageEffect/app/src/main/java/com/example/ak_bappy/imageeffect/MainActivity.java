package com.example.ak_bappy.imageeffect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Drawable pic;
    ImageView img;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button captureBtn = (Button)findViewById(R.id.captureButton);
        img = (ImageView)findViewById(R.id.imageView);

        if(!cameraAvailable())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Camera do not Available..!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            captureBtn.setEnabled(false);
        }

    }

    public boolean cameraAvailable()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void startCamera(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle extra = data.getExtras();
        photo = (Bitmap) extra.get("data");
        img.setImageBitmap(photo);
    }


    public void onClickInvert(View v)
    {
        Bitmap bitmapImage = photo;
        Bitmap finalImage = Bitmap.createBitmap(bitmapImage.getWidth(),bitmapImage.getHeight(),bitmapImage.getConfig());

        int A, R, G, B;
        int pixelColor;
        int height = bitmapImage.getHeight();
        int width = bitmapImage.getWidth();

        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                pixelColor = bitmapImage.getPixel(x,y);
                A = Color.alpha(pixelColor);
                B = 255 - Color.blue(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);

                finalImage.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        Bitmap newPhoto = finalImage;
        img.setImageBitmap(newPhoto);
        Toast.makeText(getApplicationContext(),"finish",Toast.LENGTH_SHORT).show();
    }
}