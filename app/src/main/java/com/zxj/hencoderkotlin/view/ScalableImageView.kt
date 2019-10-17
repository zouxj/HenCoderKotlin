package com.zxj.hencoderkotlin.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.zxj.hencoderkotlin.Utils
import kotlin.math.max
import kotlin.math.min

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/10/9.
 * 双向滑动
 */
class ScalableImageView(var ct: Context?, attrs: AttributeSet?) : View(ct, attrs) {
    private val IMAGE_WIDTH = Utils.dp2px(300f)
    private val OVER_SCALE_FACTOR = 1.5f
    private var bitmap: Bitmap
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originalOffsetX: Float = 100f
    private var originalOffsetY: Float = 100f
    public var big: Boolean = false
    private var detector: GestureDetectorCompat
    private var henGes: HenGestureListener
    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private var currentScale: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private var TAG: String = "ScalableImageView"
    private var scaleAnimator: ObjectAnimator? = null
    private  var scroller: OverScroller
    internal var henFlingRunner = HenFlingRunner()

    private fun getCurrentScale(): Float {
        return currentScale
    }

    private fun setCurrentScale(currentScale: Float) {
        this.currentScale = currentScale
        Log.i(TAG, "currentScale===>$currentScale")

        invalidate()
    }

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        henGes = HenGestureListener()
        detector = GestureDetectorCompat(ct, henGes)
        scroller = OverScroller(context)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = ((width - bitmap.width) / 2).toFloat()
        originalOffsetY = ((height - bitmap.height) / 2).toFloat()

        if (bitmap.width.toFloat() / bitmap.height > width.toFloat() / height) {
            smallScale = width.toFloat() / bitmap.width
            bigScale = height.toFloat() / bitmap.height * OVER_SCALE_FACTOR
        } else {
            smallScale = height.toFloat() / bitmap.height
            bigScale = width.toFloat() / bitmap.width * OVER_SCALE_FACTOR
        }
        currentScale = smallScale
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.translate(offsetX, offsetY)
        canvas.run {
            scale(currentScale, currentScale, width / 2f, height / 2f)
            drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
        }
    }

    private fun getScaleAnimator(): ObjectAnimator {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0f)
        }
        scaleAnimator!!.setFloatValues(smallScale, bigScale)
        return scaleAnimator as ObjectAnimator
    }

    inner class HenGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            big = !big
            if (big) {
                offsetX = e!!.x - width / 2f - (e.x - width / 2) * bigScale / smallScale
                offsetY = e.y - height / 2f - (e.y - height / 2) * bigScale / smallScale
                fixOffsets()
                getScaleAnimator().start()
            } else {
                getScaleAnimator().reverse()
            }
            return false

        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big) {
                scroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    -(bitmap.width * bigScale - width).toInt() / 2,
                    (bitmap.width * bigScale - width).toInt() / 2,
                    -(bitmap.height * bigScale - height).toInt() / 2,
                    (bitmap.height * bigScale - height).toInt() / 2
                )
                postOnAnimation(henFlingRunner)
            }

            return false
        }

        private fun fixOffsets() {
            offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
            offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
            offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
            offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
        }


    }
    internal inner class HenFlingRunner : Runnable {

        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }
    }
}