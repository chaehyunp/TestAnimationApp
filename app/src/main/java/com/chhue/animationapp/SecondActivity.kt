package com.chhue.animationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chhue.animationapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}