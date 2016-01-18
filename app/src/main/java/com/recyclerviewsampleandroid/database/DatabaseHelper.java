package com.recyclerviewsampleandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.recyclerviewsampleandroid.models.WordsModel;
import com.recyclerviewsampleandroid.utilities.AppLog;

import java.util.ArrayList;

/**
 * Created by dhananjay on 30/7/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WordsAppDB.sqlite";

    // Tables' names
    private static final String TABLE_WORDS = "words_table";

    /**
     * words Table Columns names
     */
    private static final String KEY_PRIMARY_KEY_ID = "primary_key_id";
    private static final String KEY_ID_WORDS_MODEL = "id_words_model";
    private static final String KEY_WORD = "word";
    private static final String KEY_VARIANT = "variant";
    private static final String KEY_MEANING = "meaning";
    private static final String KEY_RATIO = "ratio";


    private final String LOG_TAG = "DatabaseHelper";
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        AppLog.v(LOG_TAG, "in constructor DatabaseHelper()");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AppLog.v(LOG_TAG, "in onCreate()");
        try {

            String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "( "
                    + KEY_PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_ID_WORDS_MODEL + " INTEGER, "
                    + KEY_WORD + " TEXT, "
                    + KEY_VARIANT + " INTEGER, "
                    + KEY_MEANING + " TEXT, "
                    + KEY_RATIO + " REAL" + ")";
            AppLog.v(LOG_TAG, "Query CREATE_WORDS_TABLE:" + CREATE_WORDS_TABLE);
            db.execSQL(CREATE_WORDS_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            AppLog.v(LOG_TAG, "in onUpgrade()");
            // Drop older table if exists
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

            // Create tables again
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAllWordsData_arrayList(ArrayList<WordsModel> arrayList_WordsModel) {
        AppLog.v(LOG_TAG, "in insertAllWordsData_arrayList()");
        SQLiteDatabase db = null;
        long resultOfQuery;
        try {
            db = this.getWritableDatabase();

            if (arrayList_WordsModel != null) {

                for (WordsModel mWordsModel : arrayList_WordsModel) {

                    if (mWordsModel != null) {
                        ContentValues values = new ContentValues();

                        values.put(KEY_ID_WORDS_MODEL, mWordsModel.getId_WordsModel());
                        values.put(KEY_WORD, mWordsModel.getWord());
                        values.put(KEY_VARIANT, mWordsModel.getVariant());
                        values.put(KEY_MEANING, mWordsModel.getMeaning());
                        values.put(KEY_RATIO, mWordsModel.getRatio());

                        resultOfQuery = db.insert(TABLE_WORDS, null, values);

                        AppLog.v(LOG_TAG, "checking insertAllWordsData_arrayList() values:" +
                                values.toString() + " resultOfQuery:" + resultOfQuery);

                    } else
                        AppLog.e(LOG_TAG, "mWordsModel null in insertAllWordsData_arrayList()");

                }

            } else
                AppLog.e(LOG_TAG, "arrayList_WordsModel null in insertAllWordsData_arrayList()");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null && db.isOpen())
                db.close();
        }
    }

    public ArrayList<WordsModel> getAllWordsData_arrayList() {
        ArrayList<WordsModel> al_WordsModel = null;
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            Cursor cursor;
            String[] arrFieldsToGet = new String[]{KEY_ID_WORDS_MODEL, KEY_WORD,
                    KEY_VARIANT, KEY_MEANING, KEY_RATIO};

//            if (isTemplateCard)
//                cursor = db.query(TABLE_CARDS, arrFieldsToGet,
//                        KEY_IS_TEMPLATE_CARD + " =? ", new String[]{"1"}, null, null, null);
//            else
            cursor = db.query(TABLE_WORDS, arrFieldsToGet, null, null, null, null, null);


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    al_WordsModel = new ArrayList<WordsModel>();
                    do {

                        WordsModel mWordsModel = new WordsModel(cursor.getInt(0), cursor.getString(1)
                                , cursor.getInt(2), cursor.getString(3), cursor.getDouble(4));

                        al_WordsModel.add(mWordsModel);

                        AppLog.v(LOG_TAG, "after adding in al_cards returns true getAllCardDetails()");
                    } while (cursor.moveToNext());

                } else
                    AppLog.e(LOG_TAG, "cursor.moveToFirst()returns false");

                cursor.close();
            } else
                AppLog.e(LOG_TAG, "getAllCardDetails cursor null");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (db != null && db.isOpen())
                db.close();
        }
        return al_WordsModel;
    }
}