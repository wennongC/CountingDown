package wennong.cai.countingdown;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import wennong.cai.countingdown.Provider.SchemeItems;

public class RecordFragment extends Fragment {
    private ContentResolver resolver;
    private EditText titleInput;
    private EditText descInput;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private static int year;
    private static int month;
    private static int dayOfMonth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolver = getActivity().getApplicationContext().getContentResolver();

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        // reference EditText element
        titleInput = view.findViewById(R.id.titleInput);
        descInput = view.findViewById(R.id.descInput);

        // For cancel button
        Button back = view.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClick(v);
            }
        });
        // For add button
        Button add = view.findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick(v);
            }
        });
        // For date selector button
        final Button selectDate = view.findViewById(R.id.selectDate);
        String date = year + "/" + month + "/" + dayOfMonth;
        selectDate.setText(date);
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                RecordFragment.year = year;
                RecordFragment.month = month;
                RecordFragment.dayOfMonth = dayOfMonth;
                String date = year + "/" + month + "/" + dayOfMonth;
                selectDate.setText(date);
            }
        };
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectDateButtonClick(v);
            }
        });

        return view;
    }

    public void onSelectDateButtonClick(View view){

        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener,
                year, month - 1, dayOfMonth);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void onAddButtonClick(View view){
        String title = titleInput.getText().toString();
        String desc = descInput.getText().toString();

        if (title.trim().length() == 0){
            Toast toast = Toast.makeText(getContext(), R.string.no_title_warning, Toast.LENGTH_LONG);
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
        resolver.insert(SchemeItems.Item.CONTENT_URI, cv);

        ((MainActivity)getActivity()).getNavigation().setSelectedItemId(R.id.navigation_home);
    }

    public void onCancelButtonClick(View view){
        ((MainActivity)getActivity()).getNavigation().setSelectedItemId(R.id.navigation_home);
    }
}
