package com.example.commonlib.base.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.base.mvp.BaseModel;
import com.example.commonlib.base.mvp.BasePresenter;
import com.example.commonlib.base.mvp.BaseView;
import com.example.commonlib.utils.GenericUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListActivity<P extends BasePresenter, M extends BaseModel, T> extends BaseActivity implements BaseView {

    private P mPresenter;
    public static final int ACTION_INIT_LOAD = 1;
    public static final int ACTION_PULL_REFRESH = 2;
    public static final int ACTION_LOAD_MORE = 3;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    protected ArrayList<T> mDataList = new ArrayList<>();
    private ListAdapter mListAdapter;
    protected int mPager = 1;

    @Override
    protected void setupMVP() {
        mPresenter = GenericUtil.getType(this, 0);
        M model = GenericUtil.getType(this, 1);
        if (mPresenter != null && model != null) {
            mPresenter.setMV(model, this);
        }
    }

    @Override
    protected void bindView() {
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void setUpView() {
        mSwipeRefreshLayout.setEnabled(setPullRefreshEnable());
        if (setPullRefreshEnable()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadData(ACTION_PULL_REFRESH);
                }
            });
        }
        mListAdapter = new ListAdapter(setItemLayoutId(), mDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListAdapter);
    }


    /**
     * @param action 加载类型
     */
    protected abstract void loadData(int action);

    @Override
    protected void loadData() {
        loadData(ACTION_INIT_LOAD);
    }

    /**
     * @return 是否支持下拉刷新
     */
    protected boolean setPullRefreshEnable() {
        return true;
    }


    /**
     * @return 是否支持加载更多
     */
    protected boolean setLoadMoreEnable() {
        return true;
    }

    /**
     * @return 条目布局id
     */
    public abstract int setItemLayoutId();

    class ListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {


        ListAdapter(int layoutResId, List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            convertView(helper, item);
        }
    }

    /**
     * @param helper
     * @param item
     */
    protected abstract void convertView(BaseViewHolder helper, T item);

    protected void loadSuccess(ArrayList<T> lisData){
        mDataList.addAll(0, lisData);
        mListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
