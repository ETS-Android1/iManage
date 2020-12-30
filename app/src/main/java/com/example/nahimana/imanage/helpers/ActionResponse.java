package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.nahimana.imanage.MainActivity;
import com.example.nahimana.imanage.User;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActionResponse {
    Context context;
    private static ActionResponse response;

    public ActionResponse(Context context) {
        this.context = context;
    }
    public static synchronized ActionResponse getInstance(Context context){
        if(response == null){
            response = new ActionResponse(context);
        }
        return response;
    }

    public void formatMessage(String title, String message, String confirmText, String cancelButtonText){
       new SweetAlertDialog(context)
               .setTitleText(title)
               .setContentText(message)
               .setConfirmText(confirmText)
               .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                   @Override
                   public void onClick(SweetAlertDialog sd) {
                       sd.dismissWithAnimation();
                   }
               }).setCancelButton(cancelButtonText, new SweetAlertDialog.OnSweetClickListener(){

           @Override
           public void onClick(SweetAlertDialog sd) {
               sd.dismissWithAnimation();
           }
       }).show();
   }
}
