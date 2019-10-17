package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/9/30.
 */
class CameraView(val ctx: Context,val attrs: AttributeSet) : View(ctx, attrs) {
    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal var camera = Camera()

    init {
        camera.rotateX(45f)
        camera.setLocation(0f, 0f, Utils.getZForCamera()) // -8 = -8 * 72
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        // 绘制上半部分
        canvas!!.save()
        canvas!!.translate((100 + 600 / 2).toFloat(), (100 + 600 / 2).toFloat())
        canvas!!.rotate(-20f)
        canvas!!.clipRect(-600, -600, 600, 0)
        canvas!!.rotate(20f)
        canvas!!.translate((-(100 + 600 / 2)).toFloat(), (-(100 + 600 / 2)).toFloat())
        canvas!!.drawBitmap(Utils.getAvatar(resources, 600), 100f, 100f, paint)
        canvas!!.restore()

        // 绘制下半部分
        canvas!!.save()
        canvas!!.translate((100 + 600 / 2).toFloat(), (100 + 600 / 2).toFloat())
        canvas!!.rotate(-20f)
        camera!!.applyToCanvas(canvas)
        canvas!!.clipRect(-600, 0, 600, 600)
        canvas!!.rotate(20f)
        canvas!!.translate((-(100 + 600 / 2)).toFloat(), (-(100 + 600 / 2)).toFloat())
        canvas!!.drawBitmap(Utils.getAvatar(resources, 600), 100f, 100f, paint)
        canvas!!.restore()
    }
}