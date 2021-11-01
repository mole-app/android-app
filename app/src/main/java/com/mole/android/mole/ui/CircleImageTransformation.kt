package com.mole.android.mole.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation


class CircleImageTransformation : Transformation {

    override fun key(): String {
        return "circle"
    }

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val path = Path()
        path.addCircle(
            (input.width / 2).toFloat(),
            (input.height / 2).toFloat(),
            (input.width / 2).toFloat(),
            Path.Direction.CCW
        )
        val answerBitmap = Bitmap.createBitmap(input.width, input.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(answerBitmap)
        canvas.clipPath(path)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(input, 0f, 0f, paint)
        input.recycle()
        return answerBitmap
    }
}