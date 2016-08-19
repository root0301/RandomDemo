package random.example.com.randomdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private HTextView hTextView;

    private TextView textMessage;

    private ImageView imageView;

    private boolean isStart = false;

    private boolean isAlreadyChoose = false;

    private Handler handler = null;

    private static final int INFO = 1234;

    private static final String TAG = "DEBUG";

    private Thread thread = null;

    private FloatingActionButton choose,add;

    private int eat = -1;

    private int count = 0;

    private String[] foodName;

    private String[] imgURL;

    private Map<String,Integer> foodSet;

    private List<String> transName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hTextView = (HTextView) findViewById(R.id.text);
        textMessage = (TextView) findViewById(R.id.text_tag);
        imageView = (ImageView) findViewById(R.id.profile_image);
        foodSet = new HashMap<String,Integer>();
        transName = new ArrayList<String>();
        hTextView.setAnimateType(HTextViewType.SCALE);
        hTextView.animateText("该吃啥");
        foodName = getResources().getStringArray(R.array.foodName);
        imgURL = getResources().getStringArray(R.array.imgURL);
        choose = (FloatingActionButton) findViewById(R.id.fab_start);
        add = (FloatingActionButton) findViewById(R.id.fab_add);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = !isStart;
                if (isStart == false) {
                    Log.d(TAG,String.valueOf(eat));
                    DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                            .with(MainActivity.this)
                            .load(R.mipmap.wait);
                    Glide.with(MainActivity.this)
                            .load(imgURL[eat])
                            .thumbnail(thumbnailRequest)
                            .into(imageView);
                    textMessage.setText("满意则添加，不满意可再次选择");
                    hTextView.animateText(foodName[eat]);
                    hTextView.setAnimateType(HTextViewType.LINE);

                } else {
                    count ++;
                    isAlreadyChoose = false;
                    textMessage.setText("点击左边按钮停止");
                    hTextView.setAnimateType(HTextViewType.SCALE);
                    if (count > 3) {
                        CharSequence str = "都第"+count+"次了，赶紧选";
                        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,String.valueOf(isAlreadyChoose));
                if (isStart==false && eat!=-1 && !isAlreadyChoose) {
                    isAlreadyChoose = true;
                    CharSequence str = "你已选择"+foodName[eat]+",已添加";
                    Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();

                    if (transName.contains(foodName[eat])) {
                        int num = foodSet.get(foodName[eat]);
                        foodSet.put(foodName[eat],num+1);
                    } else {
                        transName.add(foodName[eat]);
                        foodSet.put(foodName[eat],1);
                    }
                } else if (eat == -1){
                    CharSequence str = "请先点击开始按钮选择后才可添加";
                    Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
                } else if (isAlreadyChoose == true) {
                    CharSequence str = "已经添加了";
                    Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INFO:
                        String str = msg.obj.toString();
                        eat = Integer.parseInt(str);
                        if (isStart)
                            hTextView.animateText(foodName[eat]);
			break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        launch();
        thread.start();
    }

    private void launch() {
        thread = new Thread() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    try {
                        while (isStart) {
                            Thread.sleep(100);
                            int choose = random.nextInt(100);
                            Message message = new Message();
                            message.what = INFO;
                            message.obj = choose;
                            handler.sendMessage(message);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,ListActivity.class);
            Bundle bundle = new Bundle();
            SerializableMap myMap=new SerializableMap();
            SerializableList myList=new SerializableList();
            myMap.setMap(foodSet);
            myList.setList(transName);
            bundle.putSerializable("map",myMap);
            bundle.putSerializable("list",myList);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
