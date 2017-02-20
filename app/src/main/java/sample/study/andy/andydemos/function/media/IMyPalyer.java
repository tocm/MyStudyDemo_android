package sample.study.andy.andydemos.function.media;

/**
 * Created by Andy.chen on 2016/9/4.
 */
public interface IMyPalyer {
    void init(CustomPlayer.PlayerListener listener);
    void start();
    void prepare();
    void prepareAsync();
    void stop();
    void pause();
    void finish();
}
