package com.adam.pom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adam.pom.Helper.AppConfig;
import com.adam.pom.Helper.AppController;
import com.adam.pom.Helper.HttpHelper;
import com.adam.pom.Helper.MatchesDatabaseHelper;
import com.adam.pom.Helper.UserDatabaseHelper;
import com.adam.pom.Helper.OnErrorResponseHelper;
import com.adam.pom.Helper.VolleyCallback;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG = Login.class.getSimpleName();

    private TextView input_email, input_password;
    private Button sign_in, bypass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //reset sqlite databases.
        UserDatabaseHelper uDbh = new UserDatabaseHelper(this);
        uDbh.dropTable();

        //reset matches sqlite database
        MatchesDatabaseHelper mDbh = new MatchesDatabaseHelper(this);
        mDbh.dropTable();

        input_email = findViewById(R.id.login_email);
        input_password = findViewById(R.id.login_password);

        sign_in = findViewById(R.id.login_sign_in);
        bypass = findViewById(R.id.login_bypass);

        sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email, password;
                email = input_email.getText().toString().trim();
                password = input_password.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty()){
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    HttpHelper.makeRequest(params, AppConfig.URL_GET_USER_DETAILS, getApplicationContext(),
                            new VolleyCallback() {
                                @Override
                                public void onSuccessResponse(JSONObject result) {
                                    int id;
                                    String first_name, surname, interested_in;
                                    try{
                                        boolean success = result.getBoolean("success");
                                        if(success) {
                                            JSONObject user = new JSONObject();
                                            user = result.getJSONArray("user").getJSONObject(0);
                                            id = user.getInt("id");
                                            first_name = user.getString("first_name");
                                            surname = user.getString("surname");
                                            interested_in = user.getString("interested_in");

                                            //add user details to sqlite database.
                                            UserDatabaseHelper dbh = new UserDatabaseHelper(getApplicationContext());
                                            dbh.login(id, first_name, surname, interested_in);

                                            //populate SQLite database with cards list.
                                            getCards();
                                        }else{
                                            Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                                        }
                                    }catch(JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(), "Please input both fields", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Missing fields.");
                }
            }
        });

        bypass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserDatabaseHelper dbh = new UserDatabaseHelper(getApplicationContext());
                dbh.login(-1, "def", "ault", "female");
                goLandingPage();
            }
        });
    }

    private void getCards(){
        UserDatabaseHelper dbh = new UserDatabaseHelper(getApplicationContext());
        Cursor c = dbh.getData();
        c.moveToFirst();

        String interested_in = c.getString(c.getColumnIndex("interested_in"));

        Map<String, String> params = new HashMap<>();
        params.put("interested_in", interested_in);

        HttpHelper.makeRequest(params, AppConfig.URL_GET_USERS, getApplicationContext(),
                new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {
                            JSONArray users = result.getJSONArray("users");
                            int id;
                            String first_name, surname, sex;
                            UserDatabaseHelper dbh = new UserDatabaseHelper(getApplicationContext());
                            for (int i = 0; i < users.length(); i++) {
                                id = users.getJSONObject(i).getInt("id");
                                first_name = users.getJSONObject(i).getString("first_name");
                                surname = users.getJSONObject(i).getString("surname");
                                sex = users.getJSONObject(i).getString("sex");

                                Log.e(TAG, Integer.toString(id) + " " + first_name + " " + surname);
                                //add to database.
                                dbh.addData(id, first_name, surname, "");
                            }
                            dbh.close();

                            //move forward after success.
                            goLandingPage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void goLoadingPage(){
        Intent intent = new Intent(getApplicationContext(), LoadCards.class);
        startActivity(intent);
    }

    private void goLandingPage(){
        Intent intent = new Intent(getApplicationContext(), LandingPage.class);
        startActivity(intent);
    }
}
