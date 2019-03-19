package wennong.cai.countingdown;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ItemValues> items;
    private Resources resources;

    RecyclerAdapter(ArrayList<ItemValues> items, Context context){
        super();
        this.items = items;
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
        ItemValues task = items.get(position);
        int year = task.getItemYear();
        int month = task.getItemMonth();
        int day = task.getItemDay();

        viewHolder.itemTitle.setText(task.getItemTitle());
        long leftDays = calculateDayLeft(year,month,day);
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
            }
        });
    }

    private long calculateDayLeft(int year, int month, int day){
        final Month[] MONTHS = {Month.JANUARY, Month.FEBRUARY, Month.MARCH,
                Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST,
                Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER};

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate current = LocalDate.of(currentYear, MONTHS[currentMonth], currentDay);
        LocalDate due = LocalDate.of(year, MONTHS[month - 1], day);
        long noOfDaysBetween = ChronoUnit.DAYS.between(current, due);

        return noOfDaysBetween;
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
