package wennong.cai.countingdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingFragment extends Fragment {
    private String preference_file_key = "preference_file_key";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private int CARDVIEW_LEFT_COLOR;
    private int CARDVIEW_PAST_COLOR;
    private int CARDVIEW_TODAY_COLOR;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSharedPreference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        TextView text = view.findViewById(R.id.unImplemented);
        text.setText(R.string.wait_to_be_implemented);
        return view;
    }

    private void initSharedPreference(){
        Context context = getActivity();
        sharedPref = context.getSharedPreferences(
                preference_file_key, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    private void saveSharedPreferences(){
        editor.putInt("Key", 0);
        editor.apply();
    }

    private void restoreSharedPreferences() {
        int get = sharedPref.getInt("key", 0);
    }
}
