package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sample.study.andy.andydemos.R;

import static sample.study.andy.andydemos.function.rxjava.RxUtils.TAG;

/**
 * Created by Andy.chen on 2016/11/30.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<Group> list;
    private Context mContext;
    private boolean isShowGroupItem = false;

    public GroupAdapter(Context context, List<Group> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandrecyclerview_group_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(list.get(position).getGroupName());
        holder.mCount.setText(list.get(position).getGroupItems().size() + "");

        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        //显示底部位置
        mLayoutManager.setStackFromEnd(true);
        holder.mGroup.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        holder.mGroup.setHasFixedSize(true);
        final List<GroupItem> listChildItems = list.get(position).getGroupItems();
        GroupItemAdapter groupItemAdapter = new GroupItemAdapter(mContext,listChildItems);
        //列表添加分割线
//        holder.mGroup.addItemDecoration(new DividerItemDecoration(
//                mContext, DividerItemDecoration.HORIZONTAL_LIST));
        holder.mGroup.setAdapter(groupItemAdapter);


        //初始化列表形式
        holder.mGroup.setVisibility(View.GONE);
        holder.mImg.setImageResource(R.mipmap.button_normal);
        isShowGroupItem = false;

        groupItemAdapter.addOnItemClickListener(new GroupItemAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Toast.makeText(mContext, "addOnItemClickListener Item " + position + " Click！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDeleteClick(View view, List data, int position) {
                Toast.makeText(mContext, "onItemDeleteClick Item " + position + " Click！", Toast.LENGTH_SHORT).show();
                //滑动移除
                listChildItems.remove(position);
                holder.mGroup.getAdapter().notifyItemRemoved(position);
                Log.d(TAG,"items size = "+listChildItems.size());
                //刷新RecyclerView UI
                holder.mGroup.requestLayout();//必须重新请求布局，否则会留出空白区域
                holder.mGroup.invalidate();
            }
        });



        //给分组添加Click事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
       // holder.mGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowGroupItem) {
                    holder.mGroup.setVisibility(View.VISIBLE);
                    holder.mImg.setImageResource(R.mipmap.button_normal);
                    isShowGroupItem = true;
                } else {
                    holder.mGroup.setVisibility(View.GONE);
                    holder.mImg.setImageResource(R.mipmap.btn_grayed);
                    isShowGroupItem = false;
                }
            }
        });

        //给子列表添加touch event
//        ItemTouchListenerHelper itemItemTouchListenerHelper = new ItemTouchListenerHelper();
//        itemItemTouchListenerHelper.setItemEventListener(new ItemTouchListenerHelper.ItemEventListener() {
//            @Override
//            public void swiped(int position, int direction) {
//                //滑动移除
//                listChildItems.remove(position);
//
//                holder.mGroup.getAdapter().notifyItemRemoved(position);
//                Log.d(TAG,"items size = "+listChildItems.size());
//                //刷新RecyclerView UI
//                holder.mGroup.requestLayout();//必须重新请求布局，否则会留出空白区域
//                holder.mGroup.invalidate();
//            }
//
//            @Override
//            public void drag(int fromPosition, int toPosition) {
//                //拖动交换位置
//                Collections.swap(listChildItems, fromPosition,toPosition);
//                //通过Collections 来交换数据，最后通过的recyclerView.getAdapter().notifyItemMoved 来刷新页面。
//                holder.mGroup.getAdapter().notifyItemMoved(fromPosition,toPosition);
//            }
//        });
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemItemTouchListenerHelper) ;
//        //通过ItemTouchHelper的attachToRecyclerView方法与RecyclerView绑定在一起
//        itemTouchHelper.attachToRecyclerView(holder.mGroup);
//        itemTouchHelper.startDrag(holder);
//        itemTouchHelper.startSwipe(holder);
    }

    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public TextView mCount;
        public RecyclerView mGroup;
        public RelativeLayout mGroupLayout;
        public ImageView mImg;

        public ViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.name);
            mCount = (TextView) view.findViewById(R.id.count);
            mGroup = (RecyclerView) view.findViewById(R.id.rl_group);
            mGroupLayout = (RelativeLayout) view.findViewById(R.id.group_layout);
            mImg = (ImageView) view.findViewById(R.id.image);
        }
    }
}
