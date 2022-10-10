package com.buffalo.controlefinancas.util.fragment

import com.buffalo.controlefinancas.util.fragment.Presenter

interface HasPresenter<T : Presenter?> {
    val presenter: T
}