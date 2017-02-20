package sample.study.andy.andydemos.function.media.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

import sample.study.andy.andydemos.R;
import sample.study.andy.andydemos.function.media.CustomPlayer;
import sample.study.andy.andydemos.function.media.CustomRecorder;
import sample.study.andy.andydemos.function.media.IMyPalyer;

public class TestMediaMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = TestMediaMainActivity.class.getSimpleName();
    private File audioRecordFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_media_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkPermission();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission() {
        int checkPermission =  checkSelfPermission(Manifest.permission.INTERNET);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO},1);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            for (String str : permissions) {
                if(str.equals(Manifest.permission.RECORD_AUDIO)) {
                    //// TODO: 2016/8/14  sth
                    return;
                }
            }
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_media_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 通过意图到启动音频
     * 本地资源音频
     * @param view
     */
    public void testAudioIntent(View view) {
        String audioPath = Environment.getExternalStorageDirectory().getPath()+"/tmp/hangouts_incoming_call.ogg";
        Log.d(TAG,"testAudioIntent  audioPath = "+audioPath);
        File audioFile = new File(audioPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(audioFile),"audio/ogg/mp3");
        startActivity(intent);
    }


    /**
     * 通过意图启动本地视频
     * @param view
     */
    public void testVideoIntent(View view) {
        String audioPath = Environment.getExternalStorageDirectory().getPath()+"/tmp/videoviewdemo.mp4";
        Log.d(TAG,"testVideoIntent  videoPath = "+audioPath);
        File audioFile = new File(audioPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(audioFile),"video/*");
        startActivity(intent);
    }

    boolean isPlay = false;
    private IMyPalyer iMyPalyer;
    public void testSampleMediaPlayer(View view) {
        if (iMyPalyer == null) {
            iMyPalyer = new CustomPlayer.Builder().setMediaResources(R.raw.test_cbr).create(this);
        }
        isPlay = !isPlay;
        if(isPlay) {
            iMyPalyer.init(null);
            iMyPalyer.prepare();
            iMyPalyer.start();

        } else {
            iMyPalyer.stop();

        }
    }

    public void testNetworkMediaPlayer(View view) {
        if (iMyPalyer == null) {
            iMyPalyer = new CustomPlayer.Builder().setNetworkDataSources("http://www.mobvcasting.com/android/audio/goodmorningandroid.mp3").create(this);
        }
        isPlay = !isPlay;
        if(isPlay) {
            iMyPalyer.init(new CustomPlayer.PlayerListener() {
                @Override
                public void prepared() {
                    Log.d(TAG,"prepared ");
                    iMyPalyer.start();
                }
            });
            iMyPalyer.prepareAsync();

        } else {
            iMyPalyer.stop();

        }
    }


    public void testRecording(View view) {
        iMyPalyer = new CustomRecorder();
        isPlay = !isPlay;
        if(isPlay) {
            iMyPalyer.init(null);
            iMyPalyer.prepare();
            audioRecordFilePath = ((CustomRecorder)iMyPalyer).getAudioFile();

        } else {
            iMyPalyer.stop();
            iMyPalyer.finish();

        }
    }

    public void testRecorderPlay(View view) {
        Log.d(TAG,"testRecorderPlay");
        iMyPalyer = new CustomPlayer.Builder().setMediaUri(Uri.fromFile(audioRecordFilePath)).create(this);

        isPlay = !isPlay;
        if(isPlay) {

            iMyPalyer.init(null);
            iMyPalyer.prepare();

        } else {
            iMyPalyer.stop();
            iMyPalyer.finish();

        }
    }
}
