package sample.study.andy.andydemos.function.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * 启动service分为两种：startService,bindService.
 * 各自执行的生命周期顺序：
 * startService生命周期： onCreate->onStartCommand->onDestroy
 * bindService生命周期： onCreate->onBind->onUnBind->onDestroy
 * Created by Andy.chen on 2016/7/15.
 */
public class MessengerService extends Service {
    protected static final String TAG = MessengerService.class.getSimpleName();
    protected final static int MESSAGE_WHAT_TYPE_SEND = 1;
    protected final static int MESSAGE_WHAT_TYPE_REPLY = 2;

    /**
     * 用于bindService
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind 成功");
        return new Messenger(handler).getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate ");
    }

    /**
     * 用于bindService
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy ");
        super.onDestroy();
    }

    /**
     * 用于 startService 才回调
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand ");
        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WHAT_TYPE_SEND:
                    Log.d(TAG, "收到消息");
                    Toast.makeText(MessengerService.this,"messenger 回调成功",Toast.LENGTH_SHORT).show();
                    //获取客户端message中的Messenger，用于回调
                    final Messenger callback = msg.replyTo;
                    try {
                        // 回调
                        Message message = Message.obtain(null,MESSAGE_WHAT_TYPE_REPLY);

                        callback.send(message);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
