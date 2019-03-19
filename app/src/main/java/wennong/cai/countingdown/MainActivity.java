package wennong.cai.countingdown;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigation.setSelectedItemId(item.getItemId());
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
    }

    private void LoadingRecord(){
        RecordFragment rf = new RecordFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainCanvas, rf, "homeFragment").commit();
    }

    private void LoadingSetting(){
        // Waiting to be coded
    }

    public BottomNavigationView getNavigation(){
        return navigation;
    }

}
