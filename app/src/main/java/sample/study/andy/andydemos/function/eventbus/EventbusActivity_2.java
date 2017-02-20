package sample.study.andy.andydemos.function.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import sample.study.andy.andydemos.R;

public class EventbusActivity_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_2);
    }
    public void sendEvent(View view) {
        EventBus.getDefault().post(new EventBusEventTypes("andy first test"));
    }
}
