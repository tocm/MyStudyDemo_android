package sample.study.andy.andydemos.function.ioc.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sample.study.andy.andydemos.R;

@AndyContentViewBinder(R.layout.activity_test_ioc)//使用自定义注解Type
public class TestIOCActivity extends AppCompatActivity {
    @AndyViewIdBinder(R.id.test_ioc_tv)//使用自定义注解Filed
    private TextView textView;
    @AndyViewIdBinder(R.id.test_ioc_button)
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsInject.initInjectActivityBinder(this);
        Log.d("test","onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test","onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test","onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test","onStart");
    }

    /**
     *
     * 方法 andyOnClick 名字可以任意起
     * 只要有加入注解，在注解的逻辑代码里面会通过反射扫描本类中所有带有注解的方法名称，对应调用
     * @param v
     */
    @AndyViewClickBinder(R.id.test_ioc_button)//使用自定义注解 Method
    private void andyOnClick(View v) {
        switch (v.getId()) {
            case R.id.test_ioc_button:
                textView.setText("This is my test IOC sample clicked Button");
                break;
            case R.id.test_ioc_tv:
                textView.setText("This is my test IOC sample clicked TEXTVIEW");
                break;
            default:
                break;
        }
    }
}
