package wennong.cai.countingdown;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wennong.cai.countingdown.Provider.ItemValues;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ItemValues[] items = {};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemTitle.setText(items[position].getItemTitle());  // Need decide if the item already overdue

        viewHolder.itemDayLeft.setText("......"); //  Need calculation here !!!!!!!!!!!!!

        final int fPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // When an Item was clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemTitle;
        public TextView itemDayLeft;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDayLeft = (TextView)itemView.findViewById(R.id.item_day_left);

        }
    }
}
