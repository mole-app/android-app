package com.mole.android.mole

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mole.android.mole.ui.BlurView


class MoleAlertDialog : DialogFragment() {
    var rootView: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_alert, container, false)
        val blurView: BlurView = view.findViewById(R.id.dialog_root)
        blurView.setupWith(rootView!!).setBlurRadius(12f)
        blurView.cornerRadius(8f.dp())

        return view
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(
            requireActivity()
        )
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null)
        val blurView: BlurView = view.findViewById(R.id.dialog_root)
        blurView.setupWith(rootView!!).setBlurRadius(12f)
        blurView.cornerRadius(8f.dp())

        builder.setView(view)
        val alertDialog = builder.create()
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.setCancelable(true)
        return alertDialog
    }
}