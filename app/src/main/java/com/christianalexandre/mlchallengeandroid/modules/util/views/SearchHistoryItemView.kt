package com.christianalexandre.mlchallengeandroid.modules.util.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.christianalexandre.mlchallengeandroid.R

@SuppressLint("PrivateResource")
class SearchHistoryItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val textView: TextView

    init {
        orientation = HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 48.dp)
        setPadding(16.dp, 0, 16.dp, 0)
        gravity = Gravity.CENTER_VERTICAL

        val startIcon = ImageView(context).apply {
            layoutParams = LayoutParams(24.dp, 24.dp)
            setImageResource(com.google.android.material.R.drawable.ic_clock_black_24dp)
            imageTintList = ColorStateList.valueOf(context.getColor(R.color.grey_primary))
            gravity = Gravity.CENTER_VERTICAL
        }

        textView = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f
            ).apply {
                marginStart = 16.dp
                marginEnd = 16.dp
            }
            textSize = 16f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER_VERTICAL
        }

        val endIcon = ImageView(context).apply {
            layoutParams = LayoutParams(24.dp, 24.dp)
            setImageResource(com.google.android.material.R.drawable.material_ic_keyboard_arrow_next_black_24dp)
            imageTintList = ColorStateList.valueOf(context.getColor(R.color.grey_primary))
            gravity = Gravity.CENTER_VERTICAL
        }

        addView(startIcon)
        addView(textView)
        addView(endIcon)
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
