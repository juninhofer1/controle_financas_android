package com.buffalo.controlefinancas.util.fragment

import androidx.fragment.app.Fragment
import com.buffalo.controlefinancas.util.BaseView
import com.buffalo.controlefinancas.util.ActionMaps
import es.dmoral.toasty.Toasty

abstract class BaseFragment : Fragment(), BaseView, ActionMaps {
    abstract fun createRestListener()
    abstract override fun mapComponents()
    abstract override fun mapComponentActions()
    override fun onResume() {
        super.onResume()
        if (this is HasPresenter<*> && (this as HasPresenter<*>).presenter != null) {
            (this as HasPresenter<*>).presenter.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (this is HasPresenter<*> && (this as HasPresenter<*>).presenter != null) {
            (this as HasPresenter<*>).presenter.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this is HasPresenter<*> && (this as HasPresenter<*>).presenter != null) {
            (this as HasPresenter<*>).presenter.destroy()
        }
    }

    override fun showMessage(message: String) {
        Toasty.success(requireActivity(), message, Toasty.LENGTH_SHORT, true).show()
    }

    override fun showErrorMessage(aMessage: String) {
        Toasty.error(requireActivity(), aMessage, Toasty.LENGTH_SHORT, true).show()
    }

    override fun showWarningMessage(aMessage: String) {
        Toasty.warning(requireActivity(), aMessage, Toasty.LENGTH_SHORT, true).show()
    }

    override fun showWarningMessageLong(aMessage: String) {
        Toasty.warning(requireActivity(), aMessage, Toasty.LENGTH_LONG, true).show()
    }

    override fun showInfoMessage(aMessage: String) {
        Toasty.info(requireActivity(), aMessage, Toasty.LENGTH_SHORT, true).show()
    }
}