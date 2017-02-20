package sample.study.andy.andydemos.function.databinding.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Andy.chen on 2016/8/29.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }

    private ViewDataBinding binding;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

}
