package com.mole.android.mole.profile.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentProfileEditBinding
import com.mole.android.mole.profile.data.ProfileEditUserInfo
import com.mole.android.mole.profile.presentation.ProfileEditPresenter

class ProfileEditViewImpl : ProfileEditView,
    MoleBaseFragment<FragmentProfileEditBinding>(FragmentProfileEditBinding::inflate) {

    private lateinit var presenter: ProfileEditPresenter
    private val router = component().routingModule.router

    override fun getToolbar() = binding.moleProfileEditToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = component().profileEditModule.profileEditPresenter
        presenter.attachView(this)
    }

    override fun showInitData(editUserInfo: ProfileEditUserInfo) {
        binding.profileEditName.text = editUserInfo.name
        binding.profileEditLogin.text =
            resources.getString(R.string.login_prefix, editUserInfo.login)
        binding.personProfileEditIcon.load(editUserInfo.photoNormal) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_not_avatar_foreground)
        }
    }

    override fun showError() {
        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun backPressed(editUserInfo: ProfileEditUserInfo) {
        router.sendResult(RESULT_EDIT_PROFILE, editUserInfo)
        router.exit()
    }

    override fun onBackPress(): Boolean {
        presenter.onBackPressed()?.let {
            router.sendResult(RESULT_EDIT_PROFILE, it)
        }
        router.exit()
        return true
    }

    companion object {
        const val RESULT_EDIT_PROFILE = "RESULT_EDIT_PROFILE"
    }
}
