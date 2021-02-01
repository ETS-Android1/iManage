package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import android.text.InputType;
import android.widget.EditText;

public class PaymentAlertDialog{
    
    public static void showDialog(final Context context, final String paymentType, final String debitOrCreditId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Pay ");
        final EditText amountInput = new EditText(context);
        amountInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogBuilder.setView(amountInput);

        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Payment(context, amountInput.getText().toString(), debitOrCreditId, paymentType);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.show();
    }

}
