package com.mole.android.mole.profile.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentProfileBinding
import com.mole.android.mole.profile.presentation.ProfilePresenter
import com.mole.android.mole.summaryToString

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
        binding.profileLogin.text = resources.getString(R.string.login_prefix, login)
    }

    override fun setTotalDebtsSummary(summary: Int) {
        binding.profileDebtsSummary.text = summaryToString(summary)
    }

    override fun setTags(tags: List<String>) {
        var tagsText = "#${tags.first()}"
        val tagsWithoutFirst = tags.drop(tags.size - 2)
        for (tag in tagsWithoutFirst) {
            tagsText += ", #$tag"
        }
        binding.profileTags.text = tagsText
    }

    override fun setIcon(bitmap: Bitmap?) {
        if (bitmap != null) {
            binding.personProfileIcon.load(bitmap) {
                transformations(CircleCropTransformation())
            }
        } else {
            binding.personProfileIcon.load(R.drawable.ic_not_avatar_foreground) {
                transformations(CircleCropTransformation())
            }
        }
    }
}