package com.mole.android.mole.profile.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentProfileBinding
import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileViewImpl : ProfileView, MoleBaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var presenter: ProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            component().profileModule.profilePresenter


        presenter.attachView(this)
    }

    override fun setProfileName(name: String) {
        binding.profileName.text = name
    }

    override fun setProfileLogin(login: String) {
        binding.profileLogin.text = login
    }

    override fun setTotalDebtsSummary(summary: Int) {
        binding.profileDebtsSummary.text = summary.toString()
    }

    override fun setTags(tags: List<String>) {
        binding.profileTags.text = tags.toString()
    }
}