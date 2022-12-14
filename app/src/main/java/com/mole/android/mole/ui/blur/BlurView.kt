package com.mole.android.mole.ui.blur

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.mole.android.mole.*


class BlurView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @ColorInt
    private val overlayColor: Int

    companion object {
        const val DEFAULT_BLUR_RADIUS = 16f
        const val DEFAULT_SCALE_FACTOR = 8f
    }

    private var rootView: ViewGroup? = null

    private var blurRadius: Float = DEFAULT_BLUR_RADIUS

    private var blurAlgorithm: BlurAlgorithm = BlurAlgorithm(context)
    private lateinit var internalCanvas: BlurViewCanvas
    private lateinit var internalBitmap: Bitmap

    private val rootLocation = IntArray(2)
    private val blurViewLocation = IntArray(2)

    private val scaleSize = ScaleSize(DEFAULT_SCALE_FACTOR)
    private var scaleFactor = 1f

    private var initialized = false

    private var frameClearDrawable: Drawable? = null
    private val paint = Paint(Paint.FILTER_BITMAP_FLAG)


    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BlurView,
            defStyleAttr,
            0
        )
        overlayColor = typedArray.getColor(
            R.styleable.BlurView_blurBackgroundColor,
            Color.TRANSPARENT
        )
        typedArray.recycle()

        updateBlurParameters(measuredWidth, measuredHeight)
    }

    fun setupWith(rootView: ViewGroup?): BlurView {
        this.rootView = rootView
        initialized = false
        return this
    }

    fun setupBlurRadius(radius: Float): BlurView {
        blurRadius = radius
        return this
    }

    fun setFrameClearDrawable(frameClearDrawable: Drawable?) {
        this.frameClearDrawable = frameClearDrawable
    }

    private fun updateBlurParameters(measuredWidth: Int, measuredHeight: Int) {

        if (scaleSize.isZeroSized(measuredWidth, measuredHeight)) {
            setWillNotDraw(true)
            return
        }

        setWillNotDraw(false)
        allocateBitmap(measuredWidth, measuredHeight)
        internalCanvas = BlurViewCanvas(internalBitmap)
        initialized = true
    }

    private fun allocateBitmap(measuredWidth: Int, measuredHeight: Int) {
        val bitmapSize: ScaleSize.Size = scaleSize.scale(measuredWidth, measuredHeight)
        scaleFactor = bitmapSize.scaleFactor
        internalBitmap = Bitmap.createBitmap(
            measuredWidth,
            measuredHeight,
            blurAlgorithm.getBitmapConfig()
        )
    }

    private fun blurring() {
        if (!initialized) {
            return
        }
        if (frameClearDrawable != null) {
            frameClearDrawable?.draw(internalCanvas)
        } else {
            internalBitmap.eraseColor(Color.TRANSPARENT)
        }

        internalCanvas.save()
        setupInternalCanvasMatrix()
        rootView?.draw(internalCanvas)
        internalCanvas.restore()

        internalBitmap = blurAlgorithm.blur(internalBitmap, blurRadius)
        internalCanvas.setBitmap(internalBitmap)
    }

    private fun setupInternalCanvasMatrix() {
        rootView?.getLocationOnScreen(rootLocation)
        getLocationOnScreen(blurViewLocation)
        val left = blurViewLocation[0] - rootLocation[0]
        val top = blurViewLocation[1] - rootLocation[1]
        val scaledLeftPosition = -left / scaleFactor
        val scaledTopPosition = -top / scaleFactor
        internalCanvas.translate(scaledLeftPosition, scaledTopPosition)
        internalCanvas.scale(1 / scaleFactor, 1 / scaleFactor)
    }

    override fun draw(canvas: Canvas?) {
        val shouldDraw: Boolean = canvas?.let {
            drawView(it)
        } == true
        if (shouldDraw) {
            super.draw(canvas)
        }
    }

    private fun drawView(canvas: Canvas): Boolean {
        if (!initialized) {
            return true
        }

        if (canvas is BlurViewCanvas) {
            return false
        }
        blurring()

        canvas.save()
        canvas.scale(scaleFactor, scaleFactor)
        canvas.drawBitmap(internalBitmap, 0f, 0f, paint)
        canvas.restore()
        if (overlayColor != Color.TRANSPARENT) {
            canvas.drawColor(overlayColor)
        }

        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateBlurParameters(measuredWidth, measuredHeight)
    }
}