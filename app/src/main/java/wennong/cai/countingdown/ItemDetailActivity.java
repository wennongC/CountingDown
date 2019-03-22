package wennong.cai.countingdown;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import wennong.cai.countingdown.Provider.ItemValues;
import wennong.cai.countingdown.Provider.SchemeItems;

public class ItemDetailActivity extends AppCompatActivity {

    private ContentResolver resolver;
    private int id;
    private ItemValues item;

    private EditText titleInput;
    private EditText descInput;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private static int year;
    private static int month;
    private static int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        resolver = getContentResolver();
        id = getIntent().getIntExtra("id", -1);
        item = getItemInstance(id);
        year = item.getItemYear();
        month = item.getItemMonth();
        dayOfMonth = item.getItemDay();

        titleInput = findViewById(R.id.titleInput);
        titleInput.setText(item.getItemTitle());

        // Wait to be Coded
    }

    public void onEditButtonClick(View view){
        // Wait to be Coded
    }

    public void onConfirmClick(View view){
        // Wait to be Coded
    }

    public void onCancelButtonClick(View view){
        onBackPressed();
    }

    private ItemValues getItemInstance(int id){
        String[] Args = {String.valueOf(id)};
        Cursor cursor = resolver.query(SchemeItems.Item.CONTENT_URI, SchemeItems.Item.PROJECTION, SchemeItems.Item.ID + " = ?", Args, null);
        if (cursor == null){
            return null;
        }
        if (cursor.moveToFirst()) {
            return new ItemValues(
                    cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ID)),
                    cursor.getString(cursor.getColumnIndex(SchemeItems.Item.ITEM_TITLE)),
                    cursor.getString(cursor.getColumnIndex(SchemeItems.Item.ITEM_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ITEM_YEAR)),
                    cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ITEM_MONTH)),
                    cursor.getInt(cursor.getColumnIndex(SchemeItems.Item.ITEM_DAY))
            );
        }
        return null;
    }
}
