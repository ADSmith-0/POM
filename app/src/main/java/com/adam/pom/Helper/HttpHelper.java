package com.adam.pom.Helper;
import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

public class HttpHelper {

    private static final String TAG = HttpHelper.class.getSimpleName();

    public static void makeRequest(Map<String, String> params, String url, Context context, VolleyCallback callback){
        StringRequest strReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            if(response != null) {
                                JSONObject jObj = new JSONObject(response);
                                callback.onSuccessResponse(jObj);
                            }else{
                                Log.e(TAG, "response is empty!");
                            }
                        }catch(JSONException e){
                            Log.e(TAG, ": Error: ");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        OnErrorResponseHelper.checkError(TAG, context, error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
