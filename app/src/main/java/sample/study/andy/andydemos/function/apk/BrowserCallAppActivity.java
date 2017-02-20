package sample.study.andy.andydemos.function.apk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import sample.study.andy.andydemos.MainActivity;

public class BrowserCallAppActivity extends MainActivity {
private final static String TAG = BrowserCallAppActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate " );
//        setContentView(R.layout.activity_browser_call_app);
        String action = getIntent().getAction();
        String irtsplink = null;
        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = getIntent().getData();
            if(uri != null){
                irtsplink = uri.getQueryParameter("irtsplink");
            }
        }
        Log.d(TAG,"get browser data = " + irtsplink);
    }
}
