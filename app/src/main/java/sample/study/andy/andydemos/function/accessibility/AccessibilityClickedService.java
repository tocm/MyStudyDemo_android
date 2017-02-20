package sample.study.andy.andydemos.function.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.Iterator;
import java.util.List;

import sample.study.andy.andydemos.R;

/**
 * AccessibilityService是Android提供有某些障碍手机人群使用的辅助服务，可以帮助用户实现点击屏幕等一些帮助
 * 模拟自动按钮点击，响应
 * 还有可以完全自动抢红包等功能。
 */
public class AccessibilityClickedService extends AccessibilityService {

    private AccessibilityClickedService mAccessibilityClickedService;
    private final static String TAG = AccessibilityClickedService.class.getSimpleName();
    /**
     * 系统成功绑定该服务时被触发,也就是当你在设置中开启相应的服务,
     * 系统成功的绑定了该服务时会触发,通常我们可以在这里做一些初始化操作
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mAccessibilityClickedService = this;
        Log.d(TAG,"====onServiceConnected ====");
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d(TAG,"====unbindService ====");
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.d(TAG,"====onKeyEvent ====");
        return super.onKeyEvent(event);
    }

    @Override
    public List<AccessibilityWindowInfo> getWindows() {
        return super.getWindows();
    }

    /**
     * 获得事件信息
     * 有关AccessibilityEvent事件的回调函数.
     * 系统通过sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.d(TAG,"====onAccessibilityEvent ==== eventType = "+eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotificationEvents(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                String className = event.getClassName().toString();
                Log.d(TAG,"onAccessibilityEvent className = "+className);
                // 微信抢红包
                todoWechatRedPacket(event,className);
                //本地功能测试
                todoLocalFunctionTest(className);
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.d(TAG,"onAccessibilityEvent type TYPE_VIEW_CLICKED ");
                //界面点击
                todoWechatMsg();
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                //界面文字改动
                break;
        }
    }


    private void todoLocalFunctionTest(String className) {
        //测试自动点击本地页面按钮
        if (className.equals("sample.study.andy.andydemos.function.accessibility.AccessibilitySimulateActivity")) {
            Log.d(TAG,"simulateClickButton");
            //simulate click local button
            simulateClickButton();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void todoWechatMsg() {
        final AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo node = recycleFindMessage(rootNodeInfo);

        List<AccessibilityNodeInfo> accessibilityNodeInfoList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/if");
        for (AccessibilityNodeInfo nodeInfo : accessibilityNodeInfoList) {
            Log.d(TAG,"watch list find message by id = " +nodeInfo.getText());
        }

    }

    private void todoWechatRedPacket(AccessibilityEvent event,String className) {
        //微信聊天首页
        if (className.equals("com.tencent.mm.ui.LauncherUI")) {
            getPacket();
        } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.ChattingUI")) {
            watchList(event);
        } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
            //红包接收UI
            openPacket();
        } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
            close();
        } else {

        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG,"====onInterrupt ====");
    }
    private boolean checkIsGrantPermission() {
        return false;
    }

    /**
     * <p>
     * 服务参数设置:
     * 在AndroidManifest.xml声明了该服务之后,接下来就是需要对该服务进行一些参数设置了.
     * 该服务能够被配置用来接受指定类型的事件,监听指定package,检索窗口内容,获取事件类型的时间等等.
     * 现在我们对配置中的重要属性进行说明:
         accessibilityEventTypes:表示该服务对界面中的哪些变化感兴趣,即哪些事件通知,比如窗口打开,滑动,焦点变化,长按等.具体的值可以在AccessibilityEvent类中查到,如typeAllMask表示接受所有的事件通知.
         accessibilityFeedbackType:表示反馈方式,比如是语音播放,还是震动
         canRetrieveWindowContent:表示该服务能否访问活动窗口中的内容.也就是如果你希望在服务中获取窗体内容的化,则需要设置其值为true.
         notificationTimeout:接受事件的时间间隔,通常将其设置为100即可.
         packageNames:表示对该服务是用来监听哪个包的产生的事件
     * 目前有两种配置方法:
     * 方法一:4.0之后提供了可以通过<meta-data>标签进行配置
     * 方法二:通过setServiceInfo()进行配置
     * </p>
     */
    private void setServiceConfigInfo() {
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        serviceInfo.packageNames = new String[]{"sample.study.andy.andydemos"};
        serviceInfo.notificationTimeout=100;
        setServiceInfo(serviceInfo);
    }

