package sample.study.andy.andydemos.function.ipc;

/**
 * Created by Andy.chen on 2016/7/15.
 */
public interface IPCMessenger {
    public boolean isConnectService();
    public void sendMessage(String str);
}
