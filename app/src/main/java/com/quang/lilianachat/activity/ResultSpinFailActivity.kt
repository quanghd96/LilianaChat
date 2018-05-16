package com.quang.lilianachat.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.quang.lilianachat.R
import kotlinx.android.synthetic.main.activity_result_spin_fail.*

class ResultSpinFailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_spin_fail)
        imvClose.setOnClickListener { finish() }
        imvInviteFacebook.setOnClickListener { finish() }
    }
}
