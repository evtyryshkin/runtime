package ru.tyryshkin.core.informer.snackbar

import android.content.Context
import androidx.annotation.StringRes

sealed class Label {
    class LabelRes(@StringRes val label: Int) : Label()
    data class LabelString(val label: String) : Label()
}

fun Label.getString(context: Context) = when (this) {
    is Label.LabelString -> label
    is Label.LabelRes -> context.resources.getString(label)
}
