package com.example.shaw.myapplication;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.activity.BaseListActivity;
import com.example.commonlib.base.mvp.BaseModel;
import com.example.commonlib.base.mvp.BasePresenter;

import java.util.ArrayList;

public class MainActivity extends BaseListActivity<BasePresenter, BaseModel, String> {


    @Override
    protected void initEnv() {

    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void loadData(int action) {
        if(action == BaseListActivity.ACTION_INIT_LOAD){
            ArrayList list = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                list.add("条目" + i);
            }
            loadSuccess(list);
        }
        if(action == BaseListActivity.ACTION_PULL_REFRESH){
            ArrayList list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add("刷新条目" + i);
            }
            loadSuccess(list);
        }


    }

    @Override
    public int setItemLayoutId() {
        return R.layout.layout;
    }

    @Override
    protected void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
    }

}
