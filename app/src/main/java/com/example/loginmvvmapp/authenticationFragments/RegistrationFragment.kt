package com.example.loginmvvmapp.authenticationFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loginmvvmapp.R
import com.example.loginmvvmapp.databinding.FragmentRegistrationBinding
import com.example.loginmvvmapp.model.User
import com.example.loginmvvmapp.utils.NetworkResult
import com.example.loginmvvmapp.utils.Network_Check
import com.example.loginmvvmapp.utils.TokenManager
import com.example.loginmvvmapp.viewModels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding

    val userAuthenticationViewModel by viewModels<AuthenticationViewModel>()


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            _binding = FragmentRegistrationBinding.inflate(inflater, container, false)


            //check if token available
            //if available pass user to home fragment

           // checkToken()


//        val token1 = getPrefData()
//        Log.d("token1","token1 = $token1")
//
//        if (token1 != null){
//            findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
//        }

            return binding!!.root
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            //hide toolbar on registration fragment

            (requireActivity() as AppCompatActivity).supportActionBar?.hide()

            binding!!.progressBarSignUp.isVisible = false






            binding!!.signupbtn.setOnClickListener {

                val email2 = binding?.emailedt?.text.toString()
                val password = binding?.passwordedt?.text.toString()

                val userCred = User(email2,password)

                if (email2.isNotEmpty() && password.isNotEmpty()){
                lifecycleScope.launch {
                    userAuthenticationViewModel.registerUser(user = userCred)
                }
                }else{
                    Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }


            }

            //observe live data


            userAuthenticationViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
                binding!!.progressBarSignUp.isVisible = false
                when (it) {
                    is NetworkResult.Success -> {

                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT)
                            .show()

                        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    }

                    is NetworkResult.Error -> {
                        binding!!.responsetext.text = it.message
                    }

                    is NetworkResult.Loading -> {
                        binding!!.progressBarSignUp.isVisible = true
                    }

                }
            })




            binding!!.textView.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }


        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun getPrefData(): String? {
            val sharedPref =
                requireContext().getSharedPreferences("tokenPrefData", Context.MODE_PRIVATE)
            val gettokendata = sharedPref.getString("token2", null)

            return gettokendata

        }





    }

