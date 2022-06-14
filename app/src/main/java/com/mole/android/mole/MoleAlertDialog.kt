package com.mole.android.mole

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.mole.android.mole.ui.blur.BlurView


class MoleAlertDialog : DialogFragment() {
    var rootView: ViewGroup? = null

    private var cancelListener: (() -> Unit)? = null
    private var acceptListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_dialog_alert, null)
        val blurView: BlurView = view.findViewById(R.id.dialog_root)
        blurView.setupWith(rootView!!).setupBlurRadius(12f).setupCornerRadius(8f.dp).setupBorder()

        val cancelButton: Button = view.findViewById(R.id.alert_cancel)
        val acceptButton: Button = view.findViewById(R.id.alert_apply)

        val alertDialog = Dialog(requireActivity(), R.style.MoleDialog)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setContentView(blurView)
        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)

        val fragmentActivity = requireActivity()
        val wm = fragmentActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager


        val display = wm.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val width = 312.dp

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(alertDialog.window?.attributes)
        layoutParams.width = width
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window?.attributes = layoutParams

        cancelButton.setOnClickListener {
            cancelListener?.invoke()
            alertDialog.dismiss()
        }

        acceptButton.setOnClickListener {
            acceptListener?.invoke()
            alertDialog.dismiss()
        }

        return alertDialog
    }

    fun setOnCancelListener(listener: () -> Unit) {
        cancelListener = listener
    }

    fun setOnAcceptListener(listener: () -> Unit) {
        acceptListener = listener
    }
}