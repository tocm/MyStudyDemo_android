package sample.study.andy.andydemos.function.accessibility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import sample.study.andy.andydemos.R;

/**
 * 使用 MotionEvent.obtain 模拟实现自动点击
 */
public class MotionEventSimulateActivity extends AppCompatActivity {
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event_accessibility);
        dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("test MotionEvent simulateClicked ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        listenerDrawViewProcess();
    }

    private void listenerDrawViewProcess() {
        final Button button = (Button) this.findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        addOnGlobalLayoutListener(button, new Runnable() {
            @Override
            public void run() {
                simulateClicked(button, button.getWidth() / 2 - button.getX(), button.getHeight() / 2 - button.getY());
            }
        });
    }

    public static void addOnGlobalLayoutListener(final View view, final Runnable runnable) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                runnable.run();
            }
        });
    }

    private void simulateClicked(View view, float x, float y) {
        Log.d("TEST", "simulateClicked");
        long downTime = SystemClock.uptimeMillis();
        MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;

        MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);

        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
