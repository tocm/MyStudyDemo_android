package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import sample.study.andy.andydemos.R;

import static sample.study.andy.andydemos.function.rxjava.RxUtils.TAG;

/**
 * Created by Andy.chen on 2016/12/2.
 */

public class SlidingItemView extends HorizontalScrollView {
    private TextView mTextView_Delete;
    private int mScrollWidth;
    private ISlidingItemListener mISlidingItemListener;
    private Boolean isOpen = false;
    private Boolean once = false;
    public SlidingItemView(Context context) {
        super(context);
    }

    public SlidingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlidingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure begin once = "+once);
        if(!once){
            mTextView_Delete = (TextView) findViewById(R.id.tv_delete);
            Log.d(TAG,"onMeasure delte button width = "+mTextView_Delete.getWidth());
            once = true;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG,"onLayout begin changed = "+changed);
        if(changed){
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();

            Log.d(TAG,"onLayout mScrollWidth = "+mScrollWidth);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mISlidingItemListener.onDownOrMove(this);
                Log.d(TAG,"onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"onTouchEvent ACTION_UP");
                changeScrollX();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollX(){
        int scrollValues = mScrollWidth / 2;
        int scrollX = (int)getScrollX();
        Log.d(TAG,"changeScrollX scaleX = "+scrollX +",scrollValues = "+ scrollValues);
        if(scrollX >= scrollValues){
            Log.d(TAG,"changeScrollX > width smoothScrollTo mScrollWidth = "+mScrollWidth);
            this.smoothScrollTo(mScrollWidth, 0);
            mTextView_Delete.scrollTo(mScrollWidth,0);
            isOpen = true;
            mISlidingItemListener.onMenuIsOpen(this);
        }else{
            Log.d(TAG,"changeScrollX < width close smoothScrollTo");
            closeMenu();
        }
    }

    public void closeMenu() {
        if (isOpen) {
            Log.d(TAG,"closeMenu  ");
            this.smoothScrollBy(0,0);
            mTextView_Delete.scrollTo(0,0);
            isOpen = false;

        }
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG,"onScollChanged l- mScrollWidth = "+(l-mScrollWidth));
        mTextView_Delete.setTranslationX(l - mScrollWidth);

    }
    public void setSlidingButtonListener(ISlidingItemListener listener){
        mISlidingItemListener = listener;
        this.setHorizontalScrollBarEnabled(false);
    }

    public interface ISlidingItemListener {
        void onMenuIsOpen(View view);
        void onDownOrMove(SlidingItemView slidingItemView);
    }
}
