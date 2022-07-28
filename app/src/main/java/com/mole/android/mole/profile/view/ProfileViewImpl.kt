package com.mole.android.mole.profile.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.snackbar.Snackbar
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FragmentProfileBinding
import com.mole.android.mole.navigation.Screens.Settings
import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileViewImpl : ProfileView,
    MoleBaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var presenter: ProfilePresenter
    private val router = component().routingModule.router
    private val navigator = component().routingModule.navigationHolder

    override fun getToolbar() = binding.moleProfileToolbar
    override fun getMenuId() = R.menu.profile_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = component().profileModule.profilePresenter

        binding.tagsGroup.setOnClickListener {
            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
        }

        presenter.attachView(this)
    }

    override fun setProfileName(name: String) {
        binding.profileName.text = name
    }

    override fun setProfileLogin(login: String) {
        binding.profileLogin.text = resources.getString(R.string.login_prefix, login)
    }

    override fun setTotalDebtsSummary(summary: Long) {
        binding.profileDebtsSummary.text = summaryToString(summary)
    }

    override fun setTags(tags: List<String>) {
        binding.profileTags.text = tagsToString(tags)
    }

    override fun setIcon(uri: String?) {
        if (uri != null && uri.isNotEmpty()) {
            binding.personProfileIcon.load(uri) {
                transformations(CircleCropTransformation())
            }
        } else {
            binding.personProfileIcon.load(R.drawable.ic_not_avatar_foreground) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_menu_item -> {
                navigator.setNavigator(AppNavigator(requireActivity(), R.id.fragment_container))
                router.navigateTo(Settings())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showSnackBar(message: String) {
        val snakbar = Snackbar.make(binding.root.findViewById(R.id.snackbarHolder), "message", Snackbar.LENGTH_SHORT)
        snakbar.setBackgroundTint(requireContext().resolveColor(R.attr.colorOnSurface))
        snakbar.show()
    }
}