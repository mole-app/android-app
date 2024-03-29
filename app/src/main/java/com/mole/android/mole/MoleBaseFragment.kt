package com.mole.android.mole

import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.ui.actionbar.MoleActionBar

abstract class MoleBaseFragment<T : ViewBinding>
    (private val inflation: (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment(), MoleBaseView {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    private val navigatorHolder = component().routingModule.navigationHolder
    private val router = component().routingModule.router
    private val mainActivity: MoleMainActivity get() = activity as MoleMainActivity

    open fun getSoftMode(): Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    open fun getNavigator(): Navigator = AppNavigator(requireActivity(), R.id.fragment_container)
    open fun getToolbar(): MoleActionBar? = null

    override val scope: LifecycleCoroutineScope
        get() = viewLifecycleOwner.lifecycleScope

    @MenuRes
    open fun getMenuId(): Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = getToolbar()
        if (toolbar != null) {
            val appCompatActivity = requireActivity()
            if (appCompatActivity is AppCompatActivity) {
                appCompatActivity.setSupportActionBar(toolbar)
                appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
                toolbar.setBackClickListener {
                    requireActivity().onBackPressed()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (isVisible) {
            // Check isVisible because nested fragment don't call onPause when isVisible false
            menu.clear()
            inflater.inflate(getMenuId(), menu)
            getToolbar()?.bindMenu()
            menu.iterator().forEach { menuItem ->
                menuItem.actionView?.findViewById<AppCompatImageButton>(R.id.menu_icon)?.setOnClickListener {
                    onOptionsItemSelected(menuItem)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflation(inflater, container, false)
        setRetainInstance(false)
        val root = binding.root
        val viewUnderSnackbar = getViewUnderSnackbar()
        if (viewUnderSnackbar != null) {
            if (root is ViewGroup) {
                val snackbarHolder = CoordinatorLayout(requireContext())
                val lp = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                viewUnderSnackbar.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                    binding.root.findViewById<CoordinatorLayout>(R.id.snackbarHolder).layoutParams =
                        ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, top)
                }
                snackbarHolder.layoutParams = lp
                snackbarHolder.elevation = 1000f
                snackbarHolder.id = R.id.snackbarHolder
                root.addView(snackbarHolder)
            }
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.stackFragments.add(this)
    }

    override fun onDestroy() {
        mainActivity.stackFragments.remove(this)
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(getMenuId() != 0)
        navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        if (parentFragment != null) {
            navigatorHolder.removeNavigator()
        }
        setHasOptionsMenu(false)
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.setSoftInputMode(getSoftMode())
    }

    open fun onBackPress(): Boolean {
        router.exit()
        return true
    }

    open fun getViewUnderSnackbar(): View? = null

    protected fun withBinding(action: T.() -> Unit) {
        val binding = _binding
        if (binding != null) {
            action(binding)
        }
    }
}
