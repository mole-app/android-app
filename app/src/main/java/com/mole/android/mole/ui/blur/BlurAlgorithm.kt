package com.mole.android.mole.ui.blur

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

class BlurAlgorithm(context: Context) {
    private val renderScript: RenderScript? = RenderScript.create(context)
    private val blurScript: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(
        renderScript, Element.U8_4(
            renderScript
        )
    )

    private var outAllocation: Allocation? = null

    private var lastBitmapWidth = -1
    private var lastBitmapHeight = -1


    private fun canReuseAllocation(bitmap: Bitmap): Boolean {
        return bitmap.height == lastBitmapHeight && bitmap.width == lastBitmapWidth
    }

    fun blur(bitmap: Bitmap, blurRadius: Float): Bitmap {
        val inAllocation: Allocation = Allocation.createFromBitmap(renderScript, bitmap)
        if (!canReuseAllocation(bitmap)) {
            if (outAllocation != null) {
                outAllocation?.destroy()
            }
            outAllocation = Allocation.createTyped(renderScript, inAllocation.getType())
            lastBitmapWidth = bitmap.width
            lastBitmapHeight = bitmap.height
        }
        blurScript.setRadius(blurRadius)
        blurScript.setInput(inAllocation)
        blurScript.forEach(outAllocation)
        outAllocation?.copyTo(bitmap)
        inAllocation.destroy()
        return bitmap
    }

    fun destroy() {
        blurScript.destroy()
        renderScript!!.destroy()
        if (outAllocation != null) {
            outAllocation?.destroy()
        }
    }

    fun getBitmapConfig(): Bitmap.Config {
        return Bitmap.Config.ARGB_8888
    }
}