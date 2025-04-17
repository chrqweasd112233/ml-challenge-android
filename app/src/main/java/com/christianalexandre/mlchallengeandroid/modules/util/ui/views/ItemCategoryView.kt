package com.christianalexandre.mlchallengeandroid.modules.util.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

@SuppressLint("ResourceAsColor")
class ItemCategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val textView: TextView

    init {
        orientation = HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, 48.dp).apply {
            setMargins(8, 2,8, 2)
        }
        setPadding(2, 2, 2, 2)
        gravity = Gravity.CENTER_VERTICAL

        elevation = 4.dp.toFloat()
        background = GradientDrawable().apply {
            setColor(Color.WHITE)
            cornerRadius = 8.dp.toFloat()
            setStroke(1.dp, Color.LTGRAY)
            colors = null
        }

        textView = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f
            ).apply {
                marginStart = 8.dp
                marginEnd = 8.dp
            }
            minHeight = 32.dp
            textSize = 16f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER_VERTICAL
        }

        addView(textView)
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}