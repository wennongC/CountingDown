package wennong.cai.countingdown;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import wennong.cai.countingdown.utility.Utilities;

public class SettingFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private int CARDVIEW_LEFT_COLOR;
    private int CARDVIEW_PAST_COLOR;
    private int CARDVIEW_TODAY_COLOR;

    private String not_change_key = "Not Changed";
    private String language_setting = not_change_key;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSharedPreference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Set up the language selector spinner
        Spinner spinner = (Spinner)view.findViewById(R.id.language_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.language_array_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        // Set default item for the spinner if has.
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
        String lang = sharedPref.getString(Utilities.LANGUAGE_KEY, null);
        if(lang != null) {
            int spinnerPosition = myAdap.getPosition(transferSharedPreToArrayValue(lang));
            spinner.setSelection(spinnerPosition);
        }


        // Set save button click event
        Button saveButton = (Button)view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences();
            }
        });

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String selected = parent.getItemAtPosition(pos).toString();
        switch ( selected ){
            case "English":
                language_setting = "en";
                break;
            case "中文":
                language_setting = "zh";
                break;
            case "日本語":
                language_setting = "ja";
                break;
            default:
                language_setting = "default";
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void initSharedPreference(){
        Context context = getActivity();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void saveSharedPreferences(){
        editor = sharedPref.edit();
        if (language_setting.equals("default")) {
            editor.putString(Utilities.LANGUAGE_KEY, null);
        } else if ( ! language_setting.equals(not_change_key) ) {
            editor.putString(Utilities.LANGUAGE_KEY, language_setting);
        }
        editor.apply();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(R.string.language_setted)
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_menu_save)
                .create();
        dialog.show();

    }

    private void restoreSharedPreferences() {
        int get = sharedPref.getInt("key", 0);
    }

    private String transferSharedPreToArrayValue(String spString) {
        switch (spString) {
            case "en":
                return "English";
            case "zh":
                return "中文";
            case "ja":
                return "日本語";
            case "default":
                return "System Default";
            default:
                return "ERROR";
        }
    }
}
