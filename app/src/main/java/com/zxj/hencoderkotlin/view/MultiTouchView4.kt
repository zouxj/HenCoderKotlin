//package com.zxj.hencoderkotlin.view
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.graphics.Path
//import android.util.AttributeSet
//import android.util.SparseArray
//import android.view.MotionEvent
//import android.view.MotionEvent.*
//import android.view.View
//import com.zxj.hencoderkotlin.Utils
//
///**
// * function:
// *
// * <p>
// * Created by Zxj on 2019/10/10.
// */
//class MultiTouchView4(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
//    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//    internal var paths = SparseArray<Path>()
//
//
//   init {
//        paint.style = Paint.Style.STROKE;
//        paint.strokeWidth = Utils.dp2px(4f);
//        paint.strokeCap = Paint.Cap.ROUND;
//        paint.strokeJoin = Paint.Join.ROUND;
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.actionMasked) {
//            ACTION_DOWN, ACTION_POINTER_DOWN -> {
//                val actionIndex = event.actionIndex
//                var pointerId = event.getPointerId(actionIndex)
//                var path = Path()
//                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
//                paths.append(pointerId, path)
//                invalidate()
//            }
//            ACTION_MOVE -> {
//                for (i in 0 until event.pointerCount) {
//                    pointerId = event.getPointerId(i)
//                    path = paths.get(pointerId)
//                    path.lineTo(event.getX(i), event.getY(i))
//                }
//                invalidate()
//            }
//            ACTION_UP, ACTION_POINTER_UP -> {
//                pointerId = event.getPointerId(event.actionIndex)
//                paths.remove(pointerId)
//                invalidate()
//            }
//        }
//        return true
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        for (i in 0 until paths.size()) {
//            val path = paths.valueAt(i)
//            canvas.drawPath(path, paint)
//        }
//    }
//}