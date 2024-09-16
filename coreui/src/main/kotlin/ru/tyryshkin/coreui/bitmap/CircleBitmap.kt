package ru.tyryshkin.coreui.bitmap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

private const val DEFAULT_SIZE = 64f

fun getCircleBitmap(circleType: CircleType): Bitmap {
    val size = DEFAULT_SIZE
    val output = Bitmap.createBitmap(size.toInt(), size.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val pCircle = Paint().apply {
        color = when (circleType) {
            CircleType.START -> Color.GREEN
            CircleType.FINISH -> Color.RED
        }
    }
    canvas.drawCircle((size / 2), (size / 2), (size / 4), pCircle)
    return output
}

enum class CircleType {
    START, FINISH
}
