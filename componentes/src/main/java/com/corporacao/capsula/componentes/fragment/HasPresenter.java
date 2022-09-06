package com.corporacao.capsula.componentes.fragment;

public interface HasPresenter<T extends Presenter> {

    T getPresenter();
}
