package com.adam.pom.Fragments;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adam.pom.Helper.AppConfig;
import com.adam.pom.Helper.AppController;
import com.adam.pom.Helper.CardsArrayAdapter;
import com.adam.pom.Helper.HttpHelper;
import com.adam.pom.Helper.UserDatabaseHelper;
import com.adam.pom.Helper.OnErrorResponseHelper;
import com.adam.pom.Helper.VolleyCallback;
import com.adam.pom.LandingPage;
import com.adam.pom.Objects.Cards;
import com.adam.pom.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;


public class FragmentMain extends Fragment {
    private static final String TAG = FragmentMain.class.getSimpleName();

    private CardsArrayAdapter cardsAdapter;

    private SwipeFlingAdapterView flingContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //set Views.
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        flingContainer = rootView.findViewById(R.id.fragment_frame);

        //get data from database.
        UserDatabaseHelper dbh = new UserDatabaseHelper(getContext());
        Cursor c = dbh.getData();
        c.moveToFirst();

        //declare cards that will be translated to flingContainer.
        List<Cards> cards = new ArrayList<Cards>();

        //while there's rows in the database get the name of the next one.
        int id, image_id;
        String name, first_name, surname;
        while(c.moveToNext()){
            Cards card = new Cards(-1,"",0);
            //skipping first row as it's the users' login details.
            id = c.getInt(c.getColumnIndex("id"));
            first_name = c.getString(c.getColumnIndex("first_name"));
            surname = c.getString(c.getColumnIndex("surname"));
            name = first_name + " " + surname;
            image_id = (id == 24) ? R.drawable.user_male : R.drawable.user_female;
            card = new Cards(id, name, image_id);
            cards.add(card);
            Log.e(TAG, card.getName());
        }

        //set flingContainer to the cards array.
        cardsAdapter = new CardsArrayAdapter(this.getContext(), R.layout.item, cards);
        flingContainer.setAdapter(cardsAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                cards.remove(0);
                cardsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

                checkMatch(dataObject, false);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                checkMatch(dataObject, true);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                Log.d("LIST", "notified");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(getActivity(), "Clicked!");
            }
        });

        return rootView;
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.right)
    public void right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.left)
    public void left() {

        flingContainer.getTopCardListener().selectLeft();
    }

    private void checkMatch(Object dataObject, Boolean yes){
        String user_id, card_id, user_name, card_name, URL;
        //get users email address.
        UserDatabaseHelper dbh = new UserDatabaseHelper(getContext());
        Cursor c = dbh.getData();
        c.moveToFirst();
        user_id = Integer.toString(c.getInt(c.getColumnIndex("id")));
        user_name = c.getString(c.getColumnIndex("first_name")) + " " + c.getString(c.getColumnIndex("surname"));

        URL = (yes) ? AppConfig.URL_CHECK_TRUE : AppConfig.URL_CHECK_FALSE;

        try {
            //try casting dataobject as Cards type.
            Cards card = (Cards) dataObject;
            card_id = Integer.toString(card.getId());
            card_name = card.getName();

            StringRequest strReq = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jObj = new JSONObject(response);
                                if(jObj.getBoolean("success")){
                                    if(jObj.getBoolean("matched")){
                                        addToMatched(user_id, card_id, user_name, card_name);
                                        Toast.makeText(getContext(), "New match!", Toast.LENGTH_LONG);
                                    }
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            OnErrorResponseHelper.checkError(TAG, getContext(), error);
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", user_id);
                    params.put("card_id", card_id);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(strReq);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void addToMatched(String user_id, String card_id, String user_name, String card_name){
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_ADD_TO_MATCHED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("card_id", card_id);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq);
    }
}
