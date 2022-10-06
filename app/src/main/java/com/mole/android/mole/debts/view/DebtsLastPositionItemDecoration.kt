package com.mole.android.mole.debts.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.dp

class DebtsLastPositionItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            outRect.bottom = when (parent.getChildAdapterPosition(view)) {
                it.itemCount - 1 -> 92.dp
                else -> 0
            }
        }
    }
}