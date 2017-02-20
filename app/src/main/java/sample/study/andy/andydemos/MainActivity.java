package sample.study.andy.andydemos;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import sample.study.andy.andydemos.activity.LoginActivity;
import sample.study.andy.andydemos.activity.MasterDetailActivityDetailActivity;
import sample.study.andy.andydemos.activity.NavigationActivity;
import sample.study.andy.andydemos.activity.RefreshAndLoadmoreActivity;
import sample.study.andy.andydemos.activity.SettingsActivity;
import sample.study.andy.andydemos.activity.TabbedActivity;
import sample.study.andy.andydemos.activity.ToolbarScrollingActivity;
import sample.study.andy.andydemos.ajni.HelloJni;
import sample.study.andy.andydemos.function.accessibility.SimulationClickActivity;
import sample.study.andy.andydemos.function.databinding.activity.DataBindingRecyclerActivity;
import sample.study.andy.andydemos.function.databinding.activity.LoginDatabindingActivity;
import sample.study.andy.andydemos.function.eventbus.EventbusActivity_1;
import sample.study.andy.andydemos.function.handler.TestHandlerThreadActivity;
import sample.study.andy.andydemos.function.ioc.android.TestIOCActivity;
import sample.study.andy.andydemos.function.ipc.IPCMessenger;
import sample.study.andy.andydemos.function.ipc.MessengerClient;
import sample.study.andy.andydemos.function.key.KeyMapping;
import sample.study.andy.andydemos.function.media.activity.TestMediaMainActivity;
import sample.study.andy.andydemos.function.rxjava.RxUtils;
import sample.study.andy.andydemos.function.ui.expandsRecyclerview.TestExpandRecyclerViewActivity;
import sample.study.andy.andydemos.function.ui.tablayout.TestTabFragment;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    IPCMessenger ipcMessenger;

    private static String testStatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate testStatic = "+testStatic);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "hello JNI is "+ HelloJni.getHelloJni().getStringFromJNI(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ipcMessenger = new MessengerClient(this.getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermission();


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
                if(str.equals(Manifest.permission.INTERNET)) {
                    //// TODO: 2016/8/14  sth
                    return;
                }
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void goScrollActivity(View view) {
        Intent intent =new Intent(this, ToolbarScrollingActivity.class);
        startActivity(intent);
    }
    public void goSettingsActivity(View view) {
        Intent intent =new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goTabbedActivity(View view) {
        Intent intent =new Intent(this, TabbedActivity.class);
        startActivity(intent);
    }

    public void goNavigatonActivity(View view) {
        Intent intent =new Intent(this, NavigationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void goMasterDetailActivity(View view) {
        Intent intent =new Intent(this, MasterDetailActivityDetailActivity.class);
        startActivity(intent);
    }
    public void goLoginActivity(View view) {
        Intent intent =new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void testIPCMessenger(View view) {
        if (ipcMessenger.isConnectService()) {
            ipcMessenger.sendMessage("ANDY'S TEST IPC MESSENGER");
        }

    }
    public void testRefreshAndLoadMore(View view) {
        Intent intent =new Intent(this, RefreshAndLoadmoreActivity.class);
        startActivity(intent);
    }

    public void testHandlerThread(View view) {
        Intent intent =new Intent(this, TestHandlerThreadActivity.class);
        startActivity(intent);
    }

    private int mNum;
    private String mPath;
    public void testRxJava(View view) {
        RxUtils.createRxObserver();
        RxUtils.createRxObserverJust();
        RxUtils.testRxFlatMap();;

        if (mNum%2 == 0) {
            mPath ="http://www.uimaker.com/uploads/allimg/130221/1_130221101310_1.jpg";
        } else {
            mPath = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1471160494&di=fecc9cb077ddac6ca788e20a134d0d3f&src=http://www.uimaker.com/uploads/allimg/120416/1_120416091247_1.jpg";
        }
        mNum ++;
        RxUtils.testRxOkHttpDownload(mPath,(ImageView)this.findViewById(R.id.rx_img_test));
    }
    public void testDataBindingRecylerView(View view) {
        Intent intent = new Intent(this, DataBindingRecyclerActivity.class);
        this.startActivity(intent);
    }
    public void testDataBindingEditText(View view) {
        Intent intent = new Intent(this, LoginDatabindingActivity.class);
        this.startActivity(intent);
    }
    public void testMedia(View view) {
        Intent intent = new Intent(this, TestMediaMainActivity.class);
        this.startActivity(intent);
    }
    public void testEventBus(View view) {
        Intent intent = new Intent(this, EventbusActivity_1.class);
        this.startActivity(intent);
    }

    public void testIOC(View view) {
        Intent intent = new Intent(this, TestIOCActivity.class);
        this.startActivity(intent);
    }

    public void testExpandsRecyclerView(View view) {
        Intent intent = new Intent(this, TestExpandRecyclerViewActivity.class);
        this.startActivity(intent);
    }
    public void testTablaViewpage(View view) {

        TestTabFragment testTabFragment = TestTabFragment.newInstance(null,null);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.tablayout_fragment_main,testTabFragment);
        transaction.addToBackStack(TestTabFragment.class.getSimpleName());
        transaction.commit();
    }

    public void testAccessibility(View view) {

        Intent intent = new Intent(this, SimulationClickActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onResume(){
        testStatic = "abcd";
        Log.d(TAG,"onResume testStatic =  "+testStatic);
        super.onResume();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.d(TAG,"===> onkeyDown ACUTE keyCode = "+ keyCode +",getDeadKey = "+ KeyEvent.getDeadChar(KeyMapping.ACUTE,keyCode));
        Log.d(TAG,"===> onkeyDown GRAVE keyCode = "+ keyCode +",getDeadKey = "+ KeyEvent.getDeadChar(KeyMapping.GRAVE,keyCode));
        Log.d(TAG,"===> onkeyDown CIRCUMFLEX keyCode = "+ keyCode +",getDeadKey = "+ KeyEvent.getDeadChar(KeyMapping.CIRCUMFLEX,keyCode));
        Log.d(TAG,"===> onkeyDown TILDE keyCode = "+ keyCode +",getDeadKey = "+ KeyEvent.getDeadChar(KeyMapping.TILDE,keyCode));
        Log.d(TAG,"===> onkeyDown UMLAUT keyCode = "+ keyCode +",getDeadKey = "+ KeyEvent.getDeadChar(KeyMapping.UMLAUT,keyCode));
        Log.d(TAG,"onkeyDown  keyCode = "+ keyCode +", getCharacters = "+event.getCharacters() +",label = "+event.getDisplayLabel()+", number = "+(byte)event.getNumber()+",unicodeChar = "+event.getUnicodeChar());
        Log.d(TAG,"onkeyDown  keyCode = "+ keyCode +",label = "+String.valueOf(event.getDisplayLabel()).getBytes()[0]);
        return super.onKeyDown(keyCode, event);


    }
}
