package wennong.cai.countingdown.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import wennong.cai.countingdown.R;

public class UtilityFunctions {

    public static void deleteConfirmationDialog(final Context context, DialogInterface.OnClickListener listener){
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.delete_button)
                .setMessage(R.string.delete_alert_message)
                .setPositiveButton(R.string.yes, listener)
                .setNegativeButton(R.string.cancel, null)
                .setIcon(android.R.drawable.ic_delete)
                .create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            }
        });
        dialog.show();
    }

}
