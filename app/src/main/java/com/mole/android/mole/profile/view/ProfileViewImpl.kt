package com.mole.android.mole.profile.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.github.terrakok.cicerone.androidx.AppNavigator
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

        initRetryButton()
    }

    private fun initRetryButton() {
        binding.retryButton.setOnClickListener {
            presenter.onRetryClick()
            binding.retryButton.isEnabled = false
        }
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

    override fun showError() {
        hideContent()
        binding.retryButton.isEnabled = true
        binding.errorContainer.visibility = View.VISIBLE
    }

    private fun hideContent() {
        binding.personProfileIcon.visibility = View.GONE
        binding.profileContainer.visibility = View.GONE
        binding.profileDebtsSummaryTitle.visibility = View.GONE
        binding.profileDebtsSummary.visibility = View.GONE
        binding.tagsGroup.visibility = View.GONE
    }

    override fun showContent() {
        binding.retryButton.isEnabled = true
        binding.personProfileIcon.visibility = View.VISIBLE
        binding.profileContainer.visibility = View.VISIBLE
        binding.profileDebtsSummaryTitle.visibility = View.VISIBLE
        binding.profileDebtsSummary.visibility = View.VISIBLE
        binding.tagsGroup.visibility = View.VISIBLE
        binding.errorContainer.visibility  =View.GONE
    }
}