package com.mole.android.mole.create.view

import android.content.Context
import android.text.SpannableStringBuilder
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
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val list: RecyclerView
    private val textContainer: ViewGroup
    private val listContainer: ViewGroup
    private val text: TextInputLayout
    private val clickableArea: View
    private val nextButton: View
    private val progress: View

    private var mode = false

    private var dataBinder: DataBinder? = null

    init {
        inflate(getContext(), R.layout.choose_text_item_view, this)
        list = findViewById(R.id.list)
        textContainer = findViewById(R.id.text_container)
        listContainer = findViewById(R.id.list_container)
        text = findViewById(R.id.text)
        clickableArea = findViewById(R.id.clickable_area)
        nextButton = findViewById(R.id.next_button)
        progress = findViewById(R.id.progress)
        bind()
    }

    fun setDataBinder(dataBinder: DataBinder) {
        this.dataBinder = dataBinder
        bindData(dataBinder)
    }

    fun showProgress() {
        list.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        list.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

    fun invalidateList() {
        list.adapter?.notifyDataSetChanged()
    }

    private fun bindData(dataBinder: DataBinder) {
        val adapter = ListAdapter(dataBinder) {
            fillEditText(dataBinder.textForClickedItem(it))
            if (!mode) {
                mode = true
                clickableArea.visibility = View.VISIBLE
                shrinkList()
                hideKeyBoard()
            }
        }
        list.adapter = adapter
    }

    private fun fillEditText(textToFill: String) {
        text.editText?.let { editText ->
            editText.text = SpannableStringBuilder(textToFill)
            editText.setSelection(editText.length())
        }
    }

    private fun bind() {
        list.layoutManager = LinearLayoutManager(context)

        clickableArea.setOnClickListener {
            if (mode) {
                mode = false
                clickableArea.visibility = View.INVISIBLE
                expandList()
                showKeyboard()
            }
        }

        nextButton.setOnClickListener { dataBinder?.onNextClicked() }

        showKeyboard()
    }

    private fun expandList() {
        TransitionManager.beginDelayedTransition(this)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            listContainer.id,
            ConstraintSet.TOP,
            textContainer.id,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            nextButton.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.clear(nextButton.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(this)
    }

    private fun shrinkList() {
        TransitionManager.beginDelayedTransition(this)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            listContainer.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            nextButton.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
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

    private class ListAdapter(
        private val dataBinder: DataBinder,
        private val clickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(parent, dataBinder.layoutId, clickListener)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.bind(position)
            dataBinder.bind(holder.itemView, position)
        }

        override fun getItemCount() = dataBinder.itemsCount()
    }

    private class ItemViewHolder(
        parent: ViewGroup,
        @LayoutRes layoutId: Int,
        private val onClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {

        private var itemPosition: Int? = null

        init {
            itemView.setOnClickListener {
                itemPosition?.let(onClickListener)
            }
        }

        fun bind(position: Int) {
            itemPosition = position
        }
    }

    interface DataBinder {
        val layoutId: Int
        fun itemsCount(): Int
        fun bind(view: View, position: Int)
        fun contentSame(firstPosition: Int, secondPosition: Int): Boolean
        fun itemSame(firstPosition: Int, secondPosition: Int): Boolean
        fun textForClickedItem(position: Int): String
        fun onNextClicked()
    }

}