package wennong.cai.countingdown;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wennong.cai.countingdown.Provider.ItemValues;
import wennong.cai.countingdown.Provider.SchemeItems;
import wennong.cai.countingdown.utility.Utilities;

public class ItemDetailActivity extends AppCompatActivity {

    private ContentResolver resolver;
    private int id;
    private ItemValues item;
    private boolean editableFlag = false;

    private EditText titleInput;
    private EditText descInput;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button selectDateButton;
    private Button editButton;
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
        String displayDate = year + "/" + month + "/" + dayOfMonth;
        selectDateButton = findViewById(R.id.selectDate);
        selectDateButton.setText(displayDate);

        titleInput = findViewById(R.id.titleInput);
        titleInput.setText(item.getItemTitle());
        descInput = findViewById(R.id.descInput);
        if (item.getItemDescription() == null) descInput.setText(getString(R.string.no_desc));
        else descInput.setText(item.getItemDescription());

        editButton = findViewById(R.id.editButton);
        checkEditable();
    }

    public void onEditButtonClick(View view){
        if (editableFlag){
            editableFlag = false;
            editButton.setText(getString(R.string.editTask));
            editButton.setBackground(getDrawable(R.color.update_color_yellow));
            checkEditable();

            String title = titleInput.getText().toString();
            String desc = descInput.getText().toString();
            if (title.trim().length() == 0){
                Toast toast = Toast.makeText(this, R.string.no_title_warning, Toast.LENGTH_LONG);
                View toastView = toast.getView();
                toastView.setBackgroundColor(0xf0ff4757);
                TextView text = (TextView) toastView.findViewById(android.R.id.message);
                text.setTextColor(0xfff1f2f6);
                text.setTextSize(20);
                toast.show();
                return;
            }

            ContentValues cv = new ContentValues();
            cv.put(SchemeItems.Item.ITEM_TITLE, title);
            cv.put(SchemeItems.Item.ITEM_DESCRIPTION, desc);
            cv.put(SchemeItems.Item.ITEM_YEAR, year);
            cv.put(SchemeItems.Item.ITEM_MONTH, month);
            cv.put(SchemeItems.Item.ITEM_DAY, dayOfMonth);
            String[] args = {String.valueOf(id)};
            resolver.update(SchemeItems.Item.CONTENT_URI, cv, SchemeItems.Item.ID + " = ?", args);

        } else {
            editableFlag = true;
            editButton.setText(getString(R.string.updateTask));
            editButton.setBackground(getDrawable(R.color.insert_color_green));
            checkEditable();
        }
    }

    public void onCancelButtonClick(View view){
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_detail_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_button:
                Utilities.deleteConfirmationDialog(this, deleteButtonListener);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private DialogInterface.OnClickListener deleteButtonListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String[] args = {String.valueOf(id)};
            resolver.delete(SchemeItems.Item.CONTENT_URI, SchemeItems.Item.ID + " = ?", args);
            finish();
        }
    };

    private void checkEditable(){
        if (editableFlag){
            titleInput.setEnabled(true);
            descInput.setEnabled(true);

            // enable the Date Selector
            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month += 1;
                    ItemDetailActivity.year = year;
                    ItemDetailActivity.month = month;
                    ItemDetailActivity.dayOfMonth = dayOfMonth;
                    String date = year + "/" + month + "/" + dayOfMonth;
                    selectDateButton.setText(date);
                }
            };
            selectDateButton.setBackground(getDrawable(R.color.cancel_color_gray));
            selectDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectDateButtonClick(v);
                }
            });
        } else {
            titleInput.setEnabled(false);
            descInput.setEnabled(false);
            selectDateButton.setBackground(getDrawable(R.color.color_transparent));
            selectDateButton.setOnClickListener(null);
        }
    }

    public void onSelectDateButtonClick(View view){
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                R.style.Theme_AppCompat_Light_Dialog,
                dateSetListener,
                year, month - 1, dayOfMonth);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
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
        cursor.close();
        return null;
    }
}