    /**
     * 检测服务是否开启
        通常来说大体有一下两种方法来检测服务是否启用:
     方法一:借助服务管理器AccessibilityManager来判断,但是该方法不能检测app本身开启的服务.
     * @param servicePackageName
     * @return
     */
    public static boolean checkEnabledService(Context context,String servicePackageName) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        am.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
            @Override
            public void onAccessibilityStateChanged(boolean enabled) {
                Log.d(TAG,"onAccessibilityStateChanged enabled = "+enabled);
            }
        });
        List<AccessibilityServiceInfo> accessibilityServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            Log.d(TAG, "checkEnabledService is enabled all -->" + info.getId());
            if (servicePackageName.equals(info.getId())) {
                Log.d(TAG, "checkEnabledService is true" );
                return true;
            }
        }
        return false;
    }

    public AccessibilityClickedService getAccessibilityClickedService() {
        return mAccessibilityClickedService;
    }


    /**
     * 处理通知栏信息
     * 如果是微信红包的提示信息,则模拟点击
     *
     * @param event
     */
    private void handleNotificationEvents(AccessibilityEvent event) {
        Log.d(TAG,"handleNotificationEvents");
        List<CharSequence> texts = event.getText();
        event.getContentDescription();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                Log.d(TAG,"handleNotificationEvents get contents = "+content);
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if (content.contains(this.getResources().getString(R.string.wechat_packet))) {
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 关闭红包详情界面,实现自动返回聊天窗口
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void close() {
        Log.d(TAG,"close");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了关闭按钮的id
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gw");//ez
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : infos) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 模拟点击,拆开红包
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openPacket() {
        Log.d(TAG,"openPacket");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了红包控件的id
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bg7");
            Log.d(TAG, "openPacket list = = " + (list == null));
            if (list != null) {
                Log.d(TAG, "openPacket list size = " + list.size());
            }
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    private boolean watchList(AccessibilityEvent event) {
        AccessibilityNodeInfo eventSource = event.getSource();
        List<AccessibilityNodeInfo> nodes = eventSource.findAccessibilityNodeInfosByText("[微信红包]");
        //避免当订阅号中出现标题为“[微信红包]拜年红包”（其实并非红包）的信息时误判
        if (!nodes.isEmpty()) {
            AccessibilityNodeInfo nodeToClick = nodes.get(0);
            if (nodeToClick == null) return false;
            CharSequence contentDescription = nodeToClick.getContentDescription();
            Log.d(TAG,"watch list content description = "+contentDescription);
            if (contentDescription != null) {
                nodeToClick.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                return true;
            }
        }
        return false;
    }



    /**
     * 模拟点击,打开抢红包界面
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getPacket() {
        Log.d(TAG,"getPacket");
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        AccessibilityNodeInfo node = recycle(rootNode,"领取红包");

        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        AccessibilityNodeInfo parent = node.getParent();
        while (parent != null) {
            if (parent.isClickable()) {
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            parent = parent.getParent();
        }

    }



    /**
     * 递归查找当前聊天窗口中的红包信息
     *
     * 聊天窗口中的红包都存在"领取红包"一词,因此可根据该词查找红包
     *
     * @param node
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public AccessibilityNodeInfo recycle(AccessibilityNodeInfo node, String searchKey) {
        if (node.getChildCount() == 0) {
            if (node.getText() != null) {
                if (searchKey != null && searchKey.equals(node.getText().toString())) {
                    return node;
                }
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                if (node.getChild(i) != null) {
                    recycle(node.getChild(i),searchKey);
                }
            }
        }
        return node;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public AccessibilityNodeInfo recycleFindMessage(AccessibilityNodeInfo node) {
        if (node.getChildCount() == 0) {
            if (node.getText() != null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("控件名称:" + node.getClassName());
                stringBuffer.append("控件中的值：" + node.getText());
                stringBuffer.append("控件的ID:" + node.getViewIdResourceName());
                stringBuffer.append("点击是否出现弹窗:" + node.canOpenPopup());
                Log.d(TAG,"todoWechatMsg find message = "+stringBuffer.toString());


                return node;
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                if (node.getChild(i) != null) {
                    recycleFindMessage(node.getChild(i));
                }
            }
        }
        return node;
    }


    /**
     * 模拟自动点击本地按钮测试
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void simulateClickButton() {
        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        Log.d(TAG,"simulateClickButton entry");
        List<AccessibilityNodeInfo> accessibilityNodeInfoList = nodeInfo.findAccessibilityNodeInfosByViewId("sample.study.andy.andydemos:id/btn_accessibility_simulate_clicked");
        Iterator<AccessibilityNodeInfo> nodeInfoIterator = accessibilityNodeInfoList.iterator();
        Log.d(TAG,"nodeInfoIterator hasNext = "+nodeInfoIterator.hasNext());
        while(nodeInfoIterator.hasNext()) {
            AccessibilityNodeInfo targetNodeInfo = nodeInfoIterator.next();
            if (targetNodeInfo.isClickable()) {
                Log.d(TAG,"targetNode performAction ====");
                targetNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }
}
