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
class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

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
    private var trackingPointerId: Int = 0
    private  var actionIndex:Int=0
    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                Log.i(TAG,"ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId)
                offsetX = originalOffsetX +event.getX(index)  - downX
                offsetY = originalOffsetY +event.getY(index)  - downY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                 actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                Log.i(TAG,"ACTION_POINTER_DOWN")

            }
            MotionEvent.ACTION_POINTER_UP -> {
                actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                if (pointerId == trackingPointerId) {
                    val newIndex: Int
                    if (actionIndex == event.pointerCount - 1) {
                        newIndex = event.pointerCount - 2
                    } else {
                        newIndex = event.pointerCount - 1
                    }
                    trackingPointerId = event.getPointerId(newIndex)
                    downX = event.getX(actionIndex)
                    downY = event.getY(actionIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
                Log.i(TAG,"ACTION_POINTER_UP")

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