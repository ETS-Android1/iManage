package com.example.nahimana.imanage.helpers;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ResponseDialog {
    private Context context;

    public ResponseDialog(final Context context) {
        this.context = context;
    }
    public  static void message(final Context context, String message){
        SweetAlertDialog sweet = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        sweet.setContentText(message);
        sweet.setTitle("Done");
        sweet.show();
        }
    public static void responseDialog(final Context context, String message, String title, String type) {
        SweetAlertDialog sw = null;
        switch (type) {
            case "success":
                sw = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                break;
            case "warning":
                sw = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                break;
            case "error":
                sw = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                break;
            case "default":
                sw = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
                break;
            default:

        }
        sw.setTitleText(title)
                .setContentText(message.replaceAll("['\"',',']"," \n"))
                .show();

    }

}

