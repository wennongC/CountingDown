package wennong.cai.countingdown.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import java.util.Locale;

import wennong.cai.countingdown.R;

public class Utilities {
    public static int CARDVIEW_LEFT_COLOR = 0xff7bed9f;
    public static int CARDVIEW_PAST_COLOR = 0xffa4b0be;
    public static int CARDVIEW_TODAY_COLOR = 0xffff6b81;

    public static enum OrderBase { Alpha, DueDate, DayLeft }
    public static OrderBase ItemOrder = OrderBase.DayLeft;

    // The key to Shared Preferences
    public static String preference_file_key = "preference_file_key";
    public static String LANGUAGE_KEY = "language_code";
    public static String ORDER_KEY = "order_key";

    // Used to set all card view background setting back to the default
    public static void cardViewColorToDefault(){
        Utilities.CARDVIEW_LEFT_COLOR = 0xff7bed9f;
        Utilities.CARDVIEW_PAST_COLOR = 0xffa4b0be;
        Utilities.CARDVIEW_TODAY_COLOR = 0xffff6b81;
    }

    // Used to generate the delete confirmation dialog
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

    public static int getApiLevel(){
        return Build.VERSION.SDK_INT;
    }

}
