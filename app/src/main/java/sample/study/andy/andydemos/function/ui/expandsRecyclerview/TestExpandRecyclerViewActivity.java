package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import sample.study.andy.andydemos.R;

public class TestExpandRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expand_recycler_view);
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

        recyclerView = (RecyclerView) this.findViewById(R.id.test_expand_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GroupAdapter groupAdapter = new GroupAdapter(this,testData());
        recyclerView.setAdapter(groupAdapter);


        //长按允许拖动ITEM 交换位置
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchListenerHelper<Group>(testData())) ;
//        itemTouchHelper.attachToRecyclerView(recyclerView);



    }

    private List<Group> testData() {
        List<Group> groupList = new ArrayList<Group>();
        List<GroupItem> groupItemList = new ArrayList<GroupItem>();

        for (int j=0;j<10;j++) {
            GroupItem groupItem = new GroupItem("child_"+j,"Child's content order numb"+j);
            groupItemList.add(groupItem);
        }

        for (int i= 0;i<3;i++){
            Group group = new Group("GTitle_"+i,groupItemList);
            groupList.add(group);
        }

        return groupList;

    }

}
