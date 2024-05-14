package com.example.loginmvvmapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.loginmvvmapp.R
import com.example.loginmvvmapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SplashFragment : Fragment() {
    // TODO: Rename and change types of parameters


    var token:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = view.findViewById<TextView>(R.id.splashText)

        val splashAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.anim_from_left_to_right)

        text.startAnimation(splashAnimation)

        isTokenExists()
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("token","token=$token")
            if (token!=null){
                view?.post {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }else{
                view?.post {
                    findNavController().navigate(R.id.action_splashFragment_to_registrationFragment)
                }
            }

        }, 2000)
    }

    private fun isTokenExists() :Boolean {

        val tokenManager = TokenManager(requireContext())

        lifecycleScope.launch {
            tokenManager.getToken().collect { it ->

                //make sure you collect it before check if its null
                val gettokens = it
                token = it

                Log.d("TAG", "token by datastore = $gettokens")
            }
        }

        return token != null
    }



}