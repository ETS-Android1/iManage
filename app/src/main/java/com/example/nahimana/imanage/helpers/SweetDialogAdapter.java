package com.example.nahimana.imanage.helpers;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

final public class SweetDialogAdapter {
   public SweetAlertDialog sad;
    public Context context;
    private static SweetDialogAdapter sda;

    public SweetDialogAdapter(Context context) {
        this.context = context;
    }
    public static synchronized SweetDialogAdapter getInstance(Context context){
        if(sda == null){
            sda = new SweetDialogAdapter(context);
        }
        return sda;
    }

    public  void successDialog(String message, String title){
        sad = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sad.setTitleText(title)
                .setContentText(message)
                .show();
    }
    public  void warningDialog(String message, String title){
        sad = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sad.setTitleText(title)
                .setContentText(message)
                .show();
    }
    public  void dangerDialog(String message, String title){
        sad = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sad.setTitleText(title)
                .setContentText(message)
                .show();
    }
}
