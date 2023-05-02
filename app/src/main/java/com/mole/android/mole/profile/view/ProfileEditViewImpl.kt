package com.mole.android.mole.profile.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FragmentProfileEditBinding
import com.mole.android.mole.profile.data.ProfileUserInfo
import com.mole.android.mole.profile.presentation.ProfileEditPresenter

class ProfileEditViewImpl : ProfileEditView,
    MoleBaseFragment<FragmentProfileEditBinding>(FragmentProfileEditBinding::inflate) {

    private lateinit var presenter: ProfileEditPresenter

    override fun getToolbar() = binding.moleProfileEditToolbar

    override fun getSoftMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
    }

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.fragment_container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = component().profileModule.profileEditPresenter
        presenter.attachView(this)
        initConfirmButton()
    }

    private fun initConfirmButton() {
        binding.profileEditConfirmBtn.setupBorder(Shape.RECTANGLE, 80f.dp)
        binding.profileEditConfirmBtn.setOnClickListener {
            val name = binding.profileEditNameEditText.text.toString()
            val login = binding.profileEditLoginEditText.text.toString()
            presenter.onConfirmButtonClick(name, login)
        }
    }

    override fun showInitData(editUserInfo: ProfileUserInfo) {
        binding.profileEditNameEditText.setText(editUserInfo.name)
        binding.profileEditLoginEditText.setText(editUserInfo.login)
        binding.personProfileEditIcon.load(editUserInfo.photoNormal) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_not_avatar_foreground)
        }
    }

    override fun showError() {
        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun closeScreen() {
        onBackPress()
    }
}
