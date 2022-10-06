package com.mole.android.mole.chat.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.dp

class ChatFirstPositionItemDecoration() :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = when (parent.getChildAdapterPosition(view)) {
            0 -> 102.dp
            else -> 0
        }
    }
}