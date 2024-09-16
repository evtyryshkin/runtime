package ru.tyryshkin.core.informer.snackbar

import android.content.Context
import androidx.annotation.StringRes
import ru.tyryshkin.core.informer.snackbar.Message.MessageRes
import ru.tyryshkin.core.informer.snackbar.Message.MessageString

sealed class Message {
    class MessageRes(@StringRes val res: Int, vararg val args: Any) : Message()
    data class MessageString(val string: String) : Message()
}

fun Message.getString(context: Context) = when (this) {
    is MessageString -> string
    is MessageRes -> context.resources.getString(res, *args)
}
