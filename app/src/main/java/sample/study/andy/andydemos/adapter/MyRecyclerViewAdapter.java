package sample.study.andy.andydemos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sample.study.andy.andydemos.R;

/**
 * Created by Andy.chen on 2016/8/4.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private LayoutInflater mInflater;
    private Context context;
    private List<String> mData;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public MyRecyclerViewAdapter(Context context,List<String> data) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("test","onCreateViewHolder ");
        if (viewType == TYPE_ITEM) {
            View view =mInflater.inflate(R.layout.item_base_refresh_and_loadmore, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hd, int position) {
        Log.d("test","onBindViewHolder ");

        final RecyclerView.ViewHolder holder = hd;
        if (holder instanceof ItemViewHolder) {
            if (mData != null && mData.size()>0) {
                String str = mData.get(position);
                Log.d("test","((ItemViewHolder) holder).tvTitle = "+((ItemViewHolder) holder).tvTitle);
//                ((TextView) (holder.itemView.findViewById(R.id.item_base_title))).setText(str);
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.tvTitle.setText(str);

            }
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }
    @Override
    public int getItemCount() {
        return mData == null ? 0 : (mData.size() == 0 ? 0 : mData.size() + 1);
    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public ItemViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.item_base_title);
            Log.d("test","ItemViewHolder  tvTitle is null " +(tvTitle == null) );
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFoot;
        public FootViewHolder(View view) {
            super(view);
            tvFoot = (TextView) view.findViewById(R.id.item_foot_text);
            Log.d("test","FootViewHolder  tvFoot is null " +(tvFoot == null) );
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
