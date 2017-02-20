package sample.study.andy.andydemos.function.databinding.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sample.study.andy.andydemos.R;
import sample.study.andy.andydemos.function.databinding.adapter.RecyclerviewAdapter;

public class DataBindingRecyclerActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding_recycler);
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView_test);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        for(int i=0;i<10; i++) {
            list.add("Test title + "+ i);
        }
        recyclerView.setAdapter(new RecyclerviewAdapter(list));
    }
}
