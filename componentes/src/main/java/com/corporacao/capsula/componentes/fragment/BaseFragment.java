package com.corporacao.capsula.componentes.fragment;

import android.support.v4.app.Fragment;

import com.corporacao.capsula.componentes.util.ActionMaps;
import com.corporacao.capsula.componentes.util.BaseView;

import es.dmoral.toasty.Toasty;


public abstract class BaseFragment extends Fragment implements BaseView, ActionMaps {

    public abstract void createRestListener();
    public abstract void mapComponents();
    public abstract void mapComponentActions();

    @Override
    public void onResume() {
        super.onResume();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().destroy();
        }
    }

    @Override
    public void showMessage(String message) {
        Toasty.success(getActivity(), message, Toasty.LENGTH_SHORT, true).show();
    }

    @Override
    public void showErrorMessage(String aMessage) {
        Toasty.error(getActivity(), aMessage, Toasty.LENGTH_SHORT, true).show();
    }

    @Override
    public void showWarningMessage(String aMessage) {
        Toasty.warning(getActivity(), aMessage, Toasty.LENGTH_SHORT, true).show();
    }

    @Override
    public void showWarningMessageLong(String aMessage) {
        Toasty.warning(getActivity(), aMessage, Toasty.LENGTH_LONG, true).show();
    }

    @Override
    public void showInfoMessage(String aMessage) {
        Toasty.info(getActivity(), aMessage, Toasty.LENGTH_SHORT, true).show();
    }
}
