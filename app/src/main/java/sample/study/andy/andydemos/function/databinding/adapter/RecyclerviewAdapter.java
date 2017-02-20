package sample.study.andy.andydemos.function.databinding.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import sample.study.andy.andydemos.BR;
import sample.study.andy.andydemos.R;
import sample.study.andy.andydemos.function.databinding.model.ItemInfoModel;

/**
 * Created by Andy.chen on 2016/8/29.
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> listData;
    public RecyclerviewAdapter(List<String> list) {
        this.listData = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_databing_recyclerview,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(viewDataBinding.getRoot());
        recyclerViewHolder.setBinding(viewDataBinding);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecyclerViewHolder) {
            String strTitle = listData.get(position);
            ItemInfoModel itemInfoModele = new ItemInfoModel();
            itemInfoModele.setTitle(strTitle);
            ((RecyclerViewHolder) holder).getBinding().setVariable(BR.bindingInfo,itemInfoModele);
            ((RecyclerViewHolder) holder).getBinding().executePendingBindings();

        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
