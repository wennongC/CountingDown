package wennong.cai.countingdown;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import wennong.cai.countingdown.Provider.ItemValues;
import wennong.cai.countingdown.Provider.SchemeItems;

public class HomeFragment extends Fragment{
    private ContentResolver resolver;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<ItemValues> items;

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
        getData();
        adapter = new RecyclerAdapter(items);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getData(){
            Cursor cursor = resolver.query(SchemeItems.Item.CONTENT_URI, SchemeItems.Item.PROJECTION, null, null);
            items = new ArrayList<>();
            int i = 0;
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
                    i++;
                    // do what ever things left
                } while (cursor.moveToNext());
            }

            cursor.close();
    }
}
