package com.mole.android.mole.profile.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FragmentProfileBinding
import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileViewImpl : ProfileView, MoleBaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var presenter: ProfilePresenter

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

    override fun setTotalDebtsSummary(summary: Int) {
        binding.profileDebtsSummary.text = summaryToString(summary)
    }

    override fun setTags(tags: List<String>) {
        binding.profileTags.text = tagsToString(tags)
    }

    override fun setIcon(uri: Uri?) {
        if (uri != null && uri != Uri.EMPTY) {
            binding.personProfileIcon.load(uri) {
                transformations(CircleCropTransformation())
            }
        } else {
            binding.personProfileIcon.load(R.drawable.ic_not_avatar_foreground) {
                transformations(CircleCropTransformation())
            }
        }
    }
}