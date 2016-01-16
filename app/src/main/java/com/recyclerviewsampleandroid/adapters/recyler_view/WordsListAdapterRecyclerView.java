package com.recyclerviewsampleandroid.adapters.recyler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.recyclerviewsampleandroid.R;
import com.recyclerviewsampleandroid.application.ApplicationDelegate;
import com.recyclerviewsampleandroid.models.WordsModel;
import com.recyclerviewsampleandroid.utilities.AppLog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dhananjay on 14/1/16.
 */
public class WordsListAdapterRecyclerView extends RecyclerView.Adapter
        <WordsListAdapterRecyclerView.WordsViewHolder> {

    private Context mContext;
    private ArrayList<WordsModel> arrayList_WordsModel;
    private ImageLoader mImageLoader_volley;
    private final String AppLog_TAG = this.getClass().getSimpleName();

    public WordsListAdapterRecyclerView(Context mContext, ArrayList<WordsModel> arrayList_WordsModel) {

        this.mContext = mContext;
        this.arrayList_WordsModel = arrayList_WordsModel;
        mImageLoader_volley = ApplicationDelegate.getApplicationInstance().getImageLoader();

    }

    @Override
    public WordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_recyler_view_item
                , parent, false);

        AppLog.v(AppLog_TAG, "onCreateViewHolder()");
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WordsViewHolder holder, int position) {

        holder.txtWord_word_list_item.setText(arrayList_WordsModel.get(position).getWord());

        holder.txtMeaning_word_list_item.setText(arrayList_WordsModel.get(position).getMeaning());

        AppLog.v(AppLog_TAG, "onBindViewHolder() mContext.getString(R.string.ImageOfWordsUrl, position)" +
                mContext.getString(R.string.ImageOfWordsUrl, position));
        // holder.imgWordImage_word_list_item  //mContext.getString(R.string.ImageOfWordsUrl, position)

//        mImageLoader_volley.get(mContext.getString(R.string.ImageOfWordsUrl, position), new
//                ImageLoader.ImageListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(AppLog_TAG, "Image Load Error: " + error.getMessage());
//                        holder.imgWordImage_word_list_item.setImageDrawable(mContext.getResources().
//                                getDrawable(R.mipmap.dummy_image));
//                    }
//
//                    @Override
//                    public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
//                        if (response.getBitmap() != null) {
//                            // load image into imageview
//                            holder.imgWordImage_word_list_item.setImageBitmap(response.getBitmap());
//                        }
//                    }
//                });

        Picasso.with(mContext)
                .load(mContext.getString(R.string.ImageOfWordsUrl, position))
                .placeholder(R.mipmap.dummy_image)
                .error(R.mipmap.dummy_image)
                .into(holder.imgWordImage_word_list_item);
    }

    @Override
    public int getItemCount() {
        AppLog.v(AppLog_TAG, "getItemCount() arrayList_WordsModel.size():" + arrayList_WordsModel.size());
        return arrayList_WordsModel.size();
    }

    public class WordsViewHolder extends RecyclerView.ViewHolder {

        public TextView txtWord_word_list_item, txtMeaning_word_list_item;
        public ImageView imgWordImage_word_list_item;

        public WordsViewHolder(View itemView) {
            super(itemView);
            AppLog.v(AppLog_TAG, "WordsViewHolder()");

            txtWord_word_list_item = (TextView) itemView.findViewById(R.id.txtWord_word_list_item);
            txtMeaning_word_list_item = (TextView) itemView.findViewById(R.id.txtMeaning_word_list_item);
            imgWordImage_word_list_item = (ImageView) itemView.findViewById(R.id.
                    imgWordImage_word_list_item);
        }

    }
}