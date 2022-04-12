package com.mole.android.mole

import androidx.lifecycle.LifecycleCoroutineScope


open class MoleBasePresenter<T : MoleBaseView> {
    protected var view: T? = null

    private val scope
        get() = view?.scope

    protected fun withScope(action: (LifecycleCoroutineScope) -> Unit) {
        view?.let { scope?.let(action) }
    }

    open fun attachView(view: T) {
        this.view = view
    }

    open fun detachView() {
        view = null
    }

    protected inline fun withView(action: (T) -> Unit) {
        view?.apply(action)
    }
}