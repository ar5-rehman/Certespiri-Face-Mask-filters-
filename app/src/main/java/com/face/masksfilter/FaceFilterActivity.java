package com.face.masksfilter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.face.masksfilter.camera.CameraSourcePreview;
import com.face.masksfilter.camera.GraphicOverlay;

import java.io.IOException;

import static android.view.View.GONE;

public class FaceFilterActivity extends AppCompatActivity {
    RelativeLayout relativeLayoutOne, relativeLayoutTwo;
    int g;

    private final Thread observer = new Thread("observer") {

        {
            setDaemon(true);
        }

        public void run() {

            while( !isInterrupted() ) {
                /*
                TextGraphic mTextGraphic = new TextGraphic(mGraphicOverlay);
                mGraphicOverlay.add(mTextGraphic);*/
                //mTextGraphic.updateText(2);
            }

        };
    };

    private static final String TAG = "FaceTracker";

    private CameraSource mCameraSource = null;
    private int typeFace = 0;
    private int typeFlash = 0;
    private boolean flashmode = false;
    private Camera camera;
    ImageView nextbtn, backbtn;

    private static final int MASK[] = {
            R.id.outline1,
            R.id.outline2,
            R.id.outline3,
            R.id.largeMask1,
            R.id.largeMask2,
            R.id.largeMask3,
            R.id.largeMask4,
            R.id.largeMask5,
            R.id.largeMask6,
            R.id.largeMask7,
            R.id.largeMask8,
            R.id.largeMask9,
            R.id.largeMask10,
            R.id.smallMask1,
            R.id.smallMask2,
            R.id.smallMask3,
            R.id.smallMask4,
            R.id.smallMask5,
            R.id.smallMask6,
            R.id.smallMask7,
            R.id.smallMask8,
            R.id.smallMask9,
            R.id.smallMask10,
            R.id.shieldMask1,
            R.id.shieldMask2,
            R.id.shieldMask3,
            R.id.shieldMask4
    };

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    //==============================================================================================
    // Activity Methods
    //==============================================================================================

