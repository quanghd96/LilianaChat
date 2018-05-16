package com.quang.lilianachat.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.quang.lilianachat.R
import kotlinx.android.synthetic.main.activity_lucky_wheel.*
import java.util.*

class LuckyWheelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_wheel)
        wheelview.setOnWheelStopListener { _, _, _ ->
            if (Random().nextBoolean()) {
                startActivity(Intent(this, ResultSpinFailActivity::class.java))
            } else startActivity(Intent(this, ResultSpinGreatActivity::class.java))
        }
    }
}
