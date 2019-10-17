package com.zxj.hencoderkotlin.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zxj.hencoderkotlin.R
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/9/30.
 */
class ImageTextView(val ctx: Context, val attrs: AttributeSet) : View(ctx, attrs) {

    private val IMAGE_WIDTH = Utils.dp2px(100f)
    private val IMAGE_OFFSET = Utils.dp2px(80f)
    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal lateinit var bitmap: Bitmap
    internal var fontMetrics = Paint.FontMetrics()
    internal var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor."
    internal var cutWidth = FloatArray(1)

    init {
        bitmap = getAvatar(Utils.dp2px(100f).toInt() )
        paint.textSize = Utils.dp2px(14f)
        paint.getFontMetrics(fontMetrics)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(bitmap, width - IMAGE_WIDTH, IMAGE_OFFSET, paint)
        val length = text.length
        var verticalOffset = -fontMetrics.top
        var start = 0
        while (start < length) {
            val maxWidth: Int
            val textTop = verticalOffset + fontMetrics.top
            val textBottom = verticalOffset + fontMetrics.bottom
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH || textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
                // 文字和图片在同一行
                maxWidth = (width - IMAGE_WIDTH).toInt()
            } else {
                // 文字和图片不在同一行
                maxWidth = width
            }
            val count = paint.breakText(text, start, length, true, maxWidth.toFloat(), cutWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.ic_launcher, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.ic_launcher, options)
    }

}