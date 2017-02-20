package sample.study.andy.andydemos.function.accessibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sample.study.andy.andydemos.R;

public class SimulationClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);


    }

    public void motionEventSimulateClicked(View view) {
        Intent intent = new Intent(this,MotionEventSimulateActivity.class);
        this.startActivity(intent);
    }

    public void accessibilitySimulateEventClicked(View view) {
        Intent intent = new Intent(this,AccessibilitySimulateActivity.class);
        this.startActivity(intent);
    }
    public void reflectSimulateEventClicked(View view) {

    }
}
