package com.corporacao.capsula.componentes.activity;

import android.app.ProgressDialog;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.corporacao.capsula.componentes.util.BaseView;
import com.corporacao.capsula.componentes.util.ActionMaps;

import es.dmoral.toasty.Toasty;

public abstract class BaseActivity extends AppCompatActivity implements AppBarLayout.OnLayoutChangeListener, ActionMaps, BaseView {

    public static final String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;

    /**
     * Método responsavel por definir qual layout xml que a tela mostrará.
     * Utilizamos o template method para executar mapComponents(), mapComponentActions() que são implemetados pelas subclasses
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mapComponents();
        mapComponentActions();
    }

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void showMessage(String message) {
        Toasty.success(this, message, Toasty.LENGTH_SHORT, true).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toasty.error(this, message, Toasty.LENGTH_SHORT, true).show();
    }

    @Override
    public void showWarningMessage(String aMessage) {
        Toasty.warning(this, aMessage, Toasty.LENGTH_SHORT, true).show();
    }

    @Override
    public void showWarningMessageLong(String aMessage) {
        Toasty.warning(this, aMessage, Toasty.LENGTH_LONG, true).show();
    }

    @Override
    public void showInfoMessage(String aMessage) {
        Toasty.info(this, aMessage, Toasty.LENGTH_SHORT, true).show();
    }

    public abstract void mapComponents();
    public abstract void mapComponentActions();

    public void showProgess() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Aguarde");
        mProgressDialog.show();
    }

    public void cancelProgess() {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
