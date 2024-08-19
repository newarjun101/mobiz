package com.kbz.mobiz.presentation.screens.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.kbz.mobiz.R
import com.kbz.mobiz.databinding.FragmentSplashBinding
import com.kbz.mobiz.presentation.screens.MainActivity
import com.kbz.mobiz.viewmodel.WelcomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private  val welcomeViewModel : WelcomeViewModel by activityViewModels()
    private  val   splashBiding : FragmentSplashBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return splashBiding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkIsFirst()
        super.onViewCreated(view, savedInstanceState)
    }
   private fun checkIsFirst() {

        lifecycleScope.launch(Dispatchers.Main) {
            Log.d("ArjunLifeCycle","Started ${welcomeViewModel.data}")
            welcomeViewModel.readFromLocal.collect{
                if(it){
                    Log.d("ArjunLifeCycle","inslide flow Started ${activity}")
                    startActivity(Intent(activity, MainActivity::class.java))
                   // findNavController().popBackStack()
                    activity?.finish()
                }else {
                    findNavController().navigate(R.id.welcomeFragment,null,
                        NavOptions.Builder().setPopUpTo(findNavController().graph.startDestinationId, true).build())
                }
            }
        }
    }

}