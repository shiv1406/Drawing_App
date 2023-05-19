package com.example.drawing

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.drawing.fragments.InputBottomSheet

class SingleImageActivity : AppCompatActivity() {
    private val TIME_INTERVAL = 3000
    private var mBackPressed: Long = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_image)
        val imageView=findViewById<ImageView>(R.id.full_img)

        val imgUri=intent.extras?.getString("uri")!!
        Glide
            .with(applicationContext)
            .load(imgUri)
            .into(imageView as ImageView)

        imageView.setOnClickListener {

            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                val showReportSheet2 = InputBottomSheet(imgUri)
                showReportSheet2.show((this as AppCompatActivity).supportFragmentManager,"T")
            }

            mBackPressed = System.currentTimeMillis()
        }
    }
}