package com.kbz.mobiz.presentation.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kbz.mobiz.R
import com.kbz.mobiz.databinding.ActivityWelcomeBinding
import com.kbz.mobiz.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private  val welcomeViewModel : WelcomeViewModel by viewModels()
    private  val  welcomeBinding : ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.app_bg_color)
        setContentView(welcomeBinding.root)

    }

}