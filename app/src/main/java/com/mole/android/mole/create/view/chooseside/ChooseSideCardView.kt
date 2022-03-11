package com.mole.android.mole.create.view.chooseside

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mole.android.mole.R

class ChooseSideCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(getContext(), R.layout.view_choose_side_card, this)
        isClickable = true
        background = ContextCompat.getDrawable(context, R.drawable.ripple_choose_side)
    }

    private val text: TextView = findViewById(R.id.choose_text)
    private val icon: ImageView = findViewById(R.id.choose_image)

    fun render(side: Side) {
        text.setText(side.textRes)
        icon.setImageResource(side.iconRes)
    }

    enum class Side(@DrawableRes val iconRes: Int, @StringRes val textRes: Int) {
        MY_DEBT(iconRes = R.drawable.create_debt_negative, textRes = R.string.my_debt),
        HIS_DEBT(iconRes = R.drawable.create_debt_positive, textRes = R.string.his_debt)
    }

}