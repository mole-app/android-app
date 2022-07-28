package com.mole.android.mole

import androidx.recyclerview.widget.DiffUtil

abstract class MoleBaseDiffUtilCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        checkItemsId(oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        checkContents(oldItemPosition, newItemPosition)

    abstract fun checkItemsId(oldItemPosition: Int, newItemPosition: Int): Boolean

    abstract fun checkContents(oldItemPosition: Int, newItemPosition: Int): Boolean
}