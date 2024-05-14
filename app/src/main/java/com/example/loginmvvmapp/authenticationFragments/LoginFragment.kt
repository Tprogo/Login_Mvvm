package com.example.loginmvvmapp.authenticationFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loginmvvmapp.R
import com.example.loginmvvmapp.databinding.FragmentLoginBinding
import com.example.loginmvvmapp.model.User
import com.example.loginmvvmapp.utils.NetworkResult
import com.example.loginmvvmapp.utils.TokenManager
import com.example.loginmvvmapp.viewModels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
   private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding


//    private val authViewModel by viewModels<AuthenticationViewModel>()

    val authViewModel by viewModels<AuthenticationViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.progressBarSignin.isVisible = false

        binding!!.guideSignup.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }




        binding!!.loginbtn.setOnClickListener {
            val email = binding!!.emailedtlogin.text.toString()
            val pass = binding!!.passwordedtlogin.text.toString()

            val userLoginData = User(email,pass)



            if (email.isNotEmpty() && pass.isNotEmpty()){


            lifecycleScope.launch {
                authViewModel.loginUser(user = userLoginData)
            }

            }else{
                Toast.makeText(context, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }

        }




        authViewModel.userLoginResponseLiveData.observe(viewLifecycleOwner, Observer {

            binding!!.progressBarSignin.isVisible = false


            when(it){
                is NetworkResult.Success -> {

                    //add token in data store

//                    savePreference(it.data!!.token)

                    val token = it.data!!.token

                    if (token != null) {

                        lifecycleScope.launch {
                            tokenManager.saveToken(it.data!!.token)
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }

                    } else{
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                    Log.d("Save Token","save token = ${it.data!!.token}")



//                    lifecycleScope.launch {
//                        tokenManager.getToken().collect {
//                            val getTokenData = it
//
//                            if (getTokenData != null){
//                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                            }
//                        }
//                    }

                }
                is NetworkResult.Error -> {
                    binding!!.responsetextlogin.text = it.message
                }

                is NetworkResult.Loading -> {
                    binding!!.progressBarSignin.isVisible = true
                }
            }
        })











    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun savePreference(token: String) {
        val sharedPref = requireContext().getSharedPreferences("tokenPrefData", Context.MODE_PRIVATE)
        val editorPref = sharedPref.edit()
        editorPref.putString("token2", token)
        editorPref.apply()
    }


}