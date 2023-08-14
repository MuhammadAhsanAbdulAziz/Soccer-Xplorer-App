package com.example.soccerxplorer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.navigation.NavController;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UtilManager {

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void SuccessMessage(Context context, NavController navController) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setTitleText("IMPORTANT").
                setContentText("Item Updated Successfully").setConfirmText("Okay").setConfirmClickListener(sweetAlertDialog -> {
                    navController.popBackStack();
                    sweetAlertDialog.cancel();
                    sweetAlertDialog.dismiss();
                }).show();

    }

    public static void errorMessage(Context context,String msg) {

        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(msg).show();
    }
}
