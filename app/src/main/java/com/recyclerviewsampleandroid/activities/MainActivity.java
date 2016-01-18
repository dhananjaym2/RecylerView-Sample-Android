package com.recyclerviewsampleandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.VolleyError;
import com.recyclerviewsampleandroid.R;
import com.recyclerviewsampleandroid.adapters.recyler_view.WordsListAdapterRecyclerView;
import com.recyclerviewsampleandroid.constants.AppConstants;
import com.recyclerviewsampleandroid.database.DatabaseHelper;
import com.recyclerviewsampleandroid.interfaces.OnResponseListener_Volley;
import com.recyclerviewsampleandroid.models.WordsModel;
import com.recyclerviewsampleandroid.parsers.AppJsonParser;
import com.recyclerviewsampleandroid.utilities.AppLog;
import com.recyclerviewsampleandroid.utilities.AppUtil;
import com.recyclerviewsampleandroid.volley.GetAPIAsyncTask_Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnResponseListener_Volley {

    private RecyclerView recyclerView_wordsList;
    private WordsListAdapterRecyclerView mWordsListAdapterRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<WordsModel> arrayList_WordsModel;
    private OnResponseListener_Volley mOnResponseListener_Volley = this;
    private final String LOG_TAG = this.getClass().getSimpleName();
    private View.OnClickListener mOnClickListener_alert;
    private DatabaseHelper mDatabaseHelper;
    private Thread mThread_saveInDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getWordsData();
    }

    private void initView() {

        recyclerView_wordsList = (RecyclerView) findViewById(R.id.recyclerView_wordsList);

        mLayoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView_wordsList.setLayoutManager(mLayoutManager);

        mOnClickListener_alert = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppUtil.isInternetNetworkConnected(MainActivity.this)) {
                    AppUtil.mDialog.dismiss();
                    getWordsData();
                }
            }
        };
    }

    private void getWordsData() {
        // TODO check if data is NOT available in DB then call API

        mDatabaseHelper = new DatabaseHelper(MainActivity.this);
        arrayList_WordsModel = mDatabaseHelper.getAllWordsData_arrayList();

        if (arrayList_WordsModel != null) {

            setWordsAdapter();

        } else if (AppUtil.isInternetNetworkConnected(MainActivity.this)) {
            String strURL_getWordsData = getString(R.string.GetWordsUrl);

            GetAPIAsyncTask_Volley mGetAPIAsyncTask_Volley = new GetAPIAsyncTask_Volley(
                    MainActivity.this, strURL_getWordsData, mOnResponseListener_Volley, AppConstants.
                    API_GET_WORDS_DATA);

            mGetAPIAsyncTask_Volley.requestAsync_GetWordsData();
        } else {

            AppUtil.showAlert(this, getString(R.string.CheckYourInternetConnection), getString(R.string.
                    RetryNow), false, mOnClickListener_alert);
        }
    }

    @Override
    public void onResponseReceived_Volley(String response, VolleyError error) {
        if (response != null) {

            AppJsonParser mAppJsonParser = new AppJsonParser(this, response);

            arrayList_WordsModel = mAppJsonParser.parseWordsData();

            setWordsAdapter();

            saveWordsDataInDB();

        } else if (error != null) {

            AppLog.e(LOG_TAG, "onResponseReceived_Volley() response null error:" + error.
                    getLocalizedMessage());
        }
    }

    private void setWordsAdapter() {
        AppLog.d(LOG_TAG, "setWordsAdapter");

        if (arrayList_WordsModel != null) {
            AppLog.d(LOG_TAG, "arrayList_WordsModel.size():" + arrayList_WordsModel.size());

            mWordsListAdapterRecyclerView = new WordsListAdapterRecyclerView(MainActivity.this,
                    arrayList_WordsModel);
            recyclerView_wordsList.setAdapter(mWordsListAdapterRecyclerView);

        } else
            AppLog.d(LOG_TAG, "arrayList_WordsModel null");
    }

    private void saveWordsDataInDB() {

        if (mThread_saveInDatabase != null) {

            if (!mThread_saveInDatabase.isInterrupted())
                mThread_saveInDatabase.interrupt();

        } else {

            mThread_saveInDatabase = new Thread(new Runnable() {
                @Override
                public void run() {

                    AppLog.d(LOG_TAG, "in run() of mThread_saveInDatabase");
                    mDatabaseHelper.insertAllWordsData_arrayList(arrayList_WordsModel);

                }
            });
        }

        AppLog.d(LOG_TAG, "just before calling start() of mThread_saveInDatabase");

        mThread_saveInDatabase.start();
    }
}