package sample.study.andy.andydemos.function.media;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Andy.chen on 2016/9/5.
 */
public class CustomRecorder implements IMyPalyer{

    private MediaRecorder mediaRecorder;

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

    private File audioFile = null;
    public CustomRecorder() {
        mediaRecorder = new MediaRecorder();
    }

    @Override
    public void init(CustomPlayer.PlayerListener listener) {

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//录音源格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//输出的格式 3GP，另外还有MP4,ARM
        /**
         * 从音质来说，AAC 比 AMR_NB 好。   AAC属于有损压缩的格格式   AAC格式的声音文件比同一个文件MP3格式的要小的多  ，但声音质量不会降低
         AMR_NB编码声音的无视频纯声音3gp文件就是amr,他的文件比AAC的小，他的音乐效果没ACC的好,
         采用的音频压缩格式不同，AAC比AMR音质好，AMR适合移动网络等速度较低的环境播放，如果在自己电脑或手机上用的话，建设用AAC格式的
         */
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置编解码器 ， 自适应多速率窄带编解码器，默认采样率8KHZ,码率在4.75~12.2 kbps

        File outPath = new File(Environment.getExternalStorageDirectory().getPath()+"/tmp/record/");
        if (!outPath.exists()) {
            outPath.mkdirs();
        }

        try {
            audioFile = File.createTempFile("testRecord",".3gp",outPath);
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {

        if (mediaRecorder != null) {
            mediaRecorder.start();
        }
    }

    @Override
    public void prepare() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void prepareAsync() {
    }

    @Override
    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void finish() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
