package com.recyclerviewsampleandroid.utilities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.recyclerviewsampleandroid.R;

/**
 * Created by dhananjay on 14/1/16.
 */
public class AppUtil {

    public static ProgressDialog mProgressDialog;
    public static Dialog mDialog;

    /**
     * method to check if internet is connected or not
     *
     * @param mContext Context
     * @return true if connected else false
     */
    public static boolean isInternetNetworkConnected(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();

    }


    /**
     * static method to SHOW progress dialog for the App.
     * <p/>
     * use Utility.hideProgressDialog(mContext)to hide this dialog.
     *
     * @param mContext                   Context
     * @param strMessageOnProgressDialog String Message On Progress Dialog
     */
    public static void showProgressDialog(Context mContext, String strMessageOnProgressDialog) {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {

                /**
                 * to change the message showing on mProgressDialog
                 */
                if (strMessageOnProgressDialog != null) {
                    mProgressDialog.setMessage(strMessageOnProgressDialog);
                } else {
                    try {
                        mProgressDialog.setMessage(mContext.getString(R.string.LoadingProgressDialog));
                    } catch (Exception e) {
                        AppLog.showExceptionLogAndToast(mContext, "showProgressDialog", e);
                    }
                }
                return;
            }
        } else
            mProgressDialog = new ProgressDialog(mContext);
        if (strMessageOnProgressDialog != null) {
            mProgressDialog.setMessage(strMessageOnProgressDialog);
        } else {
            mProgressDialog.setMessage(mContext.getString(R.string.LoadingProgressDialog));
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * static method to HIDE progress dialog for the App.
     */
    public static void hideProgressDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
    }


    public static void showAlert(final Context mContext, String strAlertMessage,
                                 String strBtnText, boolean isCancellable, View.OnClickListener
                                         mOnClickListener) {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = null;
        }
        mDialog = new Dialog(mContext, R.style.Dialog_No_Border);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.alert_no_header);
        mDialog.setCancelable(isCancellable);
        TextView txtAlertText_no_header = (TextView) mDialog.findViewById(R.id.txtAlertText_no_header);
        Button btnAlert_no_header = (Button) mDialog.findViewById(R.id.btnAlert_no_header);
        if (mOnClickListener != null) {
            btnAlert_no_header.setOnClickListener(mOnClickListener);
        } else {
            btnAlert_no_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }
        txtAlertText_no_header.setText(strAlertMessage);
        if (strBtnText != null) {
            btnAlert_no_header.setText(strBtnText);
        }

        mDialog.show();

    }

    public static float pxFromDp(/*final*/ Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
