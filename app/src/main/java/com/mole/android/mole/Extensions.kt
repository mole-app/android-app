package com.mole.android.mole

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.annotation.AttrRes
import com.github.terrakok.cicerone.Router
import com.mole.android.mole.di.MoleComponent
import com.mole.android.mole.ui.blur.BlurView
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.math.sign


fun View.setVisibleView(visible: Boolean) {
    when (visible) {
        true -> this.visibility = View.VISIBLE
        false -> this.visibility = View.GONE
    }
}

fun Context.resolveColor(@AttrRes resId: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}

fun Int.isNegative(): Boolean {
    return this.sign == -1
}

fun Int.isPositive(): Boolean {
    return this.sign == 1
}

@Deprecated(
    "Replace to val Int.dp",
    ReplaceWith("dp")
)
fun Int.dp(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

@Deprecated(
    "Replace to val Int.dp",
    ReplaceWith("dp")
)
fun Float.dp(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density

fun <T : View> T.setupCornerRadius(radius: Float): T {
    clipToOutline = true
    outlineProvider = ViewRoundRectOutlineProvider(radius)
    return this
}

enum class Shape {
    RECTANGLE,
    OVAL
}

private fun View.preparePathForBorder(shape: Shape, radiusBorder: Float): Path {
    val rectFBorder = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    val pathBorder = Path()

    when (shape) {
        Shape.RECTANGLE -> {
            val radiusArr = floatArrayOf(
                radiusBorder, radiusBorder,
                radiusBorder, radiusBorder,
                radiusBorder, radiusBorder,
                radiusBorder, radiusBorder
            )
            pathBorder.addRoundRect(
                rectFBorder,
                radiusArr,
                Path.Direction.CW
            )
        }
        Shape.OVAL -> {
            pathBorder.addOval(
                rectFBorder,
                Path.Direction.CW
            )
        }
    }
    return pathBorder
}

fun BlurView.setupBorder(): BlurView {
    return setupBorder(
        Shape.RECTANGLE,
        8f.dp,
        1f.dp,
        R.attr.colorIconDisabled,
        R.attr.colorGradientStroke
    )
}

fun <T : View> T.setupBorder(
    shape: Shape,
    radiusBorder: Float
): T {
    return setupBorder(
        shape,
        radiusBorder,
        1f.dp,
        R.attr.colorIconDisabled,
        R.attr.colorGradientStroke
    )
}

fun <T : View> T.setupBorder(
    shape: Shape,
    radiusBorder: Float,
    lineWidth: Float,
    @AttrRes colorStart: Int,
    @AttrRes colorEnd: Int
): T {

    var isCalled = false
    addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
        if (!isCalled) {

            val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
            val width = measuredWidth
            val height = measuredHeight
            val bitmap = Bitmap.createBitmap(
                width + lineWidth.toInt(),
                height + lineWidth.toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)

            val pathBorder = preparePathForBorder(shape, radiusBorder)

            val gradientStartColor = context.resolveColor(colorStart)
            val gradientEndColor = context.resolveColor(colorEnd)

            paintBorder.shader = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                gradientStartColor,
                gradientEndColor,
                Shader.TileMode.MIRROR
            )

            paintBorder.style = Paint.Style.STROKE
            paintBorder.strokeWidth = lineWidth

            canvas.translate(lineWidth / 2, lineWidth / 2)
            canvas.drawPath(pathBorder, paintBorder)
            val drawable = BitmapDrawable(resources, bitmap)
            drawable.setBounds(0, 0, width, height)
            v.overlay.add(drawable)

            isCalled = true
        }
    }
    return this
}

fun EditText.onTextChanged(onTextChanged: (s: CharSequence) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s)
        }

        override fun afterTextChanged(editable: Editable) {
        }
    })
}

fun component(): MoleComponent {
    return MoleApplication.requireComponent()
}

inline fun <reified T> Router.setResultListenerGeneric(
    key: String,
    crossinline action: (T) -> Unit
) {
    this.setResultListener(key) { data ->
        val dataT = data as? T
        if (dataT != null) {
            action(dataT)
        }
    }
}

suspend fun Call.await(): Response {
    return suspendCancellableCoroutine { continuation ->
        val callback = ContinuationCallback(this, continuation)
        enqueue(callback)
        continuation.invokeOnCancellation(callback)
    }
}

private class ContinuationCallback(
    private val call: Call,
    private val continuation: CancellableContinuation<Response>
) : Callback, CompletionHandler {

    override fun onResponse(call: Call, response: Response) {
        continuation.resume(response) {
            if (!call.isCanceled) {
                continuation.resumeWithException(it)
            }
        }
    }

    override fun onFailure(call: Call, e: IOException) {
        if (!call.isCanceled) {
            continuation.resumeWithException(e)
        }
    }

    override fun invoke(cause: Throwable?) {
        try {
            call.cancel()
        } catch (_: Throwable) {
        }
    }
}