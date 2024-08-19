package com.kbz.mobiz.presentation.screens.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kbz.mobiz.databinding.FragmentWelcomeBinding
import com.kbz.mobiz.presentation.screens.MainActivity
import com.kbz.mobiz.viewmodel.WelcomeViewModel


class WelcomeFragment : Fragment() {
    private  val welcomeViewModel : WelcomeViewModel by activityViewModels()
    private  val   welcomeBinding : FragmentWelcomeBinding by lazy {
        FragmentWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return welcomeBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       welcomeBinding.welcomeButton.setOnClickListener{
           welcomeViewModel.saveToLocal(true)
           startActivity(Intent(activity, MainActivity::class.java))
           activity?.finish()
       }
    }

}