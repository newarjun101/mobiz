package com.kbz.mobiz.presentation.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kbz.mobiz.R
import com.kbz.mobiz.databinding.ActivityWelcomeBinding
import com.kbz.mobiz.viewmodel.SearchViewModel
import com.kbz.mobiz.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private  val welcomeViewModel : WelcomeViewModel by viewModels()
    private  val  welcomeBinding : ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // checkIsFirst()
        setContentView(welcomeBinding.root)
        /*welcomeBinding.welcomeButton.setOnClickListener{
            welcomeViewModel.saveToLocal(true)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }*/
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    fun checkIsFirst() {
       lifecycleScope.launch(Dispatchers.Main) {
           welcomeViewModel.readFromLocal.collect{
               if(it){
                   startActivity(Intent(applicationContext,MainActivity::class.java))
                   finish()
               }
           }
       }
    }
}