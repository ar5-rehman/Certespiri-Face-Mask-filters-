package com.face.masksfilter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    Button size, style, shop;
    ImageView setting;
    int i;
    String lang;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int MASKS_LARGE[] = {
            R.drawable.large1,
            R.drawable.large2,
            R.drawable.large3,
            R.drawable.large4,
            R.drawable.large5,
            R.drawable.large6,
            R.drawable.large7,
            R.drawable.large8,
            R.drawable.large9,
            R.drawable.large10
    };

    private static final int MASKS_SMALL[] = {
            R.drawable.small1,
            R.drawable.small2,
            R.drawable.small3,
            R.drawable.small4,
            R.drawable.small5,
            R.drawable.small6,
            R.drawable.small7,
            R.drawable.small8,
            R.drawable.small9,
            R.drawable.small10
    };

    private static final int MASKS_SHIELD[] = {
            R.drawable.shield1,
            R.drawable.shield2,
            R.drawable.shield3,
            R.drawable.shield4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        size = findViewById(R.id.size);
        style = findViewById(R.id.style);
        shop = findViewById(R.id.shop);
        setting = findViewById(R.id.setting);

        SharedPreferences sh
                = getSharedPreferences("language",
                MODE_PRIVATE);
        lang = sh.getString("lang", "");
        if (lang.equals("")) {
            size.setText("Select your size");
            style.setText("Select your style");
            shop.setText("Shop now");
        } else if (lang.equals("Deutsch")) {
            size.setText("Größe wählen");
            style.setText("Style wählen");
            shop.setText("Zum Shop");
        } else if (lang.equals("English")) {
            size.setText("Select your size");
            style.setText("Select your style");
            shop.setText("Shop now");
        } else if (lang.equals("French")) {
            size.setText("Choisir la taille");
            style.setText("Sélectionnez le design");
            shop.setText("Acheter maintenant");
        } else if (lang.equals("Thai")) {
            size.setText("เลือกขนาด");
            style.setText("เลือกแบบ");
            shop.setText("ช็อปเลย");
        } else if (lang.equals("Chinese")) {
            size.setText("选择尺码");
            style.setText("选择型号");
            shop.setText("开始选购");
        } else if (lang.equals("Italian")) {
            size.setText("scegliete la vostra misura");
            style.setText("scegliete il vostro stile");
            shop.setText("acquistate ora");
        } else if (lang.equals("Polish")) {
            size.setText("Wybierz swój rozmiar");
            style.setText("Wybierz swój styl");
            shop.setText("Przejdź do sklepu");
        }


        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                Intent sizeIntent = new Intent(MainActivity.this, FaceFilterActivity.class);
                sizeIntent.putExtra("data", 0);
                startActivity(sizeIntent);
            }
        });

        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                Intent styleIntent = new Intent(MainActivity.this, FaceFilterActivity.class);
                styleIntent.putExtra("data", 1);
                startActivity(styleIntent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lang.equals("English")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.certespiri.net/en"));
                    startActivity(browserIntent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.certespiri.net "));
                    startActivity(browserIntent);
                }

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(MainActivity.this, LanguageActivity.class);
                startActivity(settingIntent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}