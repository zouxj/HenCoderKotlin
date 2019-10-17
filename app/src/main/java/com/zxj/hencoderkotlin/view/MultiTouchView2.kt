package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/10/10.
 */
class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    val TAG: String = "MultiTouchView1"
    private val IMAGE_WIDTH = Utils.dp2px(150f)
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var sumX = 0f
        var sumY = 0f
        var pointerCount = event!!.pointerCount
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (i in 0 until pointerCount) {
            if (!(isPointerUp && i == event.actionIndex)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if (isPointerUp) {
            pointerCount -= 1
        }
        Log.i(TAG," _ $sumX ___ $sumY")

        val focusX = sumX / pointerCount
        val focusY = sumY / pointerCount

        when (event!!.actionMasked) {

            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_POINTER_DOWN  -> {
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = originalOffsetX + focusX - downX
                offsetY = originalOffsetY + focusY - downY
                invalidate()
            }
            else -> {

            }

        }
        return true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawBitmap(bitmap, offsetX, offsetY, paint)
    }


}