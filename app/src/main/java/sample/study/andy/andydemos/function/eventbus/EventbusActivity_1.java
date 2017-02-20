package sample.study.andy.andydemos.function.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import sample.study.andy.andydemos.R;

public class EventbusActivity_1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_1);

        EventBus.getDefault().register(this);
    }

    public void gotoActivity2(View view) {
        Log.d("test","gotoactivity2");
        Intent intent = new Intent(this,EventbusActivity_2.class);
        this.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 从发布者那里得到eventbus传送过来的数据
     * 加上@Subscribe以防报错：its super classes have no public methods with the @Subscribe annotation

     * @param event
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventMainThread(EventBusEventTypes event) {
        event.showStr();
        Toast.makeText(this,"Andy test eventbus",Toast.LENGTH_SHORT).show();
    }

}

