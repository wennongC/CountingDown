package wennong.cai.countingdown;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import wennong.cai.countingdown.Provider.ItemValues;
import wennong.cai.countingdown.Provider.SchemeItems;

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


            // wait to be coded   (Update the record in the database)


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

        // Wait to be coded

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Wait to be coded

        return super.onOptionsItemSelected(item);
    }

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
