package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/9/16.
 */
class PieChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

     val RADIUS = Utils.dp2px(150f)
     val LENGTH = Utils.dp2px(20f)
     var angles: Array<Int> = arrayOf(60, 100, 120, 80)
     var colors= arrayOf(
         Color.parseColor("#2979FF"), Color.parseColor("#C2185B"),
         Color.parseColor("#009688"), Color.parseColor("#FF8F00"))
    val paint=Paint(Paint.ANTI_ALIAS_FLAG)
    val bounds=RectF()
    val PULLED_OUT_INDEX = 2

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(
            (width / 2 - RADIUS),
            (height / 2 - RADIUS),
            (width / 2 + RADIUS),
            (height / 2 + RADIUS)
        )
    }
    var currentAngles=0f
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        for (value in angles.indices){
            paint.color=colors[value]
            canvas!!.save()
            if (PULLED_OUT_INDEX==value){
                canvas!!.translate(
                    Math.cos(Math.toRadians((currentAngles + angles[value] / 2).toDouble())).toFloat() * LENGTH,
                    Math.sin(Math.toRadians((currentAngles + angles[value] / 2).toDouble())).toFloat() * LENGTH)
            }
            canvas!!.drawArc(bounds, currentAngles, angles[value].toFloat(),true,paint)
            canvas!!.restore()
            currentAngles+=angles[value]
        }

    }
}