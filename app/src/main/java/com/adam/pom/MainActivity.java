package com.adam.pom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_registration, btn_login, btn_facebook;
    private TextView txt_discover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_discover = findViewById(R.id.txt_main_discover);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/" + "Montserrat-Regular.ttf");


        btn_registration = findViewById(R.id.btn_registration);
        btn_login = findViewById(R.id.btn_login);
        btn_facebook = findViewById(R.id.btn_main_login_facebook);

        //txt_discover.setTypeface(myFont);
        //btn_login.setTypeface(myFont);
        //btn_registration.setTypeface(myFont);
        //btn_facebook.setTypeface(myFont);

        btn_registration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}
