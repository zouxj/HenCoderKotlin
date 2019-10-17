package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory.Options
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.zxj.hencoderkotlin.R
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/9/16.
 */
class AvatarView : View {

    private val WIDTH = Utils.dp2px(100f)
    private val PADDING = Utils.dp2px(20f)
    private val EDGE_WIDTH = Utils.dp2px(20f)
    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal var xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    internal lateinit var bitmap: Bitmap
    internal var savedArea = RectF()

    constructor(context: Context, attrs: AttributeSet) : super(context,attrs) {
        bitmap = getBitmap(WIDTH.toInt())

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        savedArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint)
        val  saved =canvas!!.saveLayer(savedArea,paint)
        canvas!!.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, paint)
        paint!!.xfermode = xfermode
        canvas!!.drawBitmap(bitmap, PADDING, PADDING, paint)
        paint.xfermode = null
        canvas!!.restoreToCount(saved)

    }

    fun getBitmap(width:Int): Bitmap {
        val options = Options()
        options.inJustDecodeBounds=true
        BitmapFactory.decodeResource(resources, R.drawable.ic_launcher,options)
        options.inJustDecodeBounds=false
        options.inDensity=options.outWidth
        options.inTargetDensity=width
        return BitmapFactory.decodeResource(resources, R.drawable.ic_launcher,options)
    }
}