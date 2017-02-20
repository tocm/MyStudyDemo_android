package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import static sample.study.andy.andydemos.function.rxjava.RxUtils.TAG;

/**
 * Created by Andy.chen on 2016/12/2.
 */

/**
 * Item 滑动，拖动 事件监听
 */
public class ItemTouchListenerHelper extends ItemTouchHelper.Callback {
    private int dragFlags;
    private int swipeFlags;

    public ItemTouchListenerHelper() {
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        dragFlags = 0;
        swipeFlags = 0;
        //拖动获取事件
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager || recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        //左右滑动获取事件
        swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        Log.d(TAG, "onMove fromPosition = " + fromPosition + ", toPosition = " + toPosition);
        if (mItemEventListener != null) {
            mItemEventListener.drag(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
//        mRecyclerView.getAdapter().notifyItemRemoved(position);
        if (mItemEventListener != null) {
            mItemEventListener.swiped(position, direction);
        }
    }

    /**
     * 我们可以在这个方法中自定义用户长按后出现的效果，我这里就只设置了放大。
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setScaleX(1.05f);
            viewHolder.itemView.setScaleY(1.05f);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 当事件结束后，我们可以在这里做一些处理，比如上面onSelectedChanged缩放后效果的还原View。
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    private ItemEventListener mItemEventListener;

    public void setItemEventListener(ItemEventListener listener) {
        mItemEventListener = listener;
    }

    public interface ItemEventListener {
        public void swiped(int position, int direction);
        public void drag(int fromPosition, int toPosition);
    }
}
