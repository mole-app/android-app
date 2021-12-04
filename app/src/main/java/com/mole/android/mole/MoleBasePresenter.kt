package com.mole.android.mole


open class MoleBasePresenter<T> {
    protected var view: T? = null

    open fun attachView(view: T) {
        this.view = view
    }

    open fun detachView() {
        view = null
    }

    fun applyWithView(action: (T) -> Unit){
        view?.apply(action)
    }
}