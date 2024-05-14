package com.example.loginmvvmapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.loginmvvmapp.R
import com.example.loginmvvmapp.authenticationFragments.UserDataApi
import com.example.loginmvvmapp.databinding.FragmentHomeBinding
import com.example.loginmvvmapp.utils.NetworkResult
import com.example.loginmvvmapp.viewModels.GetDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


   private val getDataViewModel by viewModels<GetDataViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container, false)


//            val resp = getUserDataApi.getData().body()
//
//            Log.d("TAG","Data by retrofit $resp")




        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            getDataViewModel.getData()
        }

        getDataViewModel.getLiveData.observe(viewLifecycleOwner, Observer {

            binding!!.homeprogressbar.isVisible = false



            Log.d("Progress Bar Load","Loading Visibility = ${binding!!.homeprogressbar.isVisible}")

            when(it){
                is NetworkResult.Success ->{
                    binding!!.datatext.text = it.data!!.data.toString()

                }
                is NetworkResult.Error ->{
                    binding!!.errortext.text = it.message
                }

                is NetworkResult.Loading ->{
                    binding!!.homeprogressbar.isVisible = true
                }
            }
        })
    }



}