package random.example.com.randomdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by slience on 2016/8/21.
 */

public class HandlerTest extends Activity {
/*
    //定义周期显示图片的ID
    int imageIds[] = new int[] {
            R.mipmap.add,
            R.mipmap.start,
            R.mipmap.ic_launcher,
            R.mipmap.wait
    };
    int currentImageId = 0;
    private ImageView imageView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_activity);
        imageView = (ImageView) findViewById(R.id.handler_img);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x222) {
                    imageView.setImageResource(imageIds[currentImageId++%imageIds.length]);
                }
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x222);
            }
        },0,1500);
    }*/


    static final String UPPER_NUM = "upper";
    EditText editText;
    MyThread myThread;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_activity);
        editText = (EditText) findViewById(R.id.edit_num);
        button = (Button) findViewById(R.id.bt);
        myThread = new MyThread();
        myThread.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 0x222;
                Bundle bundle = new Bundle();
                bundle.putInt(UPPER_NUM,Integer.parseInt(editText.getText().toString()));
                msg.setData(bundle);
                myThread.myHandler.sendMessage(msg);
            }
        });
    }

    class MyThread extends Thread
    {
        public Handler myHandler;

        @Override
        public void run() {
            Looper.prepare();
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x222) {
                        int upper = msg.getData().getInt(UPPER_NUM);
                        List<Integer> nums = new ArrayList<Integer>();
                        outer:
                        for (int i=2;i<=upper;i++) {
                            for (int j=2;j<Math.sqrt(i);j++) {
                                if (i!=2 && i%j==0) {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        Toast.makeText(HandlerTest.this,nums.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            };
            Looper.loop();
        }
    }




}
