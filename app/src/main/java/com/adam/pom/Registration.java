package com.adam.pom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.adam.pom.Helper.AppConfig;
import com.adam.pom.Helper.HttpHelper;
import com.adam.pom.Helper.VolleyCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private static final String TAG = Registration.class.getSimpleName();

    private TextView input_email, input_password, input_firstname, input_surname, input_phoneno;
    private Button btn_Add, btn_Bypass;
    private Spinner spinner_sex, spinner_interestedin;
    private String sex, interested_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_firstname = findViewById(R.id.input_firstname);
        input_surname = findViewById(R.id.input_surname);
        input_phoneno = findViewById(R.id.input_phoneno);
        sex = "";
        interested_in = "";

        spinner_sex = findViewById(R.id.spinner_sex);
        ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this, R.array.spinner_sex, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(sex_adapter);

        spinner_interestedin = findViewById(R.id.spinner_interestedin);
        ArrayAdapter<CharSequence> interestedin_adapter = ArrayAdapter.createFromResource(this, R.array.spinner_interestedin, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_interestedin.setAdapter(interestedin_adapter);

        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = parent.getItemAtPosition(position).toString().toLowerCase().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_interestedin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interested_in = parent.getItemAtPosition(position).toString().toLowerCase().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_Add = findViewById(R.id.registration_add);
        btn_Bypass = findViewById(R.id.registration_bypass);

        btn_Add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email, password, first_name, surname, phoneno;

                email = input_email.getText().toString().trim();
                password = input_password.getText().toString().trim();
                first_name = input_firstname.getText().toString().trim();
                surname = input_surname.getText().toString().trim();
                phoneno = input_phoneno.getText().toString().trim();


                if(!email.isEmpty() && !password.isEmpty() && !first_name.isEmpty() &&
                    !surname.isEmpty() && !phoneno.isEmpty() && !sex.isEmpty() &&
                    !interested_in.isEmpty()){

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("first_name", first_name);
                    params.put("surname", surname);
                    params.put("phoneno", phoneno);
                    params.put("sex", sex);
                    params.put("interested_in", interested_in);

                    HttpHelper.makeRequest(params, AppConfig.URL_REGISTER, getApplicationContext(),
                            new VolleyCallback() {
                                @Override
                                public void onSuccessResponse(JSONObject result) {
                                    try{
                                        boolean error = result.getBoolean("success");
                                        if(!error){
                                            String errorMsg = result.getString("message");
                                            Toast.makeText(getApplicationContext(), "Error occurred: " + errorMsg,
                                                    Toast.LENGTH_LONG).show();
                                            goLogin();
                                        }
                                    }catch(JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(Registration.this, "Missing data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Bypass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });
    }

    private void goLogin(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}
