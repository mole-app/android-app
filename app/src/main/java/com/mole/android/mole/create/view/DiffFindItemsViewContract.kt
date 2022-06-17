package com.mole.android.mole.create.view

import android.view.View

abstract class DiffFindItemsViewContract<T>(
    private val view: ChooseTextItemView,
    private val itemsSame: (T, T) -> Boolean,
    private val contentSame: (T, T) -> Boolean,
    private val bindView: (View, T) -> Unit,
    private val textForItem: (T) -> String,
) : ChooseTextItemView.ItemViewContract {

    private var currentData: List<T> = emptyList()
    private var newData: List<T> = emptyList()

    override fun itemsCount() = currentData.size

    override fun newListCount() = newData.size

    override fun bind(view: View, position: Int) {
        bindView(view, currentData[position])
    }

    override fun contentSame(firstPosition: Int, secondPosition: Int): Boolean {
        return contentSame(currentData[firstPosition], newData[secondPosition])
    }

    override fun itemSame(firstPosition: Int, secondPosition: Int): Boolean {
        return itemsSame(currentData[firstPosition], newData[secondPosition])
    }

    override fun textForClickedItem(position: Int): String {
        return when {
            position < currentData.size -> {
                textForItem(currentData[position])
            }
            position < newData.size -> {
                textForItem(newData[position])
            }
            else -> ""
        }
    }

    override fun payload(position: Int): Any? = currentData.getOrNull(position)

    override fun onNextClicked(selectedPosition: Int, payload: Any?) {
        (payload as? T)?.let { onSelectedItemNextClicked(it) }
    }

    open fun onSelectedItemNextClicked(item: T) {}

    fun updateData(data: List<T>) {
        newData = data
        view.invalidateList()
        currentData = newData
    }
}