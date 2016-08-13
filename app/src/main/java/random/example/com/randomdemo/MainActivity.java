package random.example.com.randomdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class MainActivity extends AppCompatActivity {


    private HTextView hTextView;

    private TextView textMessage;

    private boolean isStart = false;

    private boolean isAlreadyChoose = false;

    private Handler handler = null;

    private static final int INFO = 1234;

    private static final String TAG = "DEBUG";

    private Thread thread = null;

    private FloatingActionButton choose,add;

    private int eat = -1;

    private int count = 0;

    private String[] food = {"红烧","小碗菜","水煮肉","豆浆油条","烤冷面","麻辣香锅","生煎大排"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hTextView = (HTextView) findViewById(R.id.text);
        textMessage = (TextView) findViewById(R.id.text_tag);
        hTextView.setAnimateType(HTextViewType.SCALE);
        hTextView.animateText("该吃啥");
        choose = (FloatingActionButton) findViewById(R.id.fab_start);
        add = (FloatingActionButton) findViewById(R.id.fab_add);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = !isStart;
                if (isStart == false) {
                    textMessage.setText("满意则添加，不满意可再次选择");
                    hTextView.setAnimateType(HTextViewType.LINE);
                } else {
                    count ++;
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
                if (eat!=-1 && isAlreadyChoose == false) {
                    isAlreadyChoose = true;
                    CharSequence str = "你已选择"+food[eat]+",已添加";
                    Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
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
                        hTextView.animateText(str);
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
                while (true) {
                    try {
                        while (isStart) {
                            Thread.sleep(300);
                            eat ++;
                            Message message = new Message();
                            message.what = INFO;
                            message.obj = food[eat];
                            handler.sendMessage(message);
                            if (eat ==6) eat =0;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
