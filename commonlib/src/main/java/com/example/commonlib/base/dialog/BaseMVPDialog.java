package com.example.commonlib.base.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.commonlib.R;
import com.example.commonlib.base.mvp.BaseModel;
import com.example.commonlib.base.mvp.BasePresenter;
import com.example.commonlib.base.mvp.BaseView;
import com.example.commonlib.utils.GenericUtil;
import com.example.commonlib.utils.UIUtil;



/**
 * Created by shaw on 2017/8/30 0030.
 */

public abstract class BaseMVPDialog<P extends BasePresenter, M extends BaseModel> extends DialogFragment implements BaseView{

    public P mPresenter;
    public M mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyStyle();
        initEnv();
    }

    protected void initEnv() {
    }

    protected void setMyStyle() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AwesomeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(setDialogView(), container, false);
        return dialogView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = GenericUtil.getType(this, 0);
        mModel = GenericUtil.getType(this, 1);
        if (mPresenter != null && mModel != null) {
            mPresenter.setMV(mModel, this);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        win.setWindowAnimations(setAnimations());
        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = UIUtil.getScreenWidthPixels(getContext());
        if (isUsePx()) {
            params.height = setHeight();
        } else {
            params.height = setHeight() == 0
                    ? UIUtil.dip2px(getActivity(), 230)
                    : UIUtil.dip2px(getActivity(), setHeight());
        }
        win.setAttributes(params);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(true);
        initView();
    }

    protected abstract int setDialogView();

    protected abstract void initView();

    public BaseMVPDialog show(FragmentManager manager) {
        super.show(manager, this.getClass().getName());
        return this;
    }

    protected int setHeight() {
        return 0;
    }

    protected boolean isUsePx() {
        return false;
    }

    protected int setAnimations() {
        return R.style.dialog_anim;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}