    /**
     * Initializes the UI and initiates the creation of a face detector.
     */
    HorizontalScrollView largeScrollView, smallScrollView, shieldScrollView;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_face_filter);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);

        nextbtn = findViewById(R.id.nextTwo);
        backbtn = findViewById(R.id.backTwo);
        largeScrollView = findViewById(R.id.largeScrollView);
        smallScrollView = findViewById(R.id.smallScrollView);
        shieldScrollView = findViewById(R.id.shieldScrollView);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g==1) {
                    if (typeFace > 2 && typeFace < 14) {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace++;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    } else {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace = 3;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    }
                }else if(g==2)
                {
                    if (typeFace > 12 && typeFace < 22) {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace++;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    } else {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace = 13;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    }

                }else if(g==3)
                {
                    if (typeFace > 22 && typeFace < 26) {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace++;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    } else {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace = 23;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    }
                }



            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g==1) {
                    if (typeFace < 13 && typeFace > 3) {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace--;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    } else {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace = 12;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    }
                }else if(g==2)
                {
                    if (typeFace < 23 && typeFace > 13) {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace--;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    } else {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace = 22;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    }
                }else if(g==3)
                {
                    if (typeFace < 27 && typeFace > 23) {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace--;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    } else {
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                        typeFace = 26;
                        findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    }
                }
            }
        });



        ImageView next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typeFace!=2) {
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace++;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                }
                else{
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace=0;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                }

            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typeFace!=0) {
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace--;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                }else{
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace=2;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                }
            }
        });


        relativeLayoutOne = findViewById(R.id.relativeOne);
        relativeLayoutTwo = findViewById(R.id.relativeTwo);

        Intent in = getIntent();
        int i = in.getExtras().getInt("data");

        if(i==0){
            findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
            typeFace = 0;
            findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            relativeLayoutOne.setVisibility(View.VISIBLE);
            relativeLayoutTwo.setVisibility(GONE);
        }else if(i==1)
        {
            findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
            typeFace = 3;
            g=1;
            largeScrollView.setVisibility(HorizontalScrollView.VISIBLE);
            findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            relativeLayoutOne.setVisibility(GONE);
            relativeLayoutTwo.setVisibility(View.VISIBLE);
        }

        ImageView ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutOne.setVisibility(GONE);
                relativeLayoutTwo.setVisibility(View.VISIBLE);

                if(typeFace==0)
                {
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace = 3;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    largeScrollView.setVisibility(HorizontalScrollView.VISIBLE);
                    g=1;
                }
                else if(typeFace==1)
                {
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace = 13;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    smallScrollView.setVisibility(HorizontalScrollView.VISIBLE);
                    g=2;
                }
                else if(typeFace==2)
                {
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                    typeFace = 23;
                    findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
                    shieldScrollView.setVisibility(HorizontalScrollView.VISIBLE);
                    g=3;
                }

            }
        });



        TextView colorText = findViewById(R.id.textColor);
        TextView sizeText = findViewById(R.id.textsize);

        ImageView large1 = findViewById(R.id.largeMask1);
        large1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 3;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageView large2 = findViewById(R.id.largeMask2);
        large2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 4;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageView large3 = findViewById(R.id.largeMask3);
        large3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 5;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageView large4 = findViewById(R.id.largeMask4);
        large4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 6;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageView large5 = findViewById(R.id.largeMask5);
        large5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 7;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton large6 = (ImageButton) findViewById(R.id.largeMask6);
        large6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 8;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton large7 = (ImageButton) findViewById(R.id.largeMask7);
        large7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 9;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton large8 = (ImageButton) findViewById(R.id.largeMask8);
        large8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 10;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });


        ImageButton large9 = (ImageButton) findViewById(R.id.largeMask9);
        large9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 11;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton large10 = (ImageButton) findViewById(R.id.largeMask10);
        large10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 12;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small1 = (ImageButton) findViewById(R.id.smallMask1);
        small1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 13;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small2 = (ImageButton) findViewById(R.id.smallMask2);
        small2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 14;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small3= (ImageButton) findViewById(R.id.smallMask3);
        small3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 15;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small4 = (ImageButton) findViewById(R.id.smallMask4);
        small4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 16;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small5 = (ImageButton) findViewById(R.id.smallMask5);
        small5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 17;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small6 = (ImageButton) findViewById(R.id.smallMask6);
        small6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 18;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small7 = (ImageButton) findViewById(R.id.smallMask7);
        small7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 19;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small8 = (ImageButton) findViewById(R.id.smallMask8);
        small8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 20;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small9 = (ImageButton) findViewById(R.id.smallMask9);
        small9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 21;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton small10 = (ImageButton) findViewById(R.id.smallMask10);
        small10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 22;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton shield1 = (ImageButton) findViewById(R.id.shieldMask1);
        shield1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 23;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton shield2 = (ImageButton) findViewById(R.id.shieldMask2);
        shield2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 24;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton shield3 = (ImageButton) findViewById(R.id.shieldMask3);
        shield3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 25;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageButton shield4 = (ImageButton) findViewById(R.id.shieldMask4);
        shield4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background);
                typeFace = 26;
                findViewById(MASK[typeFace]).setBackgroundResource(R.drawable.round_background_select);
            }
        });

        ImageView cart = (ImageView)  findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(typeFace==3)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-0"));
                    startActivity(browserIntent);
                }else if(typeFace==4)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-0-ES"));
                    startActivity(browserIntent);
                }else if(typeFace==5){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-1"));
                    startActivity(browserIntent);
                }else if(typeFace==6)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-2"));
                    startActivity(browserIntent);
                }else if(typeFace==7)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-3"));
                    startActivity(browserIntent);
                }else if(typeFace==8){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-4"));
                    startActivity(browserIntent);
                }else if(typeFace==9)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-5"));
                    startActivity(browserIntent);
                }else if(typeFace==10){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-6"));
                    startActivity(browserIntent);
                }else if(typeFace==11)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-7"));
                    startActivity(browserIntent);
                }else if(typeFace==12)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/1-Large-Mask-8"));
                    startActivity(browserIntent);
                }else if(typeFace==13){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-0"));
                    startActivity(browserIntent);
                }else if(typeFace==14)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-0-ES"));
                    startActivity(browserIntent);
                }else if(typeFace==15){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-1"));
                    startActivity(browserIntent);
                }else if(typeFace==15)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-2"));
                    startActivity(browserIntent);
                }else if(typeFace==17)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-3"));
                    startActivity(browserIntent);
                }else if(typeFace==18){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-4"));
                    startActivity(browserIntent);
                }else if(typeFace==19)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-5"));
                    startActivity(browserIntent);
                }else if(typeFace==20){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-6"));
                    startActivity(browserIntent);
                }else if(typeFace==21)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-7"));
                    startActivity(browserIntent);
                }else if(typeFace==22)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/2-Small-Mask-8"));
                    startActivity(browserIntent);
                }else if(typeFace==23){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/3-Shield-0"));
                    startActivity(browserIntent);
                }else if(typeFace==24)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/3-Shield-0-ES"));
                    startActivity(browserIntent);
                }else if(typeFace==25){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/3-Shield-1"));
                    startActivity(browserIntent);
                }else if(typeFace==26)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.certespiri.com/3-Shield-2"));
                    startActivity(browserIntent);
                }

            }
        });

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }

        //Check the language used
        SharedPreferences sh
                = getSharedPreferences("language",
                MODE_PRIVATE);
        String lang = sh.getString("lang", "");

        if(lang.equals(""))
        {
            sizeText.setText("Size");
            colorText.setText("Style");
        }else if(lang.equals("Deutsch")) {
            sizeText.setText("Größe");
            colorText.setText("Style");
        }else if(lang.equals("English"))
        {
            sizeText.setText("Size");
            colorText.setText("Style");
        }else if(lang.equals("French"))
        {
            sizeText.setText("Taille");
            colorText.setText("Design");
        }else if(lang.equals("Thai"))
        {
            sizeText.setText("ขนาด");
            colorText.setText("แบบ");
        }else if(lang.equals("Chinese"))
        {
            sizeText.setText("尺码");
            colorText.setText("型号");
        }else if(lang.equals("Italian"))
        {
            sizeText.setText("Misura");
            colorText.setText("stile");
        }else if(lang.equals("Polish"))
        {
            sizeText.setText("Rozmiar");
            colorText.setText("Styl");
        }

    }


    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {

            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }


    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay,typeFace);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face,typeFace);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }
}