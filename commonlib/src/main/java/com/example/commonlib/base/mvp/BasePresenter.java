package com.example.commonlib.base.mvp;

public abstract class BasePresenter<V extends BaseView, M> {
    protected V mView;
    protected M mModel;

    public void setMV(M model, V view) {
        mView = view;
        mModel = model;
    }

    public void detachView() {
        mView = null;
    }

    protected boolean isActive() {
        return mView != null;
    }
}
