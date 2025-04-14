package com.christianalexandre.mlchallengeandroid.modules.util.extensions

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

fun Drawable.startAnimation(): Drawable {
    if (this is AnimatedVectorDrawable)
        this.start()
    else if (this is AnimatedVectorDrawableCompat)
        this.start()
    return this
}