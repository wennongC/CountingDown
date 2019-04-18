package wennong.cai.countingdown;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import wennong.cai.countingdown.Provider.ItemValues;
import wennong.cai.countingdown.Provider.SchemeItems;
import wennong.cai.countingdown.utility.UtilityFunctions;

/*

 This class is the adapter for the RecyclerView.
 It creates viewHolder for each task record in the SQLite
    including setting the click listener

*/

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ItemValues> items;
    private Resources resources;
    private Context activity;

    RecyclerAdapter(ArrayList<ItemValues> items, Context context){
        super();
        this.items = items;
        this.activity = context;
        resources = context.getResources();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ItemValues task = items.get(position);
        int year = task.getItemYear();
        int month = task.getItemMonth();
        int day = task.getItemDay();

        viewHolder.itemTitle.setText(task.getItemTitle());
        long leftDays = task.calculateDayLeft();
        String dueString = "";
        if (leftDays > 0){
            dueString = resources.getString(R.string.left_string_1) + String.valueOf(leftDays) + resources.getString(R.string.left_string_2);
            viewHolder.cardView.setCardBackgroundColor(0xff7bed9f);
            // viewHolder.cardView.setCardBackgroundColor(Color.argb(1, 123, 237, 159));
        } else if (leftDays < 0) {
            dueString = resources.getString(R.string.past_string_1) + String.valueOf(-leftDays) + resources.getString(R.string.past_string_2);
            viewHolder.cardView.setCardBackgroundColor(0xffa4b0be);
            // viewHolder.cardView.setCardBackgroundColor(Color.argb(1, 164, 176, 190));
        } else {
            dueString = resources.getString(R.string.today_string);
            viewHolder.cardView.setCardBackgroundColor(0xffff6b81);
            // viewHolder.cardView.setCardBackgroundColor(Color.argb(1, 255, 107, 129));
        }
        viewHolder.itemDayLeft.setText(dueString);


        final int fPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // When an Item was clicked
                Intent intent = new Intent(activity, ItemDetailActivity.class);
                intent.putExtra("id", task.getId());
                activity.startActivity(intent);
            }
        });

        // OnClickListener, will be used for delete button in Long-click menu.
        final DialogInterface.OnClickListener deleteButtonListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String[] args = {String.valueOf(task.getId())};
                activity.getContentResolver().delete(SchemeItems.Item.CONTENT_URI, SchemeItems.Item.ID + " = ?", args); // Remove for database
                items.remove(fPosition);    // Remove for the current RecyclerView
                notifyItemRemoved(fPosition);
            }
        };

        // On Long Click Listener
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle(task.getItemTitle())
                        .setMessage(activity.getString(R.string.task_due) + ": " + task.getDueDateAsString())
                        .setPositiveButton(R.string.detail, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(activity, ItemDetailActivity.class);
                                intent.putExtra("id", task.getId());
                                activity.startActivity(intent);
                            }
                        })
                        .setNeutralButton(R.string.delete_button,  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UtilityFunctions.deleteConfirmationDialog(activity, deleteButtonListener);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .setIcon(android.R.drawable.btn_star)
                        .create();

                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getColor(R.color.dark_green));
                        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED);
                    }
                });

                dialog.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemTitle;
        public TextView itemDayLeft;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDayLeft = (TextView)itemView.findViewById(R.id.item_day_left);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }
}
