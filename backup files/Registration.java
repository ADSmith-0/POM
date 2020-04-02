package com.adam.pom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private TextView fname, sname, phoneno, sex, email, password;
    private Button submitButton, deleteButton;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fname = findViewById(R.id.input_fname);
        sname = findViewById(R.id.input_sname);
        phoneno = findViewById(R.id.input_phoneno);
        sex = findViewById(R.id.input_sex);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);

        submitButton = findViewById(R.id.submit);
        deleteButton = findViewById(R.id.delete);
        mDatabaseHelper = new DatabaseHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String FName = fname.getText().toString();
                String SName = sname.getText().toString();
                int PhoneNo = Integer.parseInt(phoneno.getText().toString());
                String Sex = sex.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(fname.length() != 0 && sname.length() != 0 && phoneno.length() != 0 && sex.length() != 0 && email.length() != 0 && password.length() != 0) {
                    AddData(FName, SName, PhoneNo, Sex, Email, Password);
                }else{
                    Toast.makeText(Registration.this, "Error", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(Registration.this, DB.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                int version = mDatabaseHelper.getVersion();
                mDatabaseHelper.onUpgrade(db, version, version);
            }
        });
    }

    public void AddData(String FName, String SName, int PhoneNo, String Sex, String Email, String Password){
        boolean insertData = mDatabaseHelper.addData(FName, SName,PhoneNo, Sex, Email, Password);

        if(insertData){
            Toast.makeText(Registration.this,"Successfully inserted!", Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(Registration.this,"Something went wrong", Toast.LENGTH_SHORT);
        }
    }
}
