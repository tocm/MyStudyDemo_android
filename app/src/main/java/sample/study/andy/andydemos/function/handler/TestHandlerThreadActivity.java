package sample.study.andy.andydemos.function.handler;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import sample.study.andy.andydemos.R;

/**
 * HandlerThread 和 Handler
 * android系统提供的Handler类内部的Looper默认绑定的是UI线程的消息队列，
 * 对于非UI线程又想使用消息机制，那么HandlerThread内部的Looper是最合适的，它不会干扰或阻塞UI线程。
 * HandlerThread 内部实现创建一个线程创建自已的looper,然后将looper 传给Handler.
 */
public class TestHandlerThreadActivity extends AppCompatActivity {
    private HandlerThread mHandlerThreadCheckUpdate;
    private Handler mHandlerCheckUpdate;//处理耗时线程handler
    private Handler mHandlerUI;//与UI线程管理的handler
    private boolean isUpdateInfo;
    private TextView mTextView;
    private static final int MSG_UPDATE_INFO = 0x110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler_thread);
        mTextView = (TextView) findViewById(R.id.textView2);
        initUIHandler();
        initHandlerThread();

        LruCache l;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止查询
        isUpdateInfo = false;
        mHandlerCheckUpdate.removeMessages(MSG_UPDATE_INFO);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThreadCheckUpdate.quitSafely();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        mHandlerCheckUpdate.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    private void initUIHandler() {
        mHandlerUI = new Handler();
    }

    private void initHandlerThread() {
        mHandlerThreadCheckUpdate = new HandlerThread("test-handlerThread");
        mHandlerThreadCheckUpdate.start();
        mHandlerCheckUpdate = new Handler(mHandlerThreadCheckUpdate.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d("test", "handleMessage  " + msg.what);
                switch (msg.what) {
                    case MSG_UPDATE_INFO:
                        checkForUpdate();
                        if (isUpdateInfo) {
                            mHandlerCheckUpdate.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 1000);
                        }
                        break;
                }

                return false;
            }
        });

    }

    /**
     * 模拟从服务器解析数据
     */
    private void checkForUpdate() {
        try {
            //模拟耗时
            Thread.sleep(1000);
            mHandlerUI.post(new Runnable() {
                @Override
                public void run() {
                    String result = "测试HandlerThread 后台耗时更新：<font color='red'>%d</font>";
                    result = String.format(result, (int) (Math.random() * 3000 + 1000));
                    mTextView.setText(Html.fromHtml(result));
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
