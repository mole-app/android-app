package com.mole.android.mole.create.view.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseStepsHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)), StepsAdapter.Focusable {
    abstract fun bind()
}