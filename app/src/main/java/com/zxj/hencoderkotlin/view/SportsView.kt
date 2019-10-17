package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build

import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/9/29.
 */
class SportsView : View {

    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val RING_WIDTH = Utils.dp2px(20f)
    private val RADIUS = Utils.dp2px(150f)
    private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
    private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
    internal var fontMetrics = Paint.FontMetrics()

    constructor(context: Context, attrs: AttributeSet) : super(context,attrs) {
        paint.textSize = Utils.dp2px(100f)
        paint.typeface = Typeface.createFromAsset(getContext().assets, "Quicksand-Regular.ttf")
        paint.getFontMetrics(fontMetrics)
        paint.textAlign = Paint.Align.CENTER
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 绘制环
        paint.style=Paint.Style.STROKE
        paint.color=CIRCLE_COLOR
        paint.strokeWidth=RING_WIDTH
        canvas!!.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), RADIUS, paint)
        // 绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap=Paint.Cap.ROUND
        canvas.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            -0f,
            225f,
            false,
            paint
        )
        // 绘制文字
















    }


}