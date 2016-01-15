package com.recyclerviewsampleandroid.interfaces;

import com.android.volley.VolleyError;

/**
 * Volley API request onResponseReceived Listener
 * Created by dhananjay on 14/1/16.
 */
public interface OnResponseListener_Volley {
    void onResponseReceived_Volley(String response, VolleyError error);
}
