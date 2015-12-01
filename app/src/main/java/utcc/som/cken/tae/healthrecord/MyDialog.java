package utcc.som.cken.tae.healthrecord;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Admin on 10/9/2015 AD.
 */
public class MyDialog {

    public void errorDialog(Context context, String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(context);
        objBuilder.setIcon(R.drawable.icon_question);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        objBuilder.show();


    } //errorDialog

} //Main Class
