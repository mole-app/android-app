package com.mole.android.mole.create.view

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.*
import androidx.transition.TransitionManager
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.R
import com.mole.android.mole.onSubmit
import com.mole.android.mole.onTextChangeSkipped
import com.mole.android.mole.openKeyboard

class ChooseTextItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val list: RecyclerView
    private val textContainer: ViewGroup
    private val listContainer: ViewGroup
    private val errorContainer: ViewGroup
    private val text: TextInputLayout
    private val clickableArea: View
    private val nextButton: View
    private val progress: View
    private val title: TextView
    private val retryButton: Button
    private val emptyStub: View

    private var mode = false
    private var selectedIx = 0
    private var payload: Any? = null

    private var itemViewContract: ItemViewContract? = null

    init {
        inflate(getContext(), R.layout.choose_text_item_view, this)
        list = findViewById(R.id.list)
        textContainer = findViewById(R.id.text_container)
        listContainer = findViewById(R.id.list_container)
        text = findViewById(R.id.text)
        clickableArea = findViewById(R.id.clickable_area)
        nextButton = findViewById(R.id.next_button)
        progress = findViewById(R.id.progress)
        title = findViewById(R.id.title)
        errorContainer = findViewById(R.id.error_container)
        retryButton = findViewById(R.id.retry_button)
        emptyStub = findViewById(R.id.empty_stub)
        bind()
    }

    fun setDataBinder(itemViewContract: ItemViewContract) {
        this.itemViewContract = itemViewContract
        bindData(itemViewContract)
    }

    fun showProgress() {
        list.setVisibility(false)
        progress.setVisibility(true)
        errorContainer.setVisibility(false)
        emptyStub.setVisibility(false)
    }

    fun hideProgress() {
        showList()
    }

    fun showError() {
        list.setVisibility(false)
        progress.setVisibility(false)
        errorContainer.setVisibility(true)
        emptyStub.setVisibility(false)
    }

    fun showEmptyState() {
        list.setVisibility(false)
        progress.setVisibility(false)
        errorContainer.setVisibility(false)
        emptyStub.setVisibility(true)
    }

    fun hideEmptyState() {
        showList()
    }

    fun hideError() {
        showList()
    }

    private fun showList() {
        list.setVisibility(true)
        progress.setVisibility(false)
        errorContainer.setVisibility(false)
        emptyStub.setVisibility(false)
    }

    fun setOnRetryClickListener(listener: () -> Unit) {
        retryButton.setOnClickListener { listener() }
    }

    fun focus() {
        text.editText?.requestFocus()
        showKeyboard()
    }

    fun invalidateList() {
        itemViewContract?.let { contract ->
            list.adapter?.let { adapter ->
                val callback = DiffCallback(contract)
                val productDiffResult = DiffUtil.calculateDiff(callback)
                productDiffResult.dispatchUpdatesTo(adapter)
                list.scrollToPosition(0)
            }
        }
    }

    private fun bindData(itemViewContract: ItemViewContract) {
        val adapter = ListAdapter(itemViewContract) {
            confirmItem(it)
        }
        adapter.setHasStableIds(true)
        list.adapter = adapter
        title.setText(itemViewContract.titleId)
    }

    private fun confirmItem(ix: Int) {
        val contract = itemViewContract ?: return
        if (contract.itemsCount() <= ix) return
        val item = contract.textForClickedItem(ix)
        fillEditText(item)
        selectedIx = ix
        payload = contract.payload(selectedIx)
        if (!mode) {
            mode = true
            clickableArea.visibility = View.VISIBLE
            shrinkList()
            hideKeyBoard()
        }
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
        nextButton.setOnClickListener { itemViewContract?.onNextClicked(selectedIx, payload) }

        text.editText?.onTextChangeSkipped {
            itemViewContract?.onTextChanged(it)
        }

        text.editText?.onSubmit {
            confirmItem(0)
        }

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

    private fun View.setVisibility(isVisible: Boolean) {
        val viewVisibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        if (viewVisibility != visibility) visibility = viewVisibility
    }

    private fun showKeyboard() {
        openKeyboard()
    }

    private fun hideKeyBoard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(windowToken, 0)
    }

    private class ListAdapter(
        private val itemViewContract: ItemViewContract,
        private val clickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(parent, itemViewContract.layoutId, clickListener)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            itemViewContract.bind(holder.itemView, position)
        }

        override fun getItemCount() = itemViewContract.itemsCount()

        override fun getItemId(position: Int): Long {
            return itemViewContract.id(position)
        }
    }

    private class ItemViewHolder(
        parent: ViewGroup,
        @LayoutRes layoutId: Int,
        private val onClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {
        init {
            itemView.setOnClickListener {
                onClickListener(adapterPosition)
                adapterPosition.let(onClickListener)
            }
        }
    }

    private class DiffCallback(private val contract: ItemViewContract) : DiffUtil.Callback() {
        override fun getOldListSize() = contract.itemsCount()
        override fun getNewListSize() = contract.newListCount()
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            contract.itemSame(oldItemPosition, newItemPosition)

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            contract.contentSame(oldItemPosition, newItemPosition)
    }

    interface ItemViewContract {
        val layoutId: Int
        val titleId: Int
        fun itemsCount(): Int
        fun newListCount(): Int
        fun bind(view: View, position: Int)
        fun contentSame(firstPosition: Int, secondPosition: Int): Boolean
        fun itemSame(firstPosition: Int, secondPosition: Int): Boolean
        fun textForClickedItem(position: Int): String
        fun id(position: Int): Long
        fun onNextClicked(selectedPosition: Int, payload: Any?)
        fun onTextChanged(text: String)
        fun payload(position: Int): Any?
    }

}