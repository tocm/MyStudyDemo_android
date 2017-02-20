package sample.study.andy.andydemos.function.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Andy.chen on 2016/9/4.
 */
public class CustomPlayer implements IMyPalyer {
    private Context mContext;
    MediaPlayer mMediaPlayer;
    private int mMediaResources;
    private Uri mMediaPathUri;
    private int mPosition;
    private String mNetworkDataSources;
    private CustomPlayer(Context context, Builder builder) {
        this.mContext = context;
        mMediaResources = builder.audioResources;
        mMediaPathUri = builder.mediaUri;
        mNetworkDataSources = builder.networkDataSources;

    }

    public interface PlayerListener{
        public void prepared();
    }

    public static class Builder{
        private int audioResources;
        private Uri mediaUri;
        private String networkDataSources;
        public Builder setMediaResources(int mediaResources) {
            this.audioResources = mediaResources;
            return this;
        }
        public Builder setMediaUri(Uri mediaUri) {
            this.mediaUri = mediaUri;
            return this;
        }

        public Builder setNetworkDataSources(String dataSources) {
            this.networkDataSources = dataSources;
            return this;
        }

        public CustomPlayer create(Context context) {
            return new CustomPlayer(context,this);
        }
    }
    @Override
    public void init(final PlayerListener listener) {

        if (mMediaPathUri != null) {
            mMediaPlayer = MediaPlayer.create(mContext,mMediaPathUri);
        } else if (mNetworkDataSources != null) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(mNetworkDataSources);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mMediaPlayer = MediaPlayer.create(mContext,mMediaResources);
        }
        if (listener != null) {
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if(listener != null) {
                        listener.prepared();
                    }
                }
            });
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPosition = mp.getCurrentPosition();
            }
        });
    }

    @Override
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            mMediaPlayer.seekTo(mPosition);
        }
    }

    @Override
    public void prepare() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void prepareAsync() {
        if (mMediaPlayer != null) {
            mMediaPlayer.prepareAsync();
        }
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void finish() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }
}
