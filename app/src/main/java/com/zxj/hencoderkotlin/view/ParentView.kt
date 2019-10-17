package com.zxj.hencoderkotlin.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.R.layout



/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/10/9.
 */
class ParentView(var ct: Context?,var  attrs: AttributeSet?) : ViewGroup(ct, attrs) {

    val TAG:String="ParentView"



    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            childView.layout(0, 0, 200, 200)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG,"onTouchEvent处理事件")
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG,"onInterceptTouchEvent拦截事件")
        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG,"dispatchTouchEvent分发事件")
        return super.dispatchTouchEvent(ev)
    }
}