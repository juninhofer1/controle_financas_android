package com.corporacao.capsula.componentes.progress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.corporacao.capsula.componentes.R;

public class CustomProgressDialog extends ProgressDialog {
        
    private String mTitulo;
    private String mMensagem;
    private boolean mCancelavel;
    private boolean mIndeterminavel;
    private Integer mMaximo;
    private int mProgressStyle;

    public CustomProgressDialog(Activity context) {
        super(context);
        mTitulo = null;
        mMensagem = context.getString(R.string.progress_mensagem_padrao);
        mCancelavel = false;
        mIndeterminavel = true;
        mMaximo = 100;
        mProgressStyle = ProgressDialog.STYLE_SPINNER;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        mTitulo = null;
        mMensagem = context.getString(R.string.progress_mensagem_padrao);
        mCancelavel = false;
        mIndeterminavel = true;
        mMaximo = 100;
        mProgressStyle = ProgressDialog.STYLE_SPINNER;
    }

    public static CustomProgressDialog instantiate(Activity context) {
        return new CustomProgressDialog(context);
    }
    public static CustomProgressDialog instantiate(Activity context, int theme) {
        return new CustomProgressDialog(context,theme);
    }

    public CustomProgressDialog withTitulo(String titulo) {
        mTitulo = titulo;
        return this;
    }

    public CustomProgressDialog withMensagem(String mensagem) {
        mMensagem = mensagem;
        return this;
    }

    public CustomProgressDialog withCancelavel(boolean cancelavel) {
        mCancelavel = cancelavel;
        return this;
    }

    public CustomProgressDialog withIndeterminavel(boolean indeterminavel) {
        mIndeterminavel = indeterminavel;
        return this;
    }

    public CustomProgressDialog withMaximo(Integer maximo) {
        mMaximo = maximo;
        return this;
    }

    public CustomProgressDialog withProgressStyle(int progressStyle) {
        mProgressStyle = progressStyle;
        return this;
    }

    @Override
    public void show() {
        if(mTitulo != null) {
            setTitle(mTitulo);
        }
        if(mMensagem != null) {
            setMessage(mMensagem);
        }
        if(mMaximo != null) {
            setMax(mMaximo);
        }
        setProgressStyle(mProgressStyle);
        setCancelable(mCancelavel);
        setIndeterminate(mIndeterminavel);
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}