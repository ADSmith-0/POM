package com.adam.pom.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adam.pom.Helper.AppConfig;
import com.adam.pom.Helper.HttpHelper;
import com.adam.pom.Helper.MatchesDatabaseHelper;
import com.adam.pom.Helper.RecyclerAdapter;
import com.adam.pom.Helper.UserDatabaseHelper;
import com.adam.pom.Helper.VolleyCallback;
import com.adam.pom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentMatches extends Fragment {

    private static final String TAG = FragmentMatches.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        getMatches();

        recyclerView = rootView.findViewById(R.id.frag_matches_recycler);

        //use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //list of matches
        ArrayList<String> mNames = new ArrayList<String>();
        mNames = ReadDB();

        //specify an adapter
        recyclerAdapter = new RecyclerAdapter(mNames);
        recyclerView.setAdapter(recyclerAdapter);

        return rootView;
    }

    private void getMatches(){
        //get matches.
        UserDatabaseHelper uDbh = new UserDatabaseHelper(getContext());
        Cursor c = uDbh.getData();
        c.moveToFirst();
        int id = c.getInt(c.getColumnIndex("id"));
        String user_id = Integer.toString(id);

        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);

        HttpHelper.makeRequest(params, AppConfig.URL_GET_MATCHES, getContext(), new VolleyCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    MatchesDatabaseHelper mDbh = new MatchesDatabaseHelper(getContext());
                    mDbh.dropTable();
                    if(result.getBoolean("success")) {
                        JSONArray users = result.getJSONArray("users");
                        int id;
                        String name, first_name, surname;
                        for (int i = 0; i < users.length(); i++) {
                            id = users.getJSONObject(i).getInt("id");
                            first_name = users.getJSONObject(i).getString("first_name");
                            surname = users.getJSONObject(i).getString("surname");

                            mDbh.addData(id, first_name, surname);
                        }
                    }else{
                        mDbh.addData(-1, "", "");
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
        c.close();
    }

    private ArrayList<String> ReadDB(){
        ArrayList<String> matches = new ArrayList<String>();
        MatchesDatabaseHelper mDbh = new MatchesDatabaseHelper(getContext());
        Log.e(TAG, "Working1");
        Cursor c = mDbh.getData();
        if(c.getCount() == 0){
            Log.e(TAG, "Working2");
            matches.add("No matches!");
        }else{
            c.moveToFirst();
            String name, first_name, surname;
            do{
                first_name = c.getString(c.getColumnIndex("first_name"));
                surname = c.getString(c.getColumnIndex("surname"));
                name = first_name + " " + surname;

                Log.e(TAG, name);

                matches.add(name);
            }while(c.moveToNext());
        }

        return matches;

    }
}
