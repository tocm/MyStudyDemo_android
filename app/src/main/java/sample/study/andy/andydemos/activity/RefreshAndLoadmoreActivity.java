package sample.study.andy.andydemos.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import sample.study.andy.andydemos.R;
import sample.study.andy.andydemos.adapter.MyRecyclerViewAdapter;

public class RefreshAndLoadmoreActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;
    private List<String> list = new ArrayList<String>();
    private boolean mIsLoading;
    private MyHandler mMyHandler;
    private final static int REFRESH_DATA = 1;
    private final static int DATA_ITEM_MAX = 100;
    private final static int DATA_ITEM_DISPLAY_LIMITED = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_loadmore);
        mMyHandler = new MyHandler(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("test","onRefresh");
                list.clear();
                mMyHandler.sendEmptyMessage(REFRESH_DATA);
            }
        });
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("test","post onRefresh");
//                mSwipeRefreshLayout.setRefreshing(true);
//            }
//        });

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);

        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(this,list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMyRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                Log.d("test", "lastVisibleItemPosition = "+lastVisibleItemPosition);
                if (lastVisibleItemPosition + 1 == mMyRecyclerViewAdapter.getItemCount()) {
                    Log.d("test", "loading executed");
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mMyRecyclerViewAdapter.notifyItemRemoved(mMyRecyclerViewAdapter.getItemCount());
                        return;
                    }
                    if (!mIsLoading) {
                        mIsLoading = true;
                        mMyHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setData(mMyRecyclerViewAdapter.getItemCount());
                                Log.d("test", "load more completed");
                                mIsLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        mMyHandler.sendEmptyMessage(REFRESH_DATA);

    }

    private void setData(int fromIndex) {
        Log.d("test","setData DATA fromIndex = "+fromIndex);
        if (fromIndex < DATA_ITEM_DISPLAY_LIMITED ) {
            for (int i = fromIndex+1; i < fromIndex + DATA_ITEM_DISPLAY_LIMITED; i++) {
                list.add("TEST TITLE < limited " + i);
            }
        } else {
            for (int i = fromIndex; i < fromIndex -1 + DATA_ITEM_DISPLAY_LIMITED; i++) {
                list.add("TEST TITLE > limited " + i);
            }
        }
        mMyRecyclerViewAdapter.notifyDataSetChanged();
        mMyRecyclerViewAdapter.notifyItemRemoved(mMyRecyclerViewAdapter.getItemCount());
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d("test","setData DATA");
    }

    private static class MyHandler extends Handler {
        private WeakReference<RefreshAndLoadmoreActivity > weakReference;

        public MyHandler(RefreshAndLoadmoreActivity activity) {
            weakReference = new WeakReference<RefreshAndLoadmoreActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RefreshAndLoadmoreActivity activity = weakReference.get();
            switch (msg.what) {
                case REFRESH_DATA:
                    Log.d("test","handleMessage REFRESH DATA");

                    activity.setData(activity.mMyRecyclerViewAdapter.getItemCount());

                    break;
            }
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    }

}
