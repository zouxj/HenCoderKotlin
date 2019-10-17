package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.Color
import android.graphics.Color.BLACK
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/10/9.
 */
class ChildView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val TAG:String="ChildView"

    init {
        setBackgroundColor(Color.BLACK)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG,"onTouchEvent处理事件")
        return super.onTouchEvent(event)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG,"dispatchTouchEvent分发事件")
        return super.dispatchTouchEvent(ev)
    }
}