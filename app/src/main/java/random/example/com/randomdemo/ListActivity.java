package random.example.com.randomdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slience on 2016/8/14.
 */

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    private String[] name = null;

    private String[] num = null;

    private String TAG = "DEBUG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //initdata();
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap)bundle.get("map");
        SerializableList serializableList = (SerializableList)bundle.get("list");
        Map<String,Integer> map = new HashMap<String,Integer>();
        List<String> list = new ArrayList<String>();
        map = serializableMap.getMap();
        list = serializableList.getList();
        initRecycler(map,list);
    }


    private void initdata() {
        name = getResources().getStringArray(R.array.foodName);
        num = new String[name.length];
        for (int i = 0; i<name.length; i++) {
            num[i] = String.valueOf(i);
        }
    }

    private void initRecycler(Map<String,Integer> map, List<String> list) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this,map,list);
        recyclerView.setAdapter(adapter);
    }
}
