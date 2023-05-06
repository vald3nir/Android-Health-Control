package com.vald3nir.cholesterol_control.domain.common.view

import com.vald3nir.core_ui.CoreFragment

abstract class BaseFragment : CoreFragment() {

    fun getBaseActivity(): BaseActivity? {
        return activity as? BaseActivity
    }

    fun showLoading(show: Boolean) {
        (activity as? BaseActivity)?.showLoading(show)
    }

    override fun onStop() {
        super.onStop()
        showLoading(false)
    }

    fun showMessage(message: String?) {}
}


