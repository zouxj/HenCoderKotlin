package com.zxj.hencoderkotlin

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/9/16.
 */
 class Utils {
    companion object {
          fun dp2px(dp : Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
        }
        fun getZForCamera(): Float {
            return -6 * Resources.getSystem().displayMetrics.density
        }
        fun getAvatar(res: Resources, width: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, R.drawable.zou_shi_qi, options)
            options.inJustDecodeBounds = false
            options.inDensity = options.outWidth
            options.inTargetDensity = width
            return BitmapFactory.decodeResource(res, R.drawable.zou_shi_qi, options)
        }

    }

}