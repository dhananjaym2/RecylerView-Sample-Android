package com.recyclerviewsampleandroid.parsers;

import android.content.Context;

import com.recyclerviewsampleandroid.R;
import com.recyclerviewsampleandroid.models.WordsModel;
import com.recyclerviewsampleandroid.utilities.AppLog;
import com.recyclerviewsampleandroid.utilities.AppUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        try {
            JSONObject jObj_parent = new JSONObject(responseInJSON);
            JSONArray jArr_words = jObj_parent.getJSONArray("words");
            if (jArr_words != null) {
                arrayList_WordsModel = new ArrayList<>();

                for (int i = 0; i < jArr_words.length(); i++) {
                    JSONObject jObj_Temporary = jArr_words.getJSONObject(i);

                    if (jObj_Temporary != null) {

                        WordsModel wordsModel = new WordsModel();

                        /**
                         * ratio
                         */
                        double ratio = jObj_Temporary.getDouble("ratio");
                        wordsModel.setRatio(ratio);

                        if (ratio < 0) {
                            AppLog.d(LOG_TAG, "ratio < 0 not adding this");
                            continue;
                        }

                        /**
                         * id
                         */
                        int id = jObj_Temporary.getInt("id");
                        wordsModel.setId_WordsModel(id);

                        /**
                         * word
                         */
                        String word = jObj_Temporary.getString("word");
                        if (word != null) {
                            wordsModel.setWord(word);
                        } else {
                            wordsModel.setWord("");
                            AppLog.e(LOG_TAG, "id null in parseWordsData()");
                            AppUtil.showAlert(mContext, mContext.getString(R.string.CheckYourInternetConnection),
                                    mContext.getString(R.string.RetryNow), false, null);
                        }

                        /**
                         * variant
                         */
                        int variant = jObj_Temporary.getInt("variant");
                        wordsModel.setVariant(variant);

                        /**
                         * word
                         */
                        String meaning = jObj_Temporary.getString("meaning");
                        if (meaning != null) {
                            wordsModel.setMeaning(meaning);
                        } else {
                            wordsModel.setMeaning("");
                            AppLog.e(LOG_TAG, "id null in parseWordsData()");
                            AppUtil.showAlert(mContext, mContext.getString(R.string.CheckYourInternetConnection),
                                    mContext.getString(R.string.RetryNow), false, null);
                        }

                        arrayList_WordsModel.add(wordsModel);

                    } else {
                        AppLog.e(LOG_TAG, "jObj_Temporary null in parseWordsData()");
                        AppUtil.showAlert(mContext, mContext.getString(R.string.CheckYourInternetConnection),
                                mContext.getString(R.string.RetryNow), false, null);
                    }

                }

            } else {
                AppLog.e(LOG_TAG, "jArr_words null in parseWordsData()");
                AppUtil.showAlert(mContext, mContext.getString(R.string.CheckYourInternetConnection),
                        mContext.getString(R.string.RetryNow), false, null);
            }

        } catch (JSONException e) {
            e.printStackTrace();

            AppUtil.showAlert(mContext, mContext.getString(R.string.CheckYourInternetConnection),
                    mContext.getString(R.string.RetryNow), false, null);
        }

        return arrayList_WordsModel;
    }
}