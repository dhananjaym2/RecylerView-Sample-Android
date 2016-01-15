package com.recyclerviewsampleandroid.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.recyclerviewsampleandroid.utilities.AppLog;
import com.recyclerviewsampleandroid.volley.LruBitmapCache;

/**
 * Created by dhananjay on 14/1/16.
 */
public class ApplicationDelegate extends Application {


    public static ApplicationDelegate ApplicationDelegateInstance = null;
    private static final String LOG_TAG = ApplicationDelegate.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationDelegateInstance = this;
    }

    /**
     * Method to initialize an object
     *
     * @return application object
     */
    public static synchronized ApplicationDelegate getApplicationInstance(/*Context mContext*/) {

        if (ApplicationDelegateInstance == null) {
            ApplicationDelegateInstance = new ApplicationDelegate();
        }

        return ApplicationDelegateInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            if (getApplicationContext() != null)
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            else
                AppLog.e(LOG_TAG, "getApplicationContext() null");
        }

        VolleyLog.DEBUG = false;// to stop volley Logs

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? LOG_TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(LOG_TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        AppLog.v(LOG_TAG, "in cancelPendingRequests() of volley tag:" + tag);
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}