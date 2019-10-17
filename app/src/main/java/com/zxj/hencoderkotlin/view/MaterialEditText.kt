package com.zxj.hencoderkotlin.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.zxj.hencoderkotlin.R
import com.zxj.hencoderkotlin.Utils

/**
 * function:
 *
 * <p>
 * Created by Zxj on 2019/10/8.
 */
class MaterialEditText(val cont: Context?, val attrs: AttributeSet?) : EditText(cont, attrs) {

    private val TAG = MaterialEditText::class.java.simpleName
    private val TEXT_SIZE = Utils.dp2px(12f)
    private val TEXT_MARGIN = Utils.dp2px(8f)
    private val TEXT_VERTICAL_OFFSET = Utils.dp2px(22f)
    private val TEXT_HORIZONTAL_OFFSET = Utils.dp2px(5f)
    private val TEXT_ANIMATION_OFFSET = Utils.dp2px(16f)
    internal var floatingLabelFraction: Float = 0.toFloat()
    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal var animator: ObjectAnimator? = null
    internal var useFloatingLabel: Boolean = false
    internal var floatingLabelShown: Boolean = false
    internal var backgroundPadding = Rect()

    init {
        paint.textSize = TEXT_SIZE
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel =
            typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true)
        typedArray.recycle()
        setPadding(
            paddingLeft, (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
            paddingRight, paddingBottom
        )
        onUseFloatingLabelChanged()
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (useFloatingLabel) {
                    if (floatingLabelShown && TextUtils.isEmpty(s)) {
                        floatingLabelShown = false
                        getAnimator()!!.reverse()
                    } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                        floatingLabelShown = true
                        getAnimator()!!.start()
                    }
                }
            }

        })

    }

    private fun onUseFloatingLabelChanged() {
        background.getPadding(backgroundPadding)
        if (useFloatingLabel) {
            setPadding(
                paddingLeft, (backgroundPadding.top.toFloat() + TEXT_SIZE + TEXT_MARGIN).toInt(),
                paddingRight, paddingBottom
            )
        } else {
            setPadding(paddingLeft, backgroundPadding.top, paddingRight, paddingBottom)
        }
    }

    private fun getAnimator(): ObjectAnimator? {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(
                this@MaterialEditText,
                "floatingLabelFraction",
                0f,
                1f
            ) as ObjectAnimator?
        }
        return animator
    }


    fun getFloatingLabelFraction(): Float {

        return floatingLabelFraction
    }

    fun setFloatingLabelFraction(floatingLabelFraction: Float) {
        this.floatingLabelFraction = floatingLabelFraction
        invalidate()
    }
    fun setUseFloatingLabel(useFloatingLabel: Boolean) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel
            onUseFloatingLabelChanged()
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        paint.alpha = (0xff * floatingLabelFraction).toInt()
        val extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction)
        if (!TextUtils.isEmpty(text)) {
            canvas!!.drawText(
                hint.toString(),
                TEXT_HORIZONTAL_OFFSET,
                TEXT_VERTICAL_OFFSET + extraOffset,
                paint
            )
        }

    }
}