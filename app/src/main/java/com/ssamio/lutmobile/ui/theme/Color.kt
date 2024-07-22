package com.ssamio.lutmobile.ui.theme

import android.content.Context
import androidx.core.content.ContextCompat
import com.ssamio.lutmobile.R
import kotlin.properties.Delegates

object Colors {
    // Get colors from the XML for easier matching. These are not used anywhere, however
    var primaryColor by Delegates.notNull<Int>()
    var secondaryColor by Delegates.notNull<Int>()
    var accentColor by Delegates.notNull<Int>()
    var backgroundColor by Delegates.notNull<Int>()
    var textColor by Delegates.notNull<Int>()
    var hintTextColor by Delegates.notNull<Int>()

    fun init(context: Context) {
        primaryColor = ContextCompat.getColor(context, R.color.primaryColor)
        secondaryColor = ContextCompat.getColor(context, R.color.secondaryColor)
        accentColor = ContextCompat.getColor(context, R.color.accentColor)
        backgroundColor = ContextCompat.getColor(context, R.color.backgroundColor)
        textColor = ContextCompat.getColor(context, R.color.textColor)
        hintTextColor = ContextCompat.getColor(context, R.color.hintTextColor)
    }
}