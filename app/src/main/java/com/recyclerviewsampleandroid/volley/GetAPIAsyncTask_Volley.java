package com.recyclerviewsampleandroid.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.recyclerviewsampleandroid.R;
import com.recyclerviewsampleandroid.application.ApplicationDelegate;
import com.recyclerviewsampleandroid.constants.AppConstants;
import com.recyclerviewsampleandroid.interfaces.OnResponseListener_Volley;
import com.recyclerviewsampleandroid.utilities.AppLog;
import com.recyclerviewsampleandroid.utilities.AppUtil;

/**
 * GetAPIAsyncTask_Volley
 * Created by dhananjay on 14/1/16.
 */
public class GetAPIAsyncTask_Volley {

    private Context mContext;
    private String strURL_Get;
    private OnResponseListener_Volley mOnResponseListener_Volley;
    private String API_CALLED_TAG;

    private final String LOG_TAG = this.getClass().getSimpleName();

    public GetAPIAsyncTask_Volley(Context mContext, String strURL_Get, OnResponseListener_Volley
            mOnResponseListener_Volley, String API_CALLED_TAG) {

        this.mContext = mContext;
        this.strURL_Get = strURL_Get;
        this.mOnResponseListener_Volley = mOnResponseListener_Volley;
        this.API_CALLED_TAG = API_CALLED_TAG;

    }

    public void requestAsync_GetWordsData() {

        AppUtil.showProgressDialog(mContext, null);

        StringRequest strReq = new StringRequest(
                Request.Method.GET,
                strURL_Get,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        AppLog.d(LOG_TAG, API_CALLED_TAG + " API response:" + response);
                        AppUtil.hideProgressDialog();

                        mOnResponseListener_Volley.onResponseReceived_Volley(response, null);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppLog.e(LOG_TAG, API_CALLED_TAG + " API error: " + error.getMessage());
                        // hide the progress dialog
                        AppUtil.hideProgressDialog();
                        mOnResponseListener_Volley.onResponseReceived_Volley(null, error);
                        AppUtil.showAlert(mContext, mContext.getString(R.string.
                                OopsSomethingWentWrongPleaseTryABitAgainLater), null, true, null);
                    }
                }) {
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.VOLLEY_REQUEST_SOCKET_TIMEOUT_MS,
//                AppConstants.GET_PAYMENT_SCREEN_API_VOLLEY_REQUEST_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppLog.e(LOG_TAG, "before addToRequestQueue()" + API_CALLED_TAG);

        ApplicationDelegate.getApplicationInstance().addToRequestQueue(strReq, API_CALLED_TAG);
    }
}