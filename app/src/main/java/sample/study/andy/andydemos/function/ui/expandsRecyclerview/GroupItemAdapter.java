package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sample.study.andy.andydemos.R;

import static sample.study.andy.andydemos.function.rxjava.RxUtils.TAG;

/**
 * Created by Andy.chen on 2016/11/30.
 */

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.ViewHolder>  implements SlidingItemView.ISlidingItemListener {

    private List<GroupItem> list;
    private OnRecyclerItemClickListener mOnItemClickListener;
    private SlidingItemView mSlidingItemView;
    private Context mContext;
    public GroupItemAdapter(Context context,List<GroupItem> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_sliding_customize, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandrecyclerview_child, parent, false);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                {
                    //使用getTag方法获取数据
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(view, list, (Integer) view.getTag());
                }
            }
        });
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(list.get(position).getTitle());
        holder.mContent.setText(list.get(position).getContent());
        holder.mContent.setTag(position);
        holder.itemView.setTag(position);
        holder.btn_Delete.setTag(position);

        bindItemViewSliding(holder);

    }

    private void bindItemViewSliding(final ViewHolder holder) {
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = this.getScreenWith(mContext);

        holder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, list, n);
                    }
                }

            }
        });
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = holder.getLayoutPosition();
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemDeleteClick(view, list, n);
            }
        });
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        Log.d(TAG,"closeMenu");
        mSlidingItemView.closeMenu();
        mSlidingItemView = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mSlidingItemView != null){
            Log.d(TAG,"menuIsOpen return true");
            return true;
        }
        Log.d(TAG,"menuIsOpen return false");
        return false;
    }


    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }


    /**
     * item点击事件
     *
     * @param listener
     */
    public void addOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public TextView mTitle;
        public TextView mContent;
        public ViewGroup layout_content;


        public ViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContent = (TextView) view.findViewById(R.id.content);
            btn_Delete = (TextView) view.findViewById(R.id.tv_delete);
            layout_content = (ViewGroup)view.findViewById(R.id.layout_content);
            ((SlidingItemView)view).setSlidingButtonListener(GroupItemAdapter.this);
        }
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(View view, List data, int position);
        void onItemDeleteClick(View view, List data, int position);
    }


    public static int getScreenWith(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.d(TAG," getScreenWith = "+displayMetrics.widthPixels);
        return displayMetrics.widthPixels;
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        Log.d(TAG,"onMenuIsOpen ");
        mSlidingItemView = (SlidingItemView) view;
    }
    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingItemView slidingButtonView) {
        Log.d(TAG,"onDownOrMove begin");
        if(menuIsOpen()){
            if(mSlidingItemView != null){
                Log.d(TAG,"onDownOrMove close");
                closeMenu();
            }
        }
    }
}
