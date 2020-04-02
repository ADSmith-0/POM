package com.adam.pom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.adam.pom.Helper.AppConfig;
import com.adam.pom.Helper.AppController;
import com.adam.pom.Helper.HttpHelper;
import com.adam.pom.Helper.UserDatabaseHelper;
import com.adam.pom.Helper.OnErrorResponseHelper;
import com.adam.pom.Helper.VolleyCallback;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadCards extends AppCompatActivity {

    private static final String TAG = LoadCards.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_cards);

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

                            //Start landing page.
                            Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
