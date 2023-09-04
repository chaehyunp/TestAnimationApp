package com.chhue.animationapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chhue.animationapp.databinding.ActivityMainBinding
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageView = binding.imageView

        startAngleChangeJob(imageView)
        //startColorChangeJob(imageView)

        setBackgroundGlow(binding.likeInclude, R.drawable.sample_rectangle,256,160,230 )

        // 라이브러리 이용해서 테두리돌기
        val square = binding.sprogressbar

        square.setImage(R.drawable.btn_gradiant_alpha)
        square.setRoundedCorners(true, 100f)
        square.isIndeterminate = true
        square.setProgress(10)
        square.setColor("#1AFFFF")
        square.setImageScaleType(ImageView.ScaleType.MATRIX)
    }

    private fun setBackgroundGlow(imgview: ImageView, imageicon: Int, r: Int, g: Int, b: Int) {
        // An added margin to the initial image
        val margin = 60
        val halfMargin = margin / 2
        // the glow radius
        val glowRadius = 40

        // the glow color
        val glowColor = Color.rgb(r, g, b)

        // The original image to use
        val src = BitmapFactory.decodeResource(resources, imageicon)

        // extract the alpha from the source image
        val alpha = src.extractAlpha()

        // The output bitmap (with the icon + glow)
        val bmp =
            Bitmap.createBitmap(src.width + margin, src.height + margin, Bitmap.Config.ARGB_8888)

        // The canvas to paint on the image
        val canvas = Canvas(bmp)
        val paint = Paint()
        paint.color = glowColor

        // outer glow
        paint.maskFilter =
            BlurMaskFilter(glowRadius.toFloat(), Blur.OUTER) //For Inner glow set Blur.INNER
        canvas.drawBitmap(alpha, halfMargin.toFloat(), halfMargin.toFloat(), paint)

        // original icon
        canvas.drawBitmap(src, halfMargin.toFloat(), halfMargin.toFloat(), null)
        imgview.setImageBitmap(bmp)
    }

    //Call this once to change gradient angle
    private fun startAngleChangeJob(imageView: ImageView) {
        var shape = imageView.background
        var borderGrad = shape as GradientDrawable

        var timer = fixedRateTimer("colorTimer", false, 0L, 100) {
            var ori = borderGrad.orientation.ordinal
            var newOri = (ori + 1) % 8

            this@MainActivity.runOnUiThread {
                borderGrad.orientation = GradientDrawable.Orientation.values()[newOri]
            }
        }
    }

    private fun startColorChangeJob(imageView: ImageView) {
        var timer = fixedRateTimer("colorTimer", false, 0L, 100) {
            var shape = imageView.background
            var borderGrad = shape as GradientDrawable
            var colors = borderGrad.colors
            var colorsNew = IntArray(colors!!.size)
            for (i in colors.indices) {
                var newInd = (i + 1) % colors.size
                colorsNew[newInd] = colors[i]
            }
            this@MainActivity.runOnUiThread {
                borderGrad.colors = colorsNew
            }
        }
    }
}