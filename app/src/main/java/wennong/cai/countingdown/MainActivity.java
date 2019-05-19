package wennong.cai.countingdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Locale;

import wennong.cai.countingdown.utility.ContextWrapper;
import wennong.cai.countingdown.utility.Utilities;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting bottom navigation bar
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Setting the default fragment for the MainActivity
        frame = (FrameLayout) findViewById(R.id.mainCanvas);
        HomeFragment hf = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainCanvas, hf, "homeFragment")
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.alpha_order:
                break;
            case R.id.due_date_order:
                break;
            case R.id.day_left_order:
                break;
            default:
                break;
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    LoadingHome();
                    return true;
                case R.id.navigation_record:
                    LoadingRecord();
                    return true;
                case R.id.navigation_setting:
                    LoadingSetting();
                    return true;
            }
            return false;
        }
    };

    private void LoadingHome(){
        // Do something
        HomeFragment hf = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainCanvas, hf, "homeFragment").commit();
        hideKeyboard();
    }

    private void LoadingRecord(){
        RecordFragment rf = new RecordFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainCanvas, rf, "homeFragment").commit();
    }

    private void LoadingSetting(){
        SettingFragment rf = new SettingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainCanvas, rf, "settingFragment").commit();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public BottomNavigationView getNavigation(){
        return navigation;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // Setting language
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(newBase);
        String language_code = sharedPref.getString(Utilities.LANGUAGE_KEY, Locale.getDefault().getDisplayLanguage());
        System.out.println(language_code);

        Locale newLocale = new Locale(language_code);
        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }
}
