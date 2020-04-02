package com.adam.pom.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class OnErrorResponseHelper {
    public static void checkError(String TAG, Context context, VolleyError error){
        if(error instanceof AuthFailureError){
            Log.e(TAG, "Authorisation Failure Error: " + error.getMessage());
            Toast.makeText(context, TAG + " Error: Authorisation Failure Error", Toast.LENGTH_LONG).show();
        }else if(error instanceof NetworkError){
            Log.e(TAG, "NetworkError: " + error.getMessage());
            Toast.makeText(context, TAG + " Error: Network Error", Toast.LENGTH_LONG).show();
        }else if(error instanceof ParseError){
            Log.e(TAG, "Parse Error: " + error.getMessage());
            Toast.makeText(context, TAG + " Error: Parse Error", Toast.LENGTH_LONG).show();
        }else if(error instanceof ServerError){
            Log.e(TAG, "Server Error: " + error.getMessage());
            Toast.makeText(context, TAG + " Error: Server Error", Toast.LENGTH_LONG).show();
        }else if(error instanceof TimeoutError){
            Log.e(TAG, "Timeout Error: " + error.getMessage());
            Toast.makeText(context, TAG + " Error: Timeout Error", Toast.LENGTH_LONG).show();
        }else if(error instanceof NoConnectionError){
            Log.e(TAG, " No Connection Error: " + error.getMessage());
            Toast.makeText(context, TAG + " Error: No Connection Error", Toast.LENGTH_LONG).show();
        }
    }
}
