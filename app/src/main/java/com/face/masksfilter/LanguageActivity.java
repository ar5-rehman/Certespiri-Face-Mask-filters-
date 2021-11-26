package com.face.masksfilter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LanguageActivity extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] paths = {"Deutsch", "English", "French", "Thai", "Chinese", "Italian", "Polish"};
    SharedPreferences sharedPreferences;
    TextView set;
    Button backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);


        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(LanguageActivity.this,
                android.R.layout.simple_spinner_item,paths);

        backMenu =  findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backk  =new Intent(LanguageActivity.this, MainActivity.class);
                startActivity(backk);
            }
        });

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        sharedPreferences = getSharedPreferences("language", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        set = findViewById(R.id.textSetting);

        SharedPreferences sh
                = getSharedPreferences("language",
                MODE_PRIVATE);
        String lang = sh.getString("lang", "");

        if(lang.equals(""))
        {
            spinner.setSelection(1);
            set.setText("Setting");
        }else if(lang.equals("Deutsch")) {
            spinner.setSelection(0);
            set.setText("Einstellungen");
        }else if(lang.equals("English"))
        {
            spinner.setSelection(1);
            set.setText("Setting");
        }else if(lang.equals("French"))
        {
            spinner.setSelection(2);
            set.setText("Paramètres");
        }else if(lang.equals("Thai"))
        {
            spinner.setSelection(3);
            set.setText("ตั้งค่า");
        }else if(lang.equals("Chinese"))
        {
            spinner.setSelection(4);
            set.setText("设置");
        }else if(lang.equals("Italian"))
        {
            spinner.setSelection(5);
            set.setText("impostazioni");
        }else if(lang.equals("Polish"))
        {
            spinner.setSelection(6);
            set.setText("Ustawienia");
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 0:
                        String language1 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language1);
                        editor.commit();
                        spinner.setSelection(0);
                        set.setText("Einstellungen");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        String language2 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language2);
                        editor.commit();
                        spinner.setSelection(1);
                        set.setText("Setting");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        String language3 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language3);
                        editor.commit();
                        spinner.setSelection(2);
                        set.setText("Paramètres");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        String language4 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language4);
                        editor.commit();
                        spinner.setSelection(3);
                        set.setText("ตั้งค่า");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        String language5 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language5);
                        editor.commit();
                        spinner.setSelection(4);
                        set.setText("设置");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;

                    case 5:
                        String language6 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language6);
                        editor.commit();
                        spinner.setSelection(5);
                        set.setText("设置");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;

                    case 6:
                        String language7 = (String) parent.getItemAtPosition(position);
                        editor.putString("lang", language7);
                        editor.commit();
                        spinner.setSelection(6);
                        set.setText("设置");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        String defaultLanguage = (String) parent.getItemAtPosition(position);
                        editor.putString("English", defaultLanguage);
                        editor.commit();
                        spinner.setSelection(1);
                        set.setText("Setting");
                        Toast.makeText(LanguageActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(LanguageActivity.this, MainActivity.class);
        startActivity(b);
    }
}