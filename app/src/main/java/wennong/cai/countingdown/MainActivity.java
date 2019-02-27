package wennong.cai.countingdown;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        mTextMessage.setText(R.string.title_home); // Just a placeholder for now


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void LoadingRecord(){
        mTextMessage.setText(R.string.title_record);
        // Waiting to be coded
    }

    private void LoadingSetting(){
        mTextMessage.setText(R.string.title_setting);
        // Waiting to be coded
    }

}
