package com.mole.android.mole.about.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.mole.android.mole.ClickSpan
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ViewAboutBinding
import com.mole.android.mole.navigation.Screens.Codehub
import com.mole.android.mole.navigation.Screens.NameAbout
import com.mole.android.mole.ui.actionbar.MoleActionBar

class AboutViewImpl : MoleBaseFragment<ViewAboutBinding>(ViewAboutBinding::inflate) {

    private val router = component().routingModule.router
    private val appVersion = component().buildConfigModule.VERSION_NAME

    override fun getToolbar(): MoleActionBar {
        return binding.moleAboutToolbar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.codeSourceGroup.setOnClickListener {
            router.navigateTo(Codehub())
        }
        binding.aboutNameGroup.setOnClickListener {
            router.navigateTo(NameAbout())
        }
        binding.versionUnderline.text = requireContext().getString(R.string.settings_version_underline, appVersion)

        val emails = resources.getStringArray(R.array.emails)
        emails.forEach {
            ClickSpan.clickLink(binding.aboutText, it) {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")

                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }
            }
        }
    }
}