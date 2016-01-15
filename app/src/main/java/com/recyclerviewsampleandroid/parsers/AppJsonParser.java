package com.recyclerviewsampleandroid.parsers;

import android.content.Context;

import com.recyclerviewsampleandroid.models.WordsModel;
import com.recyclerviewsampleandroid.utilities.AppLog;

import java.util.ArrayList;

/**
 * Created by dhananjay on 15/1/16.
 */
public class AppJsonParser {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private Context mContext;
    private String responseInJSON;

    public AppJsonParser(Context mContext, String responseInJSON) {
        this.mContext = mContext;
        this.responseInJSON = responseInJSON;
    }

    public ArrayList<WordsModel> parseWordsData() {
        AppLog.e(LOG_TAG, "in parseWordsData()");

        ArrayList<WordsModel> arrayList_WordsModel = null;

        return arrayList_WordsModel;
    }
}