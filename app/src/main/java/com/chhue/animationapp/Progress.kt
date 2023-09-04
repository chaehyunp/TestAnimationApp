package com.chhue.animationapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathEffect
import android.graphics.PathMeasure
import android.graphics.RectF
import android.view.View


internal class V(context: Context?) : View(context) {
    var path: Path = Path()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var length = 0f
    var intervals = floatArrayOf(0f, 0f)

    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        val rect = RectF(0f, 0f, w.toFloat(), h.toFloat())
        val inset = paint.strokeWidth
        rect.inset(inset, inset)
        path.addRoundRect(rect, 100f, 100f, Path.Direction.CW)
        length = PathMeasure(path, false).length
        intervals[1] = length
        intervals[0] = intervals[1]
        val effect: PathEffect = DashPathEffect(intervals, length)
        paint.pathEffect = effect
    }

    fun setProgress(progress: Int) {
        val effect: PathEffect = DashPathEffect(intervals, length - length * progress / 100)
        paint.pathEffect = effect
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }
}