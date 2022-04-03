package com.mole.android.mole.create.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.R

class ChooseTextItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr)  {

    private val list: RecyclerView
    private val textContainer: ViewGroup
    private val listContainer: ViewGroup
    private val text: TextInputLayout
    private val clickableArea: View
    private val nextButton: View

    private var mode = false

    init {
        inflate(getContext(), R.layout.choose_text_item_view, this)
        list = findViewById(R.id.list)
        textContainer = findViewById(R.id.text_container)
        listContainer = findViewById(R.id.list_container)
        text = findViewById(R.id.text)
        clickableArea = findViewById(R.id.clickable_area)
        nextButton = findViewById(R.id.next_button)
        bind()
    }

    private fun bind() {
        list.layoutManager = LinearLayoutManager(context)
        val adapter = ListAdapter {
            if (!mode) {
                mode = true
                clickableArea.visibility = View.VISIBLE
                shrinkList()
                hideKeyBoard()
            }
        }
        list.adapter = adapter
        adapter.data = (0 .. 20).map { BaseItem(it) }


        clickableArea.setOnClickListener {
            if (mode) {
                mode = false
                clickableArea.visibility = View.INVISIBLE
                expandList()
                showKeyboard()
            }
        }

        showKeyboard()
    }

    private fun expandList() {
        TransitionManager.beginDelayedTransition(this)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(listContainer.id, ConstraintSet.TOP, textContainer.id, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(nextButton.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
        constraintSet.clear(nextButton.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(this)
    }

    private fun shrinkList() {
        TransitionManager.beginDelayedTransition(this)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(listContainer.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(nextButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
        constraintSet.clear(nextButton.id, ConstraintSet.TOP)
        constraintSet.applyTo(this)
    }

    private fun showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyBoard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(windowToken, 0)
    }

    private class ListAdapter(private val clickListener: (BaseItem) -> Unit) : RecyclerView.Adapter<BaseItemViewHolder>() {

        var data: List<BaseItem> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
            return ChooseUserViewHolder(parent, clickListener)
        }

        override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int) {
            if (holder is ChooseUserViewHolder) {
                holder.bind(data[position])
            }
        }

        override fun getItemCount() = data.size
    }

    private abstract class BaseItemViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {
    }

    private class ChooseUserViewHolder(
        parent: ViewGroup,
        private val itemClickListener: (BaseItem) -> Unit
    ) : BaseItemViewHolder(parent, R.layout.choose_user_holder) {
        private var item: BaseItem? = null

        init {
            itemView.setOnClickListener {
                item?.let {
                    itemClickListener(it)
                }
            }
        }

        fun bind(item: BaseItem) {
            this.item = item
        }
    }

    private data class BaseItem(val id: Int)

}