package test.mobileapp.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import test.mobileapp.model.LoadMoreModel;

public abstract class LoadMoreHelper<T extends RecyclerView.ViewHolder , M extends LoadMoreModel> extends RecyclerView.Adapter<T>
{
    List<M> adapterModel;
    private int pageSize = 20;
    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private boolean isEnable = true;

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setMoreLoadListener(RecyclerView recyclerView, final OnLoadMoreListener onLoadMoreListener) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                .getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if(!isEnable)
                            return;
                        totalItemCount = linearLayoutManager.getItemCount();
                        if(totalItemCount == 0)
                            return;
                        lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            loading = true;
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemViewType(int position) {
        if(pageSize > position)
            return 1;
        if(adapterModel.get(position).isMoreLoad())
            return 0;
        return 1;
    }

    public void addData(List<M> models) {
        if(adapterModel == null)
            adapterModel = new ArrayList<>();
        for(int i=adapterModel.size()-1 ; i >= 0; i--)
            if(adapterModel.get(i).isMoreLoad())
                adapterModel.remove(i);
        adapterModel.addAll(models);
        if(models.size() >= pageSize)
        {
            LoadMoreModel model = new LoadMoreModel();
            model.setMoreLoad(true);
            adapterModel.add((M)model);
        }
        else
        {
            setEnable(false);
        }
        notifyDataSetChanged();
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
