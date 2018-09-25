package com.example.commonlib.base.activity;

import com.example.commonlib.base.mvp.BaseModel;
import com.example.commonlib.base.mvp.BasePresenter;
import com.example.commonlib.base.mvp.BaseView;
import com.example.commonlib.utils.GenericUtil;

public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity implements BaseView{

    protected P mPresenter;

    @Override
    protected void setupMVP() {
        mPresenter = GenericUtil.getType(this, 0);
        M model = GenericUtil.getType(this, 1);
        if (mPresenter != null && model != null) {
            mPresenter.setMV(model, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
