package sample.study.andy.andydemos.function.accessibility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import sample.study.andy.andydemos.R;

/**
 * AccessibilityService 测试类
 * 完成自动点击按钮，监听通短信
 */
public class AccessibilitySimulateActivity extends AppCompatActivity {
    static final String TAG = AccessibilitySimulateActivity.class.getSimpleName();
    Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_simulate);
        mDialog = new AlertDialog.Builder(this).create();
        mDialog.setTitle("Accessibility simulate Auto-clicked ");
        this.findViewById(R.id.btn_accessibility_simulate_clicked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"");
                mDialog.show();
            }
        });

        if (AccessibilityClickedService.checkEnabledService(this,getPackageName()+"/.function.accessibility.AccessibilityClickedService")) {

            //// TODO: 2017/2/16
        } else {
            //open settings Accessibility UI
            openAccessibilityServiceSettings();

        }
    }

    /** 打开辅助服务的设置*/
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "开启 Accessibility 服务", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
