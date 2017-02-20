package sample.study.andy.andydemos.function.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Andy.chen on 2016/7/15.
 *
 * 主要解决进程间通信
 * Android进程间通信实现方式有：
 * 1.AIDL文件，然后通过aapt生成的类方便的完成服务端;
 * 2.Broadcast
 * 3.Intent
 * 4. Messenger 实现基于消息的进程间通信的方式。
 * 源码分析
        其实Messenger的内部实现的，实际上也是依赖于aidl文件实现的。
 */
public class MessengerClient implements IPCMessenger{
    protected static final String TAG = MessengerClient.class.getSimpleName();
    private Messenger mMessengerSend;
    private Messenger mMessengerReply;
    private Context mContext;
    private boolean mIsConnectedService;
    public MessengerClient(Context context) {
        this.mContext = context;
        mMessengerReply = new Messenger(new HandlerReply(this));
        bindService();
    }
    private void bindService() {
        Intent intent = new Intent(mContext,MessengerService.class);
        mContext.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected");
                mIsConnectedService = true;
                mMessengerSend = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIsConnectedService = false;
            }
        },Context.BIND_AUTO_CREATE);
    }

    public void sendMessage(String string) {
        Log.d(TAG, "sendMessage msg = "+string);
        Message message = Message.obtain(null,MessengerService.MESSAGE_WHAT_TYPE_SEND);
        message.replyTo = mMessengerReply;
        try {
            if (mMessengerSend != null) {
                Log.d(TAG, "sendMessage msg = "+string);
                mMessengerSend.send(message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private static class HandlerReply extends Handler {
        WeakReference<MessengerClient> messengerClientWeakReference;
        public HandlerReply(MessengerClient messengerClient) {
            messengerClientWeakReference = new WeakReference<MessengerClient>(messengerClient);
        }
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "回调成功");
            switch (msg.what) {
                case MessengerService.MESSAGE_WHAT_TYPE_REPLY:
                    Toast.makeText(messengerClientWeakReference.get().mContext,"messenger 回调成功",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    public boolean isConnectService() {
        return mIsConnectedService;
    }
}
