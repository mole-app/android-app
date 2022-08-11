package com.mole.android.mole.test

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mole.android.mole.R
import com.mole.android.mole.ui.MoleMessageViewWithInfo
import com.mole.android.mole.ui.MoleScrollView
import com.mole.android.mole.ui.actionbar.MoleActionBar


class FragmentTest : Fragment() {

    private var toolbar: MoleActionBar? = null

    private val scrollView: MoleScrollView by lazy { requireView().findViewById(R.id.test_main_scroll) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.mole_toolbar_with_text)

        val appCompatActivity = requireActivity()
        if (appCompatActivity is AppCompatActivity) {
            appCompatActivity.setSupportActionBar(toolbar)
            appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        val moleMessageView: MoleMessageViewWithInfo = view.findViewById(R.id.test_mole_message)
        moleMessageView.balance = 1000
//        val popupProvider = PopupProvider(requireContext(), scrollView, view)
//        moleMessageView.setOnTouchListener(popupProvider.touchListener)
//        moleMessageView.setOnLongClickListener {
//            popupProvider.start(it)
//            true
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requireActivity().window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.test_screen, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // First clear current all the menu items
        menu.clear()

        // Add the new menu items
        inflater.inflate(R.menu.history_menu, menu)
        toolbar?.bindMenu()
    }
}