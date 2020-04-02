package com.adam.pom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DB extends AppCompatActivity {

    private static final String TAG = "ListViewActivity";

    private ListView listView;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        listView = findViewById(R.id.list_db);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Populate the list view with the database");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<String>();
        while(data.moveToNext()){
            // Read data from table and add it to the arraylist.
            // columnIndex is what column from the table it's reading (the number in the getString()).
            listData.add(data.getString(1));
            listData.add(data.getString(2));
            listData.add(data.getString(3));
            listData.add(data.getString(4));
            listData.add(data.getString(5));
            listData.add(data.getString(6));
        }
        // Create list adapter and set the adapter.
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }
}
