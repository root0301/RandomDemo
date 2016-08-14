package random.example.com.randomdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by slience on 2016/8/14.
 */

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    private String[] name;

    private String[] num;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_list);
        initdata();
        initRecycler();
    }

    private void initdata() {
        name = getResources().getStringArray(R.array.foodName);
        for (int i = 1; i<=name.length; i++) {
            name[i] = String.valueOf(i);
        }
    }

    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this,name,num);
        recyclerView.setAdapter(adapter);
    }
}
