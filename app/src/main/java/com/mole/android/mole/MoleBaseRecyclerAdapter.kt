package com.mole.android.mole

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class MoleBaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data: MutableList<T> = mutableListOf()

    abstract fun getViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder

    abstract fun getViewTypeOfData(position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return getViewTypeOfData(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoleBinder<T>).bind(data[position])
    }

    override fun getItemCount() = data.size

    fun update(data: List<T>) {
        this.data.addAll(data)
    }
}