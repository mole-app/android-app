package com.mole.android.mole

import androidx.lifecycle.LifecycleCoroutineScope

open class MoleBasePresenter<T : MoleBaseView> {
    protected var view: T? = null

    private val scope
        get() = view?.scope

    protected fun withScope(action: LifecycleCoroutineScope.() -> Unit) {
        view?.let { scope?.let(action) }
    }

    protected fun <T> letScope(action: (LifecycleCoroutineScope) -> T): T? {
        return scope?.let { action(it) }
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