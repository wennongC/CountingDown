package wennong.cai.countingdown;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import wennong.cai.countingdown.Provider.ItemValues;
import wennong.cai.countingdown.Provider.SchemeItems;
import wennong.cai.countingdown.utility.Utilities;

public class HomeFragment extends Fragment{
    private ContentResolver resolver;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolver = getActivity().getApplicationContext().getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        refreshAdapter();
        return view;
    }

    public void refreshAdapter(){
        adapter = new RecyclerAdapter(getData(), getActivity());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<ItemValues> getData(){
        Cursor cursor = resolver.query(SchemeItems.Item.CONTENT_URI, SchemeItems.Item.PROJECTION, null, null, SchemeItems.Item.ITEM_TITLE);
        ArrayList<ItemValues> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                items.add(new ItemValues(
                        cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ID)),
                        cursor.getString(cursor.getColumnIndex(SchemeItems.Item.ITEM_TITLE)),
                        cursor.getString(cursor.getColumnIndex(SchemeItems.Item.ITEM_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ITEM_YEAR)),
                        cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ITEM_MONTH)),
                        cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ITEM_DAY))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        switch (sharedPref.getInt(Utilities.ORDER_KEY, -1)) {
            case R.id.due_date_order:
                OrderByDueData(items);
                break;
            case R.id.day_left_order:
                OrderByDayLeft(items);
                break;
        }

        return items;
    }

    private void OrderByDueData(ArrayList<ItemValues> items) {
        for (int m = 0; m < items.size(); m++) {
            for (int n = m; n < items.size()-1; n++ ) {
                if( items.get(n).calculateDayLeft() > items.get(n+1).calculateDayLeft() ) {
                    Collections.swap(items, n, n+1);
                }
            }

        }
    }

    private void OrderByDayLeft(ArrayList<ItemValues> items) {
        ArrayList<ItemValues> leftList = new ArrayList<>();
        ArrayList<ItemValues> pastList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).calculateDayLeft() < 0) {
                pastList.add(items.get(i));
            } else {
                leftList.add(items.get(i));
            }
        }
        OrderByDueData(leftList);
        OrderByDueData(pastList);
        items.clear();
        items.addAll(leftList);
        items.addAll(pastList);
    }

}
